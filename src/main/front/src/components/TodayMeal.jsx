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
import { parseMealString } from "../tools/tools";

function TodayMeal({ mealTab, handleTabChange, mealData }) {
  // mealData.food의 key를 탭으로 사용
  const tabKeys = Object.keys(mealData.food);
  const currentKey = tabKeys[mealTab];
  const currentMeal = mealData.food[currentKey];

  // currentMeal이 string이면(운영없음 등) 단일 셀로 처리
  const isStringType = typeof currentMeal === "string";

  // currentMeal이 object면 헤더와 셀 데이터 추출
  const mealHeaders = isStringType ? [] : Object.keys(currentMeal);
  const mealValues = isStringType ? [] : Object.values(currentMeal);

  return (
    <Paper sx={{ mb: 3, p: 2 }}>
      <Typography variant="h5" sx={{ mb: 1, fontWeight: "bold" }}>
        🍖 오늘의 식단
      </Typography>
      <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
        <Tabs
          value={mealTab}
          onChange={handleTabChange}
          aria-label="meal tabs"
          variant="scrollable"
          scrollButtons="auto"
        >
          {tabKeys.map((key, idx) => (
            <Tab key={key} label={key} />
          ))}
        </Tabs>
      </Box>
      <TableContainer component={Paper} elevation={0} sx={{ mt: 1 }}>
        <Table>
          <TableHead>
            <TableRow sx={{ backgroundColor: "#f5f5f5" }}>
              <TableCell colSpan={mealHeaders.length || 1} align="center">
                {currentKey}
              </TableCell>
            </TableRow>
            {!isStringType && (
              <TableRow>
                {mealHeaders.map((header, idx) => (
                  <TableCell align="center" key={idx}>
                    {header}
                  </TableCell>
                ))}
              </TableRow>
            )}
          </TableHead>
          <TableBody>
            <TableRow>
              {isStringType ? (
                <TableCell align="center">
                  {parseMealString(currentMeal).map((line, i) => (
                    <Typography key={i} variant="body2" component="div">
                      {line}
                    </Typography>
                  ))}
                </TableCell>
              ) : (
                mealValues.map((value, idx) => (
                  <TableCell key={idx} align="center">
                    {/* 메뉴가 <br />로 구분된 문자열이면 줄바꿈 처리 */}
                    {typeof value === "string"
                      ? parseMealString(value).map((line, i) => (
                          <Typography key={i} variant="body2" component="div">
                            {line}
                          </Typography>
                        ))
                      : value}
                  </TableCell>
                ))
              )}
            </TableRow>
          </TableBody>
        </Table>
      </TableContainer>
    </Paper>
  );
}

export default TodayMeal;
