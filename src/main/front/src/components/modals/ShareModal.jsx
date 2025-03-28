import { useState, useRef, useEffect } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  TextField,
  Box,
  Snackbar,
  Typography,
} from "@mui/material";
import { useFetchBe } from "../../tools/api";

const ShareModal = ({ openState, item }) => {
  const [open, setOpen] = openState;
  const [shareHash, setShareHash] = useState(null);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const textFieldRef = useRef(null);
  const linkFieldRef = useRef(null);

  const fetchBe = useFetchBe();

  useEffect(() => {
    if (!open || !item?.id) return;
    fetchBe(`/kafeed/sharehash/${item.id}`).then((doc) => setShareHash(doc));
  }, [fetchBe, item?.id, open]);

  const handleClose = () => {
    setOpen(false);
  };

  const handleCopyToClipboard = () => {
    if (textFieldRef.current) {
      textFieldRef.current.select();
      document.execCommand("copy"); // Deprecated but still works in most browsers
      setSnackbarMessage("클립보드에 복사되었습니다");
      setSnackbarOpen(true);
    }
  };

  const handleShareMobile = () => {
    if (navigator.share) {
      navigator
        .share({
          title: "한동피드",
          text: shareText,
        })
        .then(() => {
          setSnackbarMessage("공유 성공!");
          setSnackbarOpen(true);
        })
        .catch((error) => {
          console.error("Error sharing:", error);
          setSnackbarMessage("공유 실패!");
          setSnackbarOpen(true);
        });
    } else {
      setSnackbarMessage("Web Share API is not supported in your browser.");
      setSnackbarOpen(true);
    }
  };

  const handleShareKakao = () => {
    // Implement Kakao share functionality
    console.log("Sharing with Kakao...");
    handleClose();
  };

  if (!shareHash || !item?.content) return <></>;

  const shareLink = `${window.location.origin}/k/${shareHash.shortHash}`;
  const prefix = `[한동피드]\n`;
  const suffix = `\n\n[한동피드] ${shareLink}`;
  const prefixSuffixLen = prefix.length + suffix.length;
  const shareText = `${
    item.content.length + prefixSuffixLen > 430
      ? item.content.substr(0, 430) + "(...한동피드에서 더보기)"
      : item.content
  }${suffix}`;

  return (
    <>
      <Dialog fullWidth open={open} onClose={handleClose}>
        <DialogTitle>공유하기</DialogTitle>
        <DialogContent>
          <Typography variant="subtitle2" gutterBottom>
            고정링크
          </Typography>
          <TextField
            value={`${shareLink}`}
            variant="outlined"
            fullWidth
            inputRef={linkFieldRef}
            slotProps={{
              input: {
                readOnly: true,
              },
            }}
            onClick={() => {
              linkFieldRef.current?.select();
              document.execCommand("copy"); // Deprecated but still works in most browsers
              setSnackbarMessage("클립보드에 복사되었습니다");
              setSnackbarOpen(true);
            }}
            sx={{ mb: 2 }}
          />
          <Typography variant="subtitle2" gutterBottom>
            전체내용 복사
          </Typography>
          <TextField
            multiline
            rows={4}
            value={`${shareText}`}
            variant="outlined"
            fullWidth
            inputRef={textFieldRef}
            slotProps={{
              input: {
                readOnly: true,
              },
            }}
            onClick={() => textFieldRef.current?.select()}
          />
        </DialogContent>
        <DialogActions>
          <Box display="flex" flexDirection="column" gap={1} width="100%">
            {navigator.canShare && (
              <Button onClick={handleShareMobile} color="primary" fullWidth>
                공유하기
              </Button>
            )}
            <Button onClick={handleCopyToClipboard} color="primary" fullWidth>
              클립보드 복사
            </Button>
            {/* <Button onClick={handleShareKakao} color="primary" fullWidth>
              Share with Kakao
            </Button> */}
            <Button onClick={handleClose} color="secondary" fullWidth>
              취소
            </Button>
          </Box>
        </DialogActions>
      </Dialog>

      <Snackbar
        open={snackbarOpen}
        autoHideDuration={6000}
        onClose={() => setSnackbarOpen(false)}
        message={snackbarMessage}
        action={
          <Button color="inherit" onClick={() => setSnackbarOpen(false)}>
            Close
          </Button>
        }
      />
    </>
  );
};

export default ShareModal;
