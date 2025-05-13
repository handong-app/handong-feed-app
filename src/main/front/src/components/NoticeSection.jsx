import React from "react";
import { Box, Typography, Paper } from "@mui/material";

function NoticeSection({ notices }) {
  return (
    <Paper sx={{ mb: 3, p: 2 }}>
      <Typography variant="h5" sx={{ mb: 2, fontWeight: "bold" }}>
        📚 히즈넷 공지
      </Typography>
      <Box sx={{ backgroundColor: "#f5f5f5", p: 1, mb: 2 }}>
        <Typography variant="subtitle1">전체 히츠넷 공지사항</Typography>
      </Box>
      <Typography variant="caption" gutterBottom display="block">
        * 글배수가 기준 글은 서지를 들어갑니다.
      </Typography>
      <Typography variant="subtitle2" color="primary" gutterBottom>
        드롭합늘관리부 교심전문
      </Typography>
      {notices.map((notice) => (
        <Box key={notice.id} sx={{ my: 2 }}>
          <Typography variant="body2" component="div">
            {notice.title}
          </Typography>
        </Box>
      ))}
    </Paper>
  );
}

export default NoticeSection;
