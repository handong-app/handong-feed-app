import React from "react";
import {
  Box,
  Typography,
  Tabs,
  Tab,
  TableContainer,
  Paper,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
} from "@mui/material";

function TodayMeal({ mealTab, handleTabChange, mealData }) {
  return (
    <Paper sx={{ mb: 3, p: 2 }}>
      <Typography variant="h5" sx={{ mb: 1, fontWeight: "bold" }}>
        🍖 오늘의 식단
      </Typography>
      <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
        <Tabs value={mealTab} onChange={handleTabChange} aria-label="meal tabs">
          <Tab label="든든" />
          <Tab label="따스" />
          <Tab label="맘스" />
          <Tab label="H:Plate" />
        </Tabs>
      </Box>
      <TableContainer component={Paper} elevation={0} sx={{ mt: 1 }}>
        <Table>
          <TableHead>
            <TableRow sx={{ backgroundColor: "#f5f5f5" }}>
              <TableCell colSpan={3} align="center">
                든든한동
              </TableCell>
            </TableRow>
            <TableRow>
              <TableCell align="center" width="33%">
                아침
              </TableCell>
              <TableCell align="center" width="33%">
                점심
              </TableCell>
              <TableCell align="center" width="33%">
                저녁
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            <TableRow>
              <TableCell>
                {mealData.아침.메뉴항목.map((item, index) => (
                  <Typography key={index} variant="body2" component="div">
                    {item}
                  </Typography>
                ))}
              </TableCell>
              <TableCell>
                {mealData.점심.메뉴항목.map((item, index) => (
                  <Typography key={index} variant="body2" component="div">
                    {item}
                  </Typography>
                ))}
              </TableCell>
              <TableCell>
                {mealData.저녁.메뉴항목.map((item, index) => (
                  <Typography key={index} variant="body2" component="div">
                    {item}
                  </Typography>
                ))}
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </TableContainer>
    </Paper>
  );
}

export default TodayMeal;
