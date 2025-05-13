import { Card, CardContent, CardMedia, Typography } from "@mui/material";
import { motion, usePresenceData } from "framer-motion";

const FeedRecommendSlidePage = ({ feeds }) => {
  const direction = usePresenceData();
  return (
    <motion.div
      custom={direction}
      initial={{ x: direction === 1 ? 300 : -300, opacity: 0 }}
      animate={{ x: 0, opacity: 1 }}
      exit={{ x: direction === 1 ? -300 : 300, opacity: 0 }}
      transition={{ duration: 0.5 }}
      style={{
        position: "absolute",
        margin: 4,
        height: 345,
        display: "flex",
        gap: "16px",
        justifyContent: "flex-start",
      }}
    >
      {feeds.map((feed) => (
        <Card
          sx={{
            display: "flex",
            flexDirection: "column",
            height: "100%",
          }}
        >
          <CardMedia
            component="img"
            height="180"
            image="/public/hehe.png"
            alt="Feed Image"
          />
          <CardContent>
            <Typography variant="body2" color="text.secondary">
              {feed.tag}
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
              }}
            >
              {feed.content + feed.description + feed.year}
            </Typography>
          </CardContent>
        </Card>
      ))}
    </motion.div>
  );
};

export default FeedRecommendSlidePage;
