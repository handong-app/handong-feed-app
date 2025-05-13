import React from "react";
import PropTypes from "prop-types";
import { Box } from "@mui/material";
import TagChip from "./TagChip";
import useTags from "../hooks/useTags";

function TagChipList({ searchTags = [], setSearchTags, ...props }) {
  const [tags] = useTags();
  return (
    <Box {...props}>
      {tags.map((tag) => (
        <TagChip
          key={tag.code}
          code={tag.code}
          label={tag.label}
          colorHex={tag.colorHex}
          tint={!searchTags.includes(tag.code)}
          outline={!searchTags.includes(tag.code)}
          onClick={() => {
            setSearchTags((prev) =>
              prev.includes(tag.code)
                ? prev.filter((code) => code !== tag.code)
                : [...prev, tag.code]
            );
          }}
        />
      ))}
    </Box>
  );
}

TagChipList.propTypes = {
  searchTags: PropTypes.array.isRequired,
  setSearchTags: PropTypes.func.isRequired,
};

export default TagChipList;
