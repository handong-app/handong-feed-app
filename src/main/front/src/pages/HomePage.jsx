import React, { useState } from "react";
import {
  Container,
  Typography,
  Box,
  Paper,
  Button,
  IconButton,
} from "@mui/material";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import EmailIcon from "@mui/icons-material/Email";
import TodayMeal from "../components/TodayMeal";
import FeedRecommend from "../components/FeedRecommend";
import NoticeSection from "../components/NoticeSection";
import { mealData, notices, recommendedFeeds } from "../constants";
import MainDisplay from "../components/MainDisplay";

function HomePage() {
  const [mealTab, setMealTab] = useState(0);

  const handleTabChange = (event, newValue) => {
    setMealTab(newValue);
  };

  // 추천 피드 페이지네이션 상태 추가
  const [feedPage, setFeedPage] = useState(0);
  const feedsPerPage = 2;
  const totalFeedPages = Math.ceil(recommendedFeeds.length / feedsPerPage);

  const handleFeedNext = () => {
    setFeedPage((prev) => (prev + 1) % totalFeedPages);
  };
  const handleFeedPrev = () => {
    setFeedPage((prev) => (prev - 1 + totalFeedPages) % totalFeedPages);
  };

  return (
    <MainDisplay>
      {/* 헤더 부분 */}
      <Paper elevation={1} sx={{ p: 2, mb: 3 }}>
        <Box display="flex" justifyContent="space-between" alignItems="center">
          <Box>
            <Typography variant="h5" fontWeight="bold">
              안녕하세요 ○○○님
            </Typography>
            <Typography variant="subtitle1">14주차 금요일입니다</Typography>
          </Box>
          <Button variant="outlined">대출 날씨</Button>
        </Box>
      </Paper>

      {/* 오늘의 식단 부분 */}
      <TodayMeal
        mealTab={mealTab}
        handleTabChange={handleTabChange}
        mealData={mealData}
      />

      {/* 추천 피드 */}
      <FeedRecommend
        recommendedFeeds={recommendedFeeds}
        feedPage={feedPage}
        feedsPerPage={feedsPerPage}
        totalFeedPages={totalFeedPages}
        handleFeedPrev={handleFeedPrev}
        handleFeedNext={handleFeedNext}
      />

      {/* 히츠넷 공지 */}
      <NoticeSection notices={notices} />
    </MainDisplay>
  );
}

export default HomePage;
