import { useEffect, useState } from "react";
import { Box, useTheme, useMediaQuery } from "@mui/material";
import { AnimatePresence } from "framer-motion";
import { Button } from "@mui/material";
import FeedRecommendSlidePage from "./FeedRecommendSlidePage";
import PropTypes from "prop-types";

const FeedRecommendSlider = ({ feeds, getData }) => {
  const theme = useTheme();
  const isSmDown = useMediaQuery(theme.breakpoints.down("sm"));
  const CARDS_PER_PAGE = isSmDown ? 1 : 2;

  const [page, setPage] = useState(0);
  const [direction, setDirection] = useState(1); // 1: 오른쪽, -1: 왼쪽

  const isLastPage = page === Math.ceil(feeds.length / CARDS_PER_PAGE) - 1;

  const handleNext = () => {
    setDirection(1);
    // 마지막 페이지면 아무것도 안함
    if (isLastPage) {
      return;
    }
    // 페이지가 마지막 페이지가 아니면 다음 페이지로 이동
    setPage((prev) => prev + 1);
  };

  const handlePrev = () => {
    // 첫 페이지면 아무것도 안함
    if (page === 0) {
      return;
    }

    // 페이지가 첫 페이지가 아니면 이전 페이지로 이동
    setDirection(-1);
    setPage(
      (prev) =>
        (prev - 1 + Math.ceil(feeds.length / CARDS_PER_PAGE)) %
        Math.ceil(feeds.length / CARDS_PER_PAGE)
    );
  };

  // 마지막 페이지면 데이터 더 불러오기
  // useEffect(() => {
  //   if (page === Math.ceil(feeds.length / CARDS_PER_PAGE) - 1) {
  //     getData();
  //   }
  // }, [page, feeds.length, getData]);

  const startIdx = Math.min(
    page * CARDS_PER_PAGE,
    feeds.length - CARDS_PER_PAGE
  );

  if (!feeds || feeds.length === 0) {
    return <></>;
  }

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
      {page > 0 && (
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
      )}

      <AnimatePresence mode="sync" custom={direction}>
        <FeedRecommendSlidePage
          key={page}
          feeds={feeds.slice(startIdx, startIdx + CARDS_PER_PAGE)}
          cardsPerPage={CARDS_PER_PAGE}
        />
      </AnimatePresence>

      {/* 오른쪽 화살표 */}
      {!isLastPage && (
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
      )}
    </Box>
  );
};

export default FeedRecommendSlider;

FeedRecommendSlider.propTypes = {
  feeds: PropTypes.array.isRequired,
  getData: PropTypes.func,
};
