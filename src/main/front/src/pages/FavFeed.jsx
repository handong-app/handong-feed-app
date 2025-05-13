import MainDisplay from "../components/MainDisplay";
import FeedCard from "../components/FeedCard";
import InfiniteScroll from "react-infinite-scroller";
import useLoadData from "../hooks/useLoadData";
import { Box, Card, CardContent, Typography } from "@mui/material";
import ToolBar from "../components/ToolBar";
import { useState } from "react";

function FavFeed() {
  const [allFeeds, hasMore, search, loadData, doSearch] = useLoadData({
    type: "like",
  });

  return (
    <MainDisplay>
      <ToolBar doSearch={doSearch} />
      {!hasMore && allFeeds.length === 0 && (
        <Card sx={{ my: 1 }}>
          <CardContent>
            <Box display="flex" flexDirection="column" alignItems="center">
              {search.stags.length === 0 && search.squery.length === 0 ? (
                <>
                  <Box
                    component="img"
                    src="/graphics/feed_like_sad.png"
                    alt="no feed"
                    sx={{
                      width: 140,
                      mb: 2,
                    }}
                  />
                  <Typography variant="h6" component="div" align="center">
                    피드에 좋아요를 눌러보세요
                  </Typography>
                </>
              ) : (
                <>
                  <Box
                    component="img"
                    src="/graphics/feed_news.png"
                    alt="no feed"
                    sx={{
                      width: 200,
                      mb: 2,
                    }}
                  />
                  <Typography variant="h6" component="div" align="center">
                    찜한 피드중에 검색 결과가 없습니다.
                  </Typography>
                </>
              )}
            </Box>
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
