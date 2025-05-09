import React, { useEffect } from "react";
import { useFetchBe } from "../tools/api";

function useTags() {
  const [tags, setTags] = React.useState([]);
  const [tagLoading, setTagLoading] = React.useState(false);

  const fetchBe = useFetchBe();

  const fetchTags = async () => {
    setTagLoading(true);
    try {
      const data = await fetchBe("/tags");
      if (Array.isArray(data)) {
        setTags(data);
      }
    } catch (err) {
      console.error("Failed to fetch tags:", err);
    } finally {
      setTagLoading(false);
    }
  };

  useEffect(() => {
    fetchTags();
  }, []);

  return [tags, tagLoading];
}

export default useTags;
