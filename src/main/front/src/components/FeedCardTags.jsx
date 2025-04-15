import { Chip, Stack, useTheme } from "@mui/material";
import React from "react";

function FeedCardTags({ tags }) {
  const theme = useTheme();
  return (
    <Stack direction="row" spacing={1} sx={{ pt: 2 }}>
      {tags &&
        tags.length > 0 &&
        tags.map((tag) => (
          <Chip
            key={tag.code}
            label={`#${tag.label}`}
            sx={{
              color: theme.palette.getContrastText(`#${tag.colorHex}`),
              backgroundColor: `#${tag.colorHex}`,
            }}
            // color={tag.colorHex}
            size="small"
          />
        ))}
    </Stack>
  );
}

export default FeedCardTags;
