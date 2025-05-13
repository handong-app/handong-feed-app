import { useState } from "react";
import {
  Box,
  Card,
  CardContent,
  CardHeader,
  CardMedia,
  Typography,
  useTheme,
  useMediaQuery,
} from "@mui/material";
import { motion, AnimatePresence } from "framer-motion";
import { Button } from "@mui/material";
import { recommendedFeeds } from "../constants";
import FeedRecommendSlidePage from "./FeedRecommendSlidePage";

const cards = [
  { id: 1, title: "Card 1", content: "This is the first card." },
  { id: 2, title: "Card 2", content: "This is the second card." },
  { id: 3, title: "Card 3", content: "This is the third card." },
  { id: 4, title: "Card 4", content: "This is the fourth card." },
  { id: 5, title: "Card 5", content: "This is the fifth card." },
  { id: 6, title: "Card 6", content: "This is the sixth card." },
];

const FeedRecommendSlider = () => {
  const theme = useTheme();
  const isSmDown = useMediaQuery(theme.breakpoints.down("sm"));
  const CARDS_PER_PAGE = isSmDown ? 1 : 2;

  const [page, setPage] = useState(0);
  const [direction, setDirection] = useState(1); // 1: 오른쪽, -1: 왼쪽

  const handleNext = () => {
    setDirection(1);
    setPage((prev) => (prev + 1) % Math.ceil(cards.length / CARDS_PER_PAGE));
  };

  const handlePrev = () => {
    setDirection(-1);
    setPage(
      (prev) =>
        (prev - 1 + Math.ceil(cards.length / CARDS_PER_PAGE)) %
        Math.ceil(cards.length / CARDS_PER_PAGE)
    );
  };

  const startIdx = page * CARDS_PER_PAGE;
  const visibleCards = cards.slice(startIdx, startIdx + CARDS_PER_PAGE);

  return (
    <Box
      sx={{
        position: "relative",
        width: "100%",
        height: "350px",
        overflow: "hidden",
      }}
    >
      {/* 왼쪽 화살표 */}
      <Button
        variant="contained"
        sx={{
          position: "absolute",
          left: 0,
          top: "50%",
          transform: "translateY(-50%)",
          minWidth: 0,
          width: 24,
          height: 36,
          zIndex: 2,
          borderRadius: "8px",
          backgroundColor: "#333",
          color: "#fff",
          boxShadow: 2,
          border: "none",
          transition: "background 0.2s, color 0.2s",
          "&:hover": {
            backgroundColor: "#1976d2",
            color: "#fff",
          },
        }}
        onClick={handlePrev}
      >
        <span
          style={{
            display: "inline-block",
            width: 0,
            height: 0,
            borderTop: "8px solid transparent",
            borderBottom: "8px solid transparent",
            borderRight: "12px solid #fff",
          }}
        />
      </Button>

      <AnimatePresence mode="sync" custom={direction}>
        <FeedRecommendSlidePage
          key={page}
          feeds={recommendedFeeds.slice(startIdx, startIdx + CARDS_PER_PAGE)}
        />
      </AnimatePresence>

      {/* 오른쪽 화살표 */}
      <Button
        variant="contained"
        onClick={handleNext}
        sx={{
          position: "absolute",
          right: 0,
          top: "50%",
          transform: "translateY(-50%)",
          minWidth: 0,
          width: 24,
          height: 36,
          zIndex: 2,
          borderRadius: "8px",
          backgroundColor: "#333",
          color: "#fff",
          boxShadow: 2,
          border: "none",
          transition: "background 0.2s, color 0.2s",
          "&:hover": {
            backgroundColor: "#1976d2",
            color: "#fff",
          },
        }}
      >
        <span
          style={{
            display: "inline-block",
            width: 0,
            height: 0,
            borderTop: "8px solid transparent",
            borderBottom: "8px solid transparent",
            borderLeft: "12px solid #fff",
          }}
        />
      </Button>
    </Box>
  );
};

export default FeedRecommendSlider;
