import { useEffect, useState } from "react";
import { Typography, Paper, Box, CircularProgress } from "@mui/material";
import FeedRecommendSlider from "./FeedRecommendSlider";
import TagChipList from "./TagChipList";
import useLoadData from "../hooks/useLoadData";
import GoToAllFeedButton from "./GoToAllFeedButton";

function FeedRecommend() {
  const todayMealTabStorage = localStorage.getItem("homeFeedRecommend") || 0;
  const [searchTags, setSearchTags] = useState(() => {
    let parsed = [];
    try {
      parsed = todayMealTabStorage ? JSON.parse(todayMealTabStorage) : [];
      if (!Array.isArray(parsed)) {
        parsed = [];
      }
    } catch (e) {
      parsed = [];
    }
    return parsed;
  });

  const [loading, setLoading] = useState(true);

  const [allFeeds, hasMore, search, loadData, doSearch] = useLoadData({});

  const getData = async () => {
    setLoading(true);
    try {
      await doSearch({ squery: "", stags: searchTags });
    } catch (error) {
      console.error("피드 데이터를 불러오는 중 오류가 발생했습니다:", error);
      // 필요한 경우 오류 상태 처리
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getData();
    // localstorage에 추가
    localStorage.setItem("homeFeedRecommend", JSON.stringify(searchTags));
  }, [searchTags]);

  return (
    <Paper sx={{ mb: 3, p: 2 }}>
      <Typography variant="h5" sx={{ mb: 1, fontWeight: "bold" }}>
        ✨ 추천 피드
      </Typography>
      <TagChipList
        searchTags={searchTags}
        setSearchTags={setSearchTags}
        sx={{ mb: 1 }}
      />
      {loading || (allFeeds.length === 0 && hasMore) ? (
        <Box
          height={345}
          display="flex"
          flexDirection="column"
          alignItems="center"
          justifyContent="center"
        >
          <CircularProgress color="primary" />
          <Typography variant="body2" color="text.secondary" sx={{ mt: 2 }}>
            추천 피드를 불러오는 중입니다...
          </Typography>
        </Box>
      ) : !hasMore && allFeeds.length === 0 ? (
        <Box
          height={345}
          display="flex"
          flexDirection="column"
          alignItems="center"
          justifyContent="center"
        >
          <Typography
            variant="h5"
            color="text.secondary"
            fontWeight="bold"
            gutterBottom
          >
            태그에 피드가 없습니다
          </Typography>
          <GoToAllFeedButton />
        </Box>
      ) : (
        <FeedRecommendSlider feeds={allFeeds} />
      )}
    </Paper>
  );
}

export default FeedRecommend;
