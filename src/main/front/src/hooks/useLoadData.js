import { useEffect, useRef, useState } from "react";
import { useFetchBe } from "../tools/api";
import { removeDuplicates } from "../tools/tools";
import { useFeedCount } from "./useFeed";

const useLoadData = ({ type = "" } = {}) => {
  const fetch = useFetchBe();
  const [_, getCount] = useFeedCount();

  const [allFeeds, setAllFeed] = useState([]);
  const [hasMore, setHasMore] = useState(true);
  const [search, setSearch] = useState({ stags: [], squery: "" });

  const requestCounter = useRef(0);

  const doSearch = async ({ squery = "", stags = [] }) => {
    // setAllFeed([]);
    // setHasMore(true);
    setSearch({ squery, stags });
    return getData({ clear: true, squery, stags });
  };

  const getData = async ({
    clear = false,
    stags = search.stags,
    squery = search.squery,
  }) => {
    const currentRequest = requestCounter.current + 1;
    requestCounter.current = currentRequest;
    const lastTimestamp = clear ? -1 : allFeeds.at(-1)?.sentAtEpoch || -1;
    const data = await fetch(
      `/kafeed/scrolllist?afterSentAt=${lastTimestamp}&type=${type}&search=${
        squery || ""
      }&tags=${encodeURIComponent(Array.isArray(stags) ? stags.join(",") : "")}`
    );
    if (!Array.isArray(data)) return;

    if (requestCounter.current !== currentRequest) {
      console.log("Ignore Late Response");
      return;
    }

    setAllFeed((prev) =>
      removeDuplicates(
        [
          ...(clear ? [] : prev),
          ...data.map((dd) => ({
            author: "실명카톡방2",
            sentAtEpoch: dd.sentAt,
            createdAt: new Date(dd.sentAt * 1000),
            id: dd.id,
            content: dd.message,
            files: dd.files,
            img: dd.files[0], // Temp just for testing
            subjectId: dd.subjectId,
            like: dd.like,
            messageCount: dd.messageCount,
            tags: dd.tags,
          })),
        ],
        "id"
      )
    );
    if (data.length > 0) {
      setHasMore(true);
    } else {
      setHasMore(false);
    }

    // Also update watch seen data on init request
    if (lastTimestamp === -1) getCount();
  };

  return [allFeeds, hasMore, search, getData, doSearch];
};

export default useLoadData;
