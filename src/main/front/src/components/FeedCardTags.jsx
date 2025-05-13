import { Button, Stack } from "@mui/material";
import React, { useState } from "react";
import TagChip from "./TagChip";
import TagReportModal from "./modals/TagReportModal";

function FeedCardTags({ tags, subjectId }) {
  const [isReportModalOpen, setIsReportModalOpen] = useState(false);

  if (!tags || tags.length === 0) {
    return null;
  }
  return (
    <Stack
      direction="row"
      alignItems="center"
      justifyContent="space-between"
      sx={{ pt: 0.5, mt: 1 }}
    >
      <Stack direction="row" spacing={0.5}>
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
      <Button
        sx={{
          textTransform: "none",
          color: "primary.main",
          px: 0.5,
          py: 0,
          background: "none",
        }}
        onClick={() => setIsReportModalOpen(true)}
      >
        태그가 어색한가요?
      </Button>
      <TagReportModal
        openState={[isReportModalOpen, setIsReportModalOpen]}
        subjectId={subjectId}
      />
    </Stack>
  );
}

export default FeedCardTags;
