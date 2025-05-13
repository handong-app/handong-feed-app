import InfiniteScroll from "react-infinite-scroller";
import AdminPage from "./AdminPage";
import FeedCard from "../../components/FeedCard";
import useLoadData from "../../hooks/useLoadData";
import { Box, Paper } from "@mui/material";

function AdminFeed() {
  const [allFeeds, hasMore, _, loadData] = useLoadData();

  return (
    <AdminPage>
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
          <Box
            key={item.id}
            sx={{
              display: "flex",
            }}
          >
            <Box sx={{ flexGrow: 1, maxWidth: 500 }}>
              <FeedCard key={item.id} item={item} />
            </Box>
            <Paper sx={{ flexGrow: 1, my: "16px", ml: 2 }}>Hello!</Paper>
          </Box>
        ))}
      </InfiniteScroll>
    </AdminPage>
  );
}

export default AdminFeed;
