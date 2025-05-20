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
  Skeleton,
} from "@mui/material";
import PropTypes from "prop-types";
import { splitHtmlBr } from "../tools/tools";

function TodayMeal({
  mealTab,
  handleTabChange,
  mealData: mealDataInput,
  loading,
}) {
  if (loading || !mealDataInput) {
    return (
      <Paper sx={{ mb: 3, p: 2 }}>
        <TableContainer component={Paper} elevation={0} sx={{ mt: 1 }}>
          <Table>
            <TableBody>
              {[...Array(3)].map((_, rowIdx) => (
                <TableRow key={rowIdx}>
                  {[...Array(3)].map((_, colIdx) => (
                    <TableCell key={colIdx} align="center">
                      <Skeleton
                        variant="rectangular"
                        width={80}
                        height={14}
                        sx={{ borderRadius: 1, mx: "auto" }}
                      />
                    </TableCell>
                  ))}
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Paper>
    );
  }

  const { date: mealDate, ...mealData } = mealDataInput;

  // mealData의 key를 탭으로 사용
  const tabKeys = Object.keys(mealData);
  const currentKey = tabKeys[mealTab];
  const currentMeal = mealData[currentKey];

  // currentMeal이 string이면(운영없음 등) 단일 셀로 처리
  const isStringType = typeof currentMeal === "string";

  // currentMeal이 object면 헤더와 셀 데이터 추출
  const mealHeaders = isStringType ? [] : Object.keys(currentMeal);
  const mealValues = isStringType ? [] : Object.values(currentMeal);

  // mealDate에서 '일' 다음에 space가 없으면 space 추가
  const formattedMealDate = mealDate
    ? mealDate.replace(/일(?! )/, "일 ")
    : mealDate;

  return (
    <Paper sx={{ mb: 3, p: 2 }}>
      <Typography variant="h5" sx={{ mb: 1, fontWeight: "bold" }}>
        🍖 {formattedMealDate} 식단
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
              <TableCell
                colSpan={mealHeaders.length || 1}
                align="center"
                sx={{ width: `${100 / (mealHeaders.length || 1)}%` }}
              >
                {currentKey}
              </TableCell>
            </TableRow>
            {!isStringType && (
              <TableRow>
                {mealHeaders.map((header, idx) => (
                  <TableCell
                    align="center"
                    key={idx}
                    sx={{ width: `${100 / mealHeaders.length}%` }}
                  >
                    {header}
                  </TableCell>
                ))}
              </TableRow>
            )}
          </TableHead>
          <TableBody>
            <TableRow>
              {isStringType ? (
                <TableCell align="center" sx={{ width: "100%" }}>
                  {splitHtmlBr(currentMeal).map((line, i) => (
                    <Typography key={i} variant="body2" component="div">
                      {line}
                    </Typography>
                  ))}
                </TableCell>
              ) : (
                mealValues.map((value, idx) => (
                  <TableCell
                    key={idx}
                    align="center"
                    sx={{ width: `${100 / mealValues.length}%` }}
                  >
                    {typeof value === "string"
                      ? splitHtmlBr(value).map((line, i) => (
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

TodayMeal.propTypes = {
  mealTab: PropTypes.number.isRequired,
  handleTabChange: PropTypes.func.isRequired,
  mealData: PropTypes.object, // mealData는 다양한 키를 가질 수 있으므로 object로 지정
  loading: PropTypes.bool, // loading prop도 명시
};
