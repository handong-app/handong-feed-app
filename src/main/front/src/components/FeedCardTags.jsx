import { Chip, Stack, useTheme } from "@mui/material";
import React from "react";

function FeedCardTags({ tags }) {
  const theme = useTheme();
  console.log(tags);
  return (
    <Stack direction="row" spacing={1} sx={{ pt: 1 }}>
      {tags && tags.length > 0 && (
        <div
          style={{
            display: "flex",
            flexWrap: "wrap",
            marginTop: "8px",
          }}
        >
          {tags.map((tag) => (
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
        </div>
      )}
    </Stack>
  );
}

export default FeedCardTags;
