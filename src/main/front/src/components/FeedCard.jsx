import { useEffect, useRef, useState } from "react";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import FavoriteIcon from "@mui/icons-material/Favorite";
import ShareIcon from "@mui/icons-material/Share";
import {
  convertTextToLinks,
  formatRelativeOrAbsoluteTimestamp,
} from "../tools/tools";
import ReactShowMoreText from "react-show-more-text";

import "./FeedCard.css";
import FeedCardGallery from "./FeedCardGallery";
import { Box, Link, Skeleton, Typography } from "@mui/material";
import { useFetchBe } from "../tools/api";
import { useSetRecoilState } from "recoil";
import { feedCountAtom } from "../recoil/feedAtom";
import ShareModal from "./modals/ShareModal";
import HistoryModal from "./modals/HistoryModal";
import FeedCardTags from "./FeedCardTags";

export default function FeedCard({ loading, item, watchSeen = false }) {
  const fetch = useFetchBe();
  const setFeedCount = useSetRecoilState(feedCountAtom);
  const [expanded, setExpanded] = useState(false);
  const [isScrolledUpOut, setIsScrolledUpOut] = useState(false);
  const [updateSeenServer, setUpdateSeenServer] = useState(false);
  const openState = useState(false);
  const historyOpenState = useState(false);
  const [like, setLike] = useState(item?.like);

  // eslint-disable-next-line unused-imports/no-unused-vars
  const handleExpandClick = () => {
    setExpanded(!expanded);
  };

  const cardRef = useRef(null);

  useEffect(() => {
    const handleScroll = () => {
      if (!watchSeen) return;

      const elementTarget = cardRef.current;
      if (elementTarget) {
        const elementBottom =
          elementTarget.offsetTop + elementTarget.offsetHeight;

        // Check if the user has scrolled past the card
        if (window.scrollY > elementBottom) {
          // alert("You've scrolled past the card");
          setIsScrolledUpOut(true);
        }
      }
    };

    window.addEventListener("scroll", handleScroll);

    // Cleanup the scroll event listener on component unmount
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, [watchSeen]);

  useEffect(() => {
    if (!isScrolledUpOut || updateSeenServer) return;
    setUpdateSeenServer(true);
    console.log(isScrolledUpOut, item.id);
    fetch("/feeduser/seen", "POST", { subjectId: item.subjectId }).then(() =>
      setFeedCount((prev) => prev - 1)
    );
  }, [item, isScrolledUpOut, fetch, setFeedCount, updateSeenServer]);

  return (
    <>
      <HistoryModal openState={historyOpenState} item={item} />
      <ShareModal openState={openState} item={item} />
      <Card ref={cardRef} className="FeedCard" sx={{ my: 1 }}>
        {loading ? (
          <>
            <CardHeader
              avatar={<Skeleton variant="circular" width={40} height={40} />}
              // action={<Skeleton variant="rectangular" width={24} height={24} />}
              title={<Skeleton width="80%" />}
              subheader={<Skeleton width="40%" />}
            />
            <Skeleton variant="rectangular" width="100%" height={150} />
            <CardContent>
              <Skeleton variant="text" width="90%" />
              <Skeleton variant="text" width="80%" />
              <Skeleton variant="text" width="95%" />
            </CardContent>
          </>
        ) : (
          <>
            <CardHeader
              avatar={
                <Avatar aria-label="logo" src="/favicon.png">
                  실카
                </Avatar>
              }
              action={
                <>
                  <IconButton
                    aria-label="Share"
                    onClick={() => {
                      openState[1](true);
                    }}
                  >
                    <ShareIcon />
                  </IconButton>
                  <IconButton
                    aria-label="settings"
                    onClick={() => {
                      if (like) {
                        fetch("/feeduser/unlike", "POST", {
                          subjectId: item.subjectId,
                        }).then(() => setLike(false));
                      } else {
                        fetch("/feeduser/like", "POST", {
                          subjectId: item.subjectId,
                        }).then(() => setLike(true));
                      }
                    }}
                  >
                    <FavoriteIcon style={{ color: like ? "red" : "inherit" }} />
                  </IconButton>
                </>
              }
              title={<Typography variant="body1">실명카톡방</Typography>}
              subheader={
                <Typography variant="body2">
                  {formatRelativeOrAbsoluteTimestamp(item.createdAt)}{" "}
                  {item.messageCount && item.messageCount > 1 && (
                    <Link
                      onClick={() => {
                        historyOpenState[1](true);
                      }} // Define your onClick handler
                      style={{ textDecoration: "none", cursor: "pointer" }} // Optional styling
                    >
                      (+{item.messageCount - 1})
                    </Link>
                  )}
                </Typography>
              }
              sx={{
                "& .MuiCardHeader-avatar": {
                  marginRight: 1,
                },
              }}
            />
            <CardMedia sx={{ width: "100%" }}>
              {item.files && (
                <FeedCardGallery images={item.files} id={item.id} />
              )}
            </CardMedia>
            <CardContent
              sx={{
                whiteSpace: "pre-wrap",
                wordWrap: "break-word",
                wordBreak: "break-all",
                overflowWrap: "break-word",
                "&:last-child": {
                  paddingBottom: 2,
                },
              }}
            >
              <ReactShowMoreText lines={3} truncatedEndingComponent="">
                {convertTextToLinks(item.content.trim())}
              </ReactShowMoreText>
              <FeedCardTags tags={item.tags} />
            </CardContent>
            {/* <CardActions disableSpacing>
        <IconButton aria-label="add to favorites">
          <FavoriteIcon />
        </IconButton>
        <IconButton aria-label="share">
          <ShareIcon />
        </IconButton>
      </CardActions> */}
          </>
        )}
      </Card>
    </>
  );
}
