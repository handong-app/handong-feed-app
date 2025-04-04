import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TablePagination from "@mui/material/TablePagination";
import TableRow from "@mui/material/TableRow";
import { useCallback, useEffect, useMemo, useRef, useState } from "react";
import { useFetchBe } from "../../tools/api";
import { visuallyHidden } from "@mui/utils";

import { formatDistanceToNow, parseISO } from "date-fns";
import { ko } from "date-fns/locale";
import {
  Box,
  Button,
  TableSortLabel,
  Tooltip,
  Typography,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Snackbar,
  IconButton,
} from "@mui/material";
import VpnKeyIcon from "@mui/icons-material/VpnKey";
import VpnKeyOffIcon from "@mui/icons-material/VpnKeyOff";
import DeleteIcon from "@mui/icons-material/Delete";
import AdminPage from "./AdminPage";

const columns = [
  {
    id: "description",
    label: "Description",
    minWidth: 170,
    format: (value, row) => (
      <div>
        <Typography variant="body1">{value}</Typography>
      </div>
    ),
  },
  {
    id: "issuedBy",
    label: "Issued By",
    minWidth: 170,
    format: (value, row) => (
      <div>
        <Typography variant="body1">{value.name}</Typography>
        <Typography variant="body2" color="textSecondary">
          {value.id}
        </Typography>
      </div>
    ),
  },
  {
    id: "scopes",
    label: "Scopes",
    minWidth: 170,
    format: (value, row) => (
      <Box
        sx={{
          fontFamily: "monospace",
          whiteSpace: "pre-wrap",
          wordWrap: "break-word",
          maxHeight: "5em",
          overflowY: "auto",
          backgroundColor: "#f5f5f5",
        }}
      >
        {value?.join("\n")}
      </Box>
    ),
  },
  {
    id: "createdAt",
    label: "Created At",
    minWidth: 170,
    format: (value) => (
      <Tooltip title={value}>
        <span>
          {value &&
            formatDistanceToNow(parseISO(value), {
              addSuffix: true,
              locale: ko,
            })}
        </span>
      </Tooltip>
    ),
  },
  {
    id: "lastUsedAt",
    label: "Last Used",
    minWidth: 170,
    format: (value) => (
      <Tooltip title={value}>
        <span>
          {value &&
            formatDistanceToNow(parseISO(value), {
              addSuffix: true,
              locale: ko,
            })}
        </span>
      </Tooltip>
    ),
  },
];

function descendingComparator(a, b, orderBy) {
  if (!a[orderBy]) return 1;
  if (!b[orderBy]) return -1;
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

function getComparator(order, orderBy) {
  return order === "desc"
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy);
}

function EnhancedTableHead(props) {
  const {
    onSelectAllClick,
    order,
    orderBy,
    numSelected,
    rowCount,
    onRequestSort,
  } = props;
  const createSortHandler = (property) => (event) => {
    onRequestSort(event, property);
  };

  return (
    <TableHead>
      <TableRow>
        {columns.map((headCell) => (
          <TableCell
            key={headCell.id}
            align={headCell.numeric ? "right" : "left"}
            padding={headCell.disablePadding ? "none" : "normal"}
            sortDirection={orderBy === headCell.id ? order : false}
          >
            <TableSortLabel
              active={orderBy === headCell.id}
              direction={orderBy === headCell.id ? order : "asc"}
              onClick={createSortHandler(headCell.id)}
            >
              {headCell.label}
              {orderBy === headCell.id ? (
                <Box component="span" sx={visuallyHidden}>
                  {order === "desc" ? "sorted descending" : "sorted ascending"}
                </Box>
              ) : null}
            </TableSortLabel>
          </TableCell>
        ))}
        <TableCell align="center" padding="normal">
          Actions
        </TableCell>
      </TableRow>
    </TableHead>
  );
}

export default function AdminApiKeys() {
  const fetchBe = useFetchBe();

  const [allData, setAllData] = useState([]);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(25);

  const [order, setOrder] = useState("desc");
  const [orderBy, setOrderBy] = useState("lastReadTime");
  const [selected, setSelected] = useState([]);
  const [dense, setDense] = useState(false);

  const [openModal, setOpenModal] = useState(false);
  const [description, setDescription] = useState("");
  const [scopes, setScopes] = useState([]);
  const [scopeInput, setScopeInput] = useState("");
  const [apiKey, setApiKey] = useState("");
  const apiKeyRef = useRef(null);

  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");

  // 기존 API 키 발급 이벤트 핸들러 수정: 모달 오픈
  const handleApiKeyIssue = () => {
    setDescription("");
    setScopes([]);
    setScopeInput("");
    setApiKey("");
    setOpenModal(true);
  };

  const handleModalClose = () => {
    setOpenModal(false);
  };

  // 스코프 추가 핸들러
  const handleAddScope = () => {
    const trimmed = scopeInput.trim();
    if (trimmed && !scopes.includes(trimmed)) {
      setScopes([...scopes, trimmed]);
      setScopeInput("");
    }
  };

  // 스코프 삭제 핸들러
  const handleRemoveScope = (idx) => {
    setScopes(scopes.filter((_, i) => i !== idx));
  };

  const handleApiResponse = (success, action) => {
    setSnackbarMessage(
      `API 키 ${action}${success ? "되었습니다" : "에 실패했습니다"}.`
    );
    setSnackbarOpen(true);
    if (success) refreshData();
  };

  const handleSubmit = () => {
    // 제출 처리 로직 추가
    console.log("description:", description);
    console.log("scopes:", scopes);
    fetchBe("/admin/issue-api-key", "POST", {
      description: description,
      scopes: scopes,
    })
      .then((doc) => {
        if (doc.apiKey) {
          setApiKey(doc.apiKey);
          refreshData();
        } else {
          throw new Error("응답에 apiKey가 없습니다." + JSON.stringify(doc));
        }
      })
      .catch((err) => {
        console.error(err);
        handleApiResponse(false, "발급");
      });
  };

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === "asc";
    setOrder(isAsc ? "desc" : "asc");
    setOrderBy(property);
  };

  const handleSelectAllClick = (event) => {
    if (event.target.checked) {
      const newSelected = allData.map((n) => n.id);
      setSelected(newSelected);
      return;
    }
    setSelected([]);
  };

  const handleClick = (event, id) => {
    const selectedIndex = selected.indexOf(id);
    let newSelected = [];

    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, id);
    } else if (selectedIndex === 0) {
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) {
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelected = newSelected.concat(
        selected.slice(0, selectedIndex),
        selected.slice(selectedIndex + 1)
      );
    }
    setSelected(newSelected);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleChangeDense = (event) => {
    setDense(event.target.checked);
  };

  const handleToggleEnable = (id) => {
    console.log("Toggle enable for:", id);
    fetchBe(`/admin/api-keys/${id}/toggle-status`, "PATCH", {})
      .then((doc) => {
        if (doc.id) {
          handleApiResponse(true, "상태 변경");
        } else {
          handleApiResponse(false, "상태 변경");
          console.error("API 응답 오류:", doc);
        }
      })
      .catch((err) => {
        console.error(err);
        handleApiResponse(false, "상태 변경");
      });
  };

  const handleDelete = (id) => {
    fetchBe(`/admin/api-keys/${id}`, "DELETE", {})
      .then((doc) => {
        handleApiResponse(true, "삭제");
      })
      .catch((err) => {
        console.error(err);
        handleApiResponse(false, "삭제");
      });
  };

  // Avoid a layout jump when reaching the last page with empty allData.
  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - allData.length) : 0;

  const visibleRows = useMemo(
    () =>
      [...allData]
        .sort(getComparator(order, orderBy))
        .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage),
    [order, orderBy, page, rowsPerPage, allData]
  );

  const refreshData = useCallback(() => {
    fetchBe("/admin/api-keys").then((doc) => setAllData(doc));
  }, [fetchBe]);

  useEffect(() => {
    refreshData();
  }, []);

  return (
    <AdminPage>
      <Paper
        sx={{ width: "100%", overflow: "hidden", padding: 3 /* 추가된 패딩 */ }}
      >
        {/* 디자인 개선: variant, color, margin-bottom 추가 */}
        <Button
          variant="contained"
          color="primary"
          sx={{ mb: 2 }}
          onClick={handleApiKeyIssue}
        >
          API 키 발급
        </Button>
        <TableContainer
          sx={{
            maxHeight: "80vh",
          }}
        >
          <Table stickyHeader aria-label="sticky table">
            <EnhancedTableHead
              numSelected={selected.length}
              order={order}
              orderBy={orderBy}
              onSelectAllClick={handleSelectAllClick}
              onRequestSort={handleRequestSort}
              rowCount={allData.length}
            />
            <TableBody>
              {visibleRows.map((row) => {
                return (
                  <TableRow hover role="checkbox" tabIndex={-1} key={row.id}>
                    {columns.map((column) => {
                      return (
                        <TableCell key={column.id} align={column.align}>
                          {column.format
                            ? column.format(row[column.id], row)
                            : row[column.id]}
                        </TableCell>
                      );
                    })}
                    <TableCell align="center" padding="normal">
                      <Tooltip title={row.active ? "비활성화" : "활성화"}>
                        <IconButton onClick={() => handleToggleEnable(row.id)}>
                          {row.active ? (
                            <VpnKeyIcon sx={{ color: "green" }} />
                          ) : (
                            <VpnKeyOffIcon sx={{ color: "orange" }} />
                          )}
                        </IconButton>
                      </Tooltip>
                      <Tooltip title="삭제">
                        <IconButton
                          onClick={() => handleDelete(row.id)}
                          sx={{ color: "grey" }}
                        >
                          <DeleteIcon />
                        </IconButton>
                      </Tooltip>
                    </TableCell>
                  </TableRow>
                );
              })}
              {emptyRows > 0 && (
                <TableRow
                  style={{
                    height: (dense ? 33 : 53) * emptyRows,
                  }}
                >
                  <TableCell colSpan={6} />
                </TableRow>
              )}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[25, 100, 500]}
          component="div"
          count={allData.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </Paper>

      {/* API 키 발급 모달 추가 */}
      <Dialog
        open={openModal}
        onClose={handleModalClose}
        fullWidth
        maxWidth="sm"
      >
        <DialogTitle>API 키 발급</DialogTitle>
        {apiKey ? (
          <>
            <DialogContent>
              <Typography variant="body1">
                API 키가 발급되었습니다. 아래 키를 복사하여 사용하세요.
                <br />이 키는 다시 발급할 수 없습니다.
              </Typography>
              <TextField
                value={apiKey}
                fullWidth
                variant="outlined"
                inputRef={apiKeyRef}
                slotProps={{
                  input: {
                    readOnly: true,
                  },
                }}
                onClick={() => {
                  apiKeyRef.current?.select();
                  navigator.clipboard
                    .writeText(apiKey)
                    .then(() => {
                      handleApiResponse(true, "복사");
                    })
                    .catch((err) => {
                      console.error("클립보드 복사 실패:", err);
                      handleApiResponse(false, "복사");
                    });
                }}
              />
            </DialogContent>
            <DialogActions>
              <Button onClick={handleModalClose}>나가기</Button>
            </DialogActions>
          </>
        ) : (
          <>
            <DialogContent>
              <TextField
                autoFocus
                margin="dense"
                label="API 키 사용 용도"
                fullWidth
                variant="outlined"
                placeholder="spotlight"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              />
              {/* 스코프 추가 영역 */}
              <Box sx={{ mt: 2 }}>
                <TextField
                  margin="dense"
                  label="스코프 추가"
                  fullWidth
                  variant="outlined"
                  placeholder="예: tag:create"
                  value={scopeInput}
                  onChange={(e) => setScopeInput(e.target.value)}
                />
                <Button
                  onClick={handleAddScope}
                  sx={{ mt: 1 }}
                  variant="outlined"
                >
                  추가
                </Button>
                {scopes.length > 0 && (
                  <Box sx={{ mt: 2 }}>
                    {scopes.map((scope, idx) => (
                      <Box
                        key={idx}
                        sx={{ display: "flex", alignItems: "center", mb: 1 }}
                      >
                        <Typography variant="body2" sx={{ flexGrow: 1 }}>
                          {scope}
                        </Typography>
                        <Button
                          onClick={() => handleRemoveScope(idx)}
                          variant="text"
                          color="error"
                        >
                          삭제
                        </Button>
                      </Box>
                    ))}
                  </Box>
                )}
              </Box>
            </DialogContent>
            <DialogActions>
              <Button onClick={handleModalClose}>취소</Button>
              <Button
                onClick={handleSubmit}
                disabled={
                  scopeInput.trim() !== "" ||
                  description.trim() === "" ||
                  scopes.length === 0
                }
                variant="contained"
                color="primary"
              >
                제출
              </Button>
            </DialogActions>
          </>
        )}
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
    </AdminPage>
  );
}
