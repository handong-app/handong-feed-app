import { useEffect, useState } from "react";
import { Typography, Paper, Box } from "@mui/material";
import FeedRecommendSlider from "./FeedRecommendSlider";
import TagChipList from "./TagChipList";
import useLoadData from "../hooks/useLoadData";
import GoToAllFeedButton from "./GoToAllFeedButton";

function FeedRecommend() {
  const [searchTags, setSearchTags] = useState([]);
  const [loading, setLoading] = useState(true);

  const [allFeeds, hasMore, search, loadData, doSearch] = useLoadData({});

  const getData = async () => {
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
      {loading ? (
        <div>Loading</div>
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
