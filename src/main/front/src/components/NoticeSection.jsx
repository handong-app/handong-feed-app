import PropTypes from "prop-types";

import { Box, Typography, Paper } from "@mui/material";
import { splitHtmlBr } from "../tools/tools";

function NoticeSection({ notices, loading }) {
  if (loading) {
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
        {[...Array(3)].map((_, idx) => (
          <Box key={idx} sx={{ mb: 2 }}>
            <Box sx={{ width: "60%", mb: 1 }}>
              <Box sx={{ bgcolor: "#e0e0e0", height: 24, borderRadius: 1 }} />
            </Box>
            <Box sx={{ width: "100%" }}>
              <Box sx={{ bgcolor: "#e0e0e0", height: 16, borderRadius: 1 }} />
            </Box>
          </Box>
        ))}
      </Paper>
    );
  }
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
      {notices
        .sort((a, b) => +b.view - +a.view)
        .map((notice) => (
          <Box key={notice.link} sx={{ mb: 2 }}>
            <Typography
              as="a"
              target="_blank"
              rel="noopener noreferrer"
              href={notice.link}
              variant="subtitle2"
              color="primary"
              sx={{
                display: "-webkit-box",
                WebkitLineClamp: 1,
                WebkitBoxOrient: "vertical",
                overflow: "hidden",
                textOverflow: "ellipsis",
                whiteSpace: "normal",
                wordBreak: "break-all",
              }}
            >
              {notice.title}
            </Typography>
            <Typography
              variant="body2"
              component="div"
              sx={{
                display: "-webkit-box",
                WebkitLineClamp: 3,
                WebkitBoxOrient: "vertical",
                overflow: "hidden",
                textOverflow: "ellipsis",
                whiteSpace: "normal",
                wordBreak: "break-all",
              }}
            >
              {splitHtmlBr(notice.content).join(" ")}
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
      link: PropTypes.string.isRequired,
      title: PropTypes.string.isRequired,
      content: PropTypes.string,
      view: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
    })
  ).isRequired,
  loading: PropTypes.bool.isRequired,
};
