import { Card, CardContent, CardMedia, Typography } from "@mui/material";
import { motion, usePresenceData } from "framer-motion";
import { Link } from "react-router-dom";
import GoToAllFeedButton from "./GoToAllFeedButton";
import PropTypes from "prop-types";

const FeedRecommendSlidePage = ({ feeds, cardsPerPage }) => {
  const direction = usePresenceData();
  return (
    <motion.div
      custom={direction}
      initial={{ x: direction === 1 ? 300 : -300, opacity: 0 }}
      animate={{ x: 0, opacity: 1 }}
      exit={{ x: direction === 1 ? -300 : 300, opacity: 0 }}
      transition={{ duration: 0.35 }}
      style={{
        position: "absolute",
        margin: 4,
        height: 345,
        display: "flex",
        gap: "16px",
        justifyContent: "flex-start",
        padding: 8,
      }}
    >
      {Array.from({ length: cardsPerPage }).map((_, idx) => {
        if (feeds.length > idx) {
          const feed = feeds[idx];
          return (
            <Link
              to={`/kafeed/${feed.id}`}
              key={feed.id}
              style={{ flex: 1, textDecoration: "none" }}
            >
              <Card
                sx={{
                  display: "flex",
                  flexDirection: "column",
                  height: "100%",
                  flex: 1,
                  cursor: "pointer",
                }}
              >
                {feed.files[0] ? (
                  <CardMedia
                    component="img"
                    height="180"
                    image={feed.files[0]}
                    alt="Feed Image"
                  />
                ) : (
                  <CardMedia
                    component="img"
                    height="180"
                    image={"/new-hfeed.png"}
                    alt="Feed Image"
                    sx={{
                      objectFit: "contain",
                      backgroundColor: "white",
                    }}
                  />
                )}

                <CardContent sx={{ px: 2, py: 1 }}>
                  <Typography
                    variant="body2"
                    color="text.secondary"
                    as="div"
                    sx={{ mb: 0.5 }}
                  >
                    {feed.tags.length === 0
                      ? "\u00A0"
                      : feed.tags.map((tag) => tag.label).join(", ")}
                  </Typography>
                  <Typography
                    variant="body2"
                    sx={{
                      height: 100,
                      overflow: "hidden",
                      textOverflow: "ellipsis",
                      display: "-webkit-box",
                      WebkitLineClamp: 5,
                      WebkitBoxOrient: "vertical",
                      whiteSpace: "normal",
                      wordWrap: "break-word",
                      wordBreak: "break-all",
                    }}
                  >
                    {feed.content}
                  </Typography>
                </CardContent>
              </Card>
            </Link>
          );
        } else {
          return (
            <Card
              key={`no-feed-${idx}`}
              sx={{
                display: "flex",
                flexDirection: "column",
                height: "100%",
                flex: 1,
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              <CardContent
                sx={{
                  flex: 1,
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                  width: "100%",
                }}
              >
                <div style={{ width: "100%", textAlign: "center" }}>
                  <GoToAllFeedButton />
                </div>
              </CardContent>
            </Card>
          );
        }
      })}
    </motion.div>
  );
};

export default FeedRecommendSlidePage;

FeedRecommendSlidePage.propTypes = {
  feeds: PropTypes.array.isRequired,
  cardsPerPage: PropTypes.number.isRequired,
};
