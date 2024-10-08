import * as React from "react";
import { styled } from "@mui/material/styles";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Collapse from "@mui/material/Collapse";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import { deepOrange } from "@mui/material/colors";
import FavoriteIcon from "@mui/icons-material/Favorite";
import ShareIcon from "@mui/icons-material/Share";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import { convertTextToLinks, formatTimestamp } from "../tools/tools";
import ReactShowMoreText from "react-show-more-text";

import "./FeedCard.css";
import FeedCardGallery from "./FeedCardGallery";
import { Skeleton } from "@mui/material";

export default function FeedCard({ loading, item, setAllSeenFeedId }) {
  const [expanded, setExpanded] = React.useState(false);

  const handleExpandClick = () => {
    setExpanded(!expanded);
  };

  return (
    <Card className="FeedCard" sx={{ my: 2 }}>
      {loading ? (
        <>
          {" "}
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
              <Avatar sx={{ bgcolor: deepOrange[500] }} aria-label="recipe">
                실카
              </Avatar>
            }
            action={
              <IconButton aria-label="settings">
                <MoreVertIcon />
              </IconButton>
            }
            title="실명카톡방"
            subheader={formatTimestamp(item.createdAt)}
          />
          {/* <CardMedia
        component="img"
        // height="194"
        image="https://mui.com/static/images/cards/paella.jpg"
        alt="Paella dish"
      /> */}
          <CardMedia sx={{ width: "100%" }}>
            {item.files && <FeedCardGallery images={item.files} />}
          </CardMedia>
          <CardContent
            sx={{
              whiteSpace: "pre-wrap",
              wordWrap: "break-word",
              wordBreak: "break-all",
              overflowWrap: "break-word",
            }}
          >
            <ReactShowMoreText lines={3} truncatedEndingComponent="">
              {convertTextToLinks(item.content.trim())}
            </ReactShowMoreText>
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
  );
}
