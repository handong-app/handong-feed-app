import { useEffect, useState } from "react";
import { Typography, Paper } from "@mui/material";
import FeedRecommendSlider from "./FeedRecommendSlider";
import TagChipList from "./TagChipList";
import useLoadData from "../hooks/useLoadData";

function FeedRecommend({}) {
  const [searchTags, setSearchTags] = useState([]);
  const [loading, setLoading] = useState(true);

  const [allFeeds, hasMore, search, loadData, doSearch] = useLoadData({
    type: "like",
  });

  const getData = async (startNew) => {
    doSearch({ squery: "", stags: searchTags }).finally(() => {
      setLoading(false);
    });
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
      ) : (
        <FeedRecommendSlider feeds={allFeeds} getData={getData} />
      )}
    </Paper>
  );
}

export default FeedRecommend;
