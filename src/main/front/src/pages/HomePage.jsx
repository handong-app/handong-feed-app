import { useState } from "react";
import { Typography, Box, Paper, Button } from "@mui/material";
import TodayMeal from "../components/TodayMeal";
import FeedRecommend from "../components/FeedRecommend";
import NoticeSection from "../components/NoticeSection";
import { mealData, notices } from "../constants";
import MainDisplay from "../components/MainDisplay";
import { getCurrentWeekdayString } from "../tools/tools";
import { Link } from "react-router-dom";

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
            <Typography variant="h4" fontWeight="bold" gutterBottom>
              {getCurrentWeekdayString()} 한동피드입니다!
            </Typography>
            <Typography variant="h6" color="text.secondary">
              식단 및 히즈넷 공지도 곧 업데이트 될 예정입니다 :)
            </Typography>
            <Box mt={2} display="flex" gap={2}>
              <Button
                variant="contained"
                color="primary"
                component={Link}
                to="/new"
              >
                새로운 피드 보기
              </Button>
              <Button
                variant="outlined"
                color="primary"
                component={Link}
                to="/all"
              >
                전체피드 보기
              </Button>
            </Box>
          </Box>
        </Box>
      </Paper>

      {/* 오늘의 식단 부분 */}
      {/* <TodayMeal
        mealTab={mealTab}
        handleTabChange={handleTabChange}
        mealData={mealData}
      /> */}

      {/* 추천 피드 */}
      <FeedRecommend />

      {/* 히츠넷 공지 */}
      {/* <NoticeSection notices={notices} /> */}
    </MainDisplay>
  );
}

export default HomePage;
