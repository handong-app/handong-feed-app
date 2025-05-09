import { Chip, Stack, useTheme } from "@mui/material";
import React from "react";

function FeedCardTags({ tags }) {
  const theme = useTheme();
  return (
    <Stack direction="row" spacing={0.5} sx={{ pt: 0.5, mt: 1 }}>
      {tags &&
        tags.length > 0 &&
        tags.map((tag) => (
          <Chip
            key={tag.code}
            label={`#${tag.label}`}
            sx={{
              color: theme.palette.getContrastText(`#${tag.colorHex}`),
              backgroundColor: `#${tag.colorHex}`,
              borderRadius: 1,
            }}
            size="small"
          />
        ))}
    </Stack>
  );
}

export default FeedCardTags;
