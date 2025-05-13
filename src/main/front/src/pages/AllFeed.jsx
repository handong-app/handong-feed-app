import MainDisplay from "../components/MainDisplay";
import FeedCard from "../components/FeedCard";
import InfiniteScroll from "react-infinite-scroller";
import useLoadData from "../hooks/useLoadData";
import ToolBar from "../components/ToolBar";
import { Box, Card, CardContent, Typography } from "@mui/material";

function AllFeed() {
  const [allFeeds, hasMore, search, loadData, doSearch] = useLoadData();

  return (
    <MainDisplay>
      <ToolBar doSearch={doSearch} />
      {!hasMore && allFeeds.length === 0 && (
        <Card sx={{ my: 1 }}>
          <CardContent>
            <Box display="flex" flexDirection="column" alignItems="center">
              <Box
                component="img"
                src="/graphics/feed_news.png"
                alt="no feed"
                sx={{
                  width: 200,
                  mb: 2,
                }}
              />
              {search.stags.length === 0 && search.squery.length === 0 ? (
                <Typography variant="h6" component="div" align="center">
                  피드가 없어요!
                </Typography>
              ) : (
                <Typography variant="h6" component="div" align="center">
                  검색 결과가 없습니다.
                </Typography>
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
          // <FeedItemNew
          //   key={item.id}
          //   item={item}
          //   setAllSeenFeedId={setAllSeenFeedId}
          // />
          <FeedCard key={item.id} item={item} />
        ))}
      </InfiniteScroll>
    </MainDisplay>
  );
}

export default AllFeed;
