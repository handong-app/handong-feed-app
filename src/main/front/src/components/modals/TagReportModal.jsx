import { useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  TextField,
  Box,
  Typography,
} from "@mui/material";
import { useFetchBe } from "../../tools/api";

const TagReportModal = ({ openState, subjectId }) => {
  const [open, setOpen] = openState;
  const [reportContent, setReportContent] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const fetchBe = useFetchBe();

  const handleClose = () => {
    setOpen(false);
    setReportContent("");
    setErrorMessage("");
  };

  const handleSubmit = async () => {
    if (!reportContent.trim()) {
      setErrorMessage("신고 내용을 입력해주세요");
      return;
    }

    try {
      await fetchBe(`/tags/report`, "POST", {
        message: reportContent,
        subjectId: subjectId,
      });
      handleClose();
    } catch (error) {
      console.error("Error submitting report:", error);
      setErrorMessage("신고 접수 중 오류가 발생했습니다");
    }
  };

  return (
    <Dialog fullWidth open={open} onClose={handleClose}>
      <DialogTitle>태그 신고하기</DialogTitle>
      <DialogContent>
        <Typography variant="subtitle2" gutterBottom>
          잘못된 태그 정보를 신고해주세요
        </Typography>
        <TextField
          multiline
          rows={4}
          value={reportContent}
          onChange={(e) => setReportContent(e.target.value)}
          variant="outlined"
          fullWidth
          placeholder="신고 내용을 상세히 적어주세요"
          sx={{ mt: 1 }}
        />
        {errorMessage && (
          <Typography
            color="error"
            variant="caption"
            sx={{ mt: 1, display: "block" }}
          >
            {errorMessage}
          </Typography>
        )}
      </DialogContent>
      <DialogActions>
        <Box display="flex" gap={1} width="100%" p={2}>
          <Button onClick={handleClose} color="inherit" fullWidth>
            취소
          </Button>
          <Button
            onClick={handleSubmit}
            color="primary"
            variant="contained"
            fullWidth
          >
            신고하기
          </Button>
        </Box>
      </DialogActions>
    </Dialog>
  );
};

export default TagReportModal;
