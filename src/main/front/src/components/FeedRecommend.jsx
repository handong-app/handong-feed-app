import React from "react";
import {
  Box,
  Typography,
  Grid,
  Card,
  CardContent,
  CardMedia,
  Button,
  IconButton,
  Paper,
} from "@mui/material";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import FeedRecommendSlider from "./FeedRecommendSlider";

function FeedRecommend({
  recommendedFeeds,
  feedPage,
  feedsPerPage,
  totalFeedPages,
  handleFeedPrev,
  handleFeedNext,
}) {
  return (
    <Paper sx={{ mb: 3, p: 2 }}>
      <Typography variant="h5" sx={{ mb: 2, fontWeight: "bold" }}>
        ✨ 추천 피드
      </Typography>
      <FeedRecommendSlider />
    </Paper>
  );
}

export default FeedRecommend;
