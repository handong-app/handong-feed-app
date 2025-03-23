import MainDisplay from "../components/MainDisplay";
import FeedCard from "../components/FeedCard";
import InfiniteScroll from "react-infinite-scroller";
import useLoadData from "../hooks/useLoadData";
import { Card, CardContent, Typography } from "@mui/material";
import ToolBar from "../components/ToolBar";
import { useState } from "react";

function FavFeed() {
  const [allFeeds, hasMore, loadData, doSearch] = useLoadData({ type: "like" });

  return (
    <MainDisplay>
      <ToolBar doSearch={doSearch} />
      {!hasMore && allFeeds.length === 0 && (
        <Card sx={{ my: 1 }}>
          <CardContent>
            <Typography variant="h5" component="div" align="center">
              피드에 좋아요를 눌러보세요
            </Typography>
          </CardContent>
        </Card>
      )}
      <InfiniteScroll
        loadMore={loadData}
        hasMore={hasMore}
        loader={Array(1)
          .fill()
          .map((_, index) => (
            <FeedCard key={index} loading />
          ))}
      >
        {allFeeds.map((item) => (
          <FeedCard key={item.id} item={item} />
        ))}
      </InfiniteScroll>
    </MainDisplay>
  );
}

export default FavFeed;
