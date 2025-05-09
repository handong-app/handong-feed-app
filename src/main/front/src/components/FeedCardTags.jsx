import { Stack } from "@mui/material";
import React from "react";
import TagChip from "./TagChip";

function FeedCardTags({ tags }) {
  return (
    <Stack direction="row" spacing={0.5} sx={{ pt: 0.5, mt: 1 }}>
      {tags &&
        tags.length > 0 &&
        tags.map(
          (tag) =>
            tag && (
              <TagChip
                key={tag.code}
                code={tag.code || ""}
                label={tag.label || ""}
                colorHex={tag.colorHex || ""}
              />
            )
        )}
    </Stack>
  );
}

export default FeedCardTags;
