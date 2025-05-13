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
import Snackbar from "@mui/material/Snackbar";
import MuiAlert from "@mui/material/Alert";
import { useFetchBe } from "../../tools/api";

const TagReportModal = ({ openState, subjectId }) => {
  const [open, setOpen] = openState;
  const [reportContent, setReportContent] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const fetchBe = useFetchBe();

  const handleClose = () => {
    setOpen(false);
    setReportContent("");
    setErrorMessage("");
  };

  const handleSnackbarClose = (event, reason) => {
    if (reason === "clickaway") return;
    setSnackbarOpen(false);
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
      setSnackbarOpen(true);
      handleClose();
    } catch (error) {
      console.error("Error submitting report:", error);
      setErrorMessage("신고 접수 중 오류가 발생했습니다");
    }
  };

  return (
    <>
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
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={3000}
        onClose={handleSnackbarClose}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <MuiAlert
          elevation={6}
          variant="filled"
          onClose={handleSnackbarClose}
          severity="success"
        >
          신고가 성공적으로 접수되었습니다.
        </MuiAlert>
      </Snackbar>
    </>
  );
};

export default TagReportModal;
