import PropTypes from "prop-types";

import { Box, Typography, Paper } from "@mui/material";

function NoticeSection({ notices }) {
  return (
    <Paper sx={{ mb: 3, p: 2 }}>
      <Typography variant="h5" sx={{ mb: 2, fontWeight: "bold" }}>
        📚 히즈넷 공지
      </Typography>
      <Box sx={{ backgroundColor: "#f5f5f5", p: 1, mb: 1 }}>
        <Typography variant="subtitle1">전체 히츠넷 공지사항</Typography>
      </Box>
      <Typography variant="caption" gutterBottom display="block" mb={2}>
        * 조회수 기준으로 정렬되어 있습니다.
      </Typography>
      <Typography variant="subtitle2" color="primary">
        드롭합늘관리부 교심전문
      </Typography>
      {notices.map((notice) => (
        <Box key={notice.id} sx={{ mb: 2 }}>
          <Typography variant="body2" component="div">
            {notice.title}
          </Typography>
        </Box>
      ))}
    </Paper>
  );
}

export default NoticeSection;

NoticeSection.propTypes = {
  notices: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.number.isRequired,
      title: PropTypes.string.isRequired,
    })
  ).isRequired,
};
