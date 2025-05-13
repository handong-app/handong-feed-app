import { Button } from "@mui/material";
import { Link } from "react-router-dom";

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
    component={Link}
    to="/all"
  >
    모든 피드 보러가기
  </Button>
);

export default GoToAllFeedButton;
