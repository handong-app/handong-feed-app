import { useTheme } from "@emotion/react";
import { Chip, darken, emphasize, lighten } from "@mui/material";
import React from "react";

function TagChip({ code, label, colorHex, outline, tint, sx, onClick }) {
  const theme = useTheme();

  const backgroundColor = lighten(`#${colorHex}`, 0.65);

  return (
    <Chip
      key={code}
      label={`#${label}`}
      sx={{
        color: tint
          ? "grey"
          : outline
          ? darken(`#${colorHex}`, 0.1)
          : theme.palette.getContrastText(backgroundColor),
        backgroundColor: outline ? "transparent" : backgroundColor,
        borderRadius: 1,
        "&:not(.MuiChip-clickable)": {
          cursor: "default",
        },
        userSelect: "none",
        ...sx,
      }}
      size="small"
      onClick={onClick}
    />
  );
}

export default TagChip;
