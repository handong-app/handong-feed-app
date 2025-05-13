import { Button } from "@mui/material";

const GoToAllFeedButton = () => (
  <Button
    variant="contained"
    color="primary"
    sx={{
      borderRadius: "20px",
      fontWeight: "bold",
      fontSize: "1rem",
      px: 3,
      py: 1,
    }}
    onClick={() => (window.location.href = "/all")}
  >
    모든 피드 보러가기
  </Button>
);

export default GoToAllFeedButton;
