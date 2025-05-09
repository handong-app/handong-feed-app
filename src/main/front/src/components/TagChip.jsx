import { useTheme } from "@emotion/react";
import { Chip } from "@mui/material";
import React from "react";

function TagChip({ code, label, colorHex }) {
  const theme = useTheme();

  return (
    <Chip
      key={code}
      label={`#${label}`}
      sx={{
        color: theme.palette.getContrastText(`#${colorHex}`),
        backgroundColor: `#${colorHex}`,
        borderRadius: 1,
      }}
      size="small"
    />
  );
}

export default TagChip;
