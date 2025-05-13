import { useState } from "react";
import { Typography, Box, Paper, Button } from "@mui/material";
import TodayMeal from "../components/TodayMeal";
import FeedRecommend from "../components/FeedRecommend";
import NoticeSection from "../components/NoticeSection";
import { mealData, notices } from "../constants";
import MainDisplay from "../components/MainDisplay";

function HomePage() {
  const [mealTab, setMealTab] = useState(0);

  const handleTabChange = (event, newValue) => {
    setMealTab(newValue);
  };

  // 추천 피드 페이지네이션 상태 추가

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
        </Box>
      </Paper>

      {/* 오늘의 식단 부분 */}
      <TodayMeal
        mealTab={mealTab}
        handleTabChange={handleTabChange}
        mealData={mealData}
      />

      {/* 추천 피드 */}
      <FeedRecommend />

      {/* 히츠넷 공지 */}
      <NoticeSection notices={notices} />
    </MainDisplay>
  );
}

export default HomePage;
