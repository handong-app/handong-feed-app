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
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Snackbar,
  IconButton,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import AdminPage from "./AdminPage";

const columns = [
  { id: "code", label: "코드", minWidth: 100 },
  { id: "label", label: "레이블", minWidth: 170 },
  { id: "userDesc", label: "사용자 설명", minWidth: 200 },
  { id: "llmDesc", label: "LLM 설명", minWidth: 200 },
  {
    id: "colorHex",
    label: "색상",
    minWidth: 100,
    format: (value) => (
      <Box
        sx={{
          backgroundColor: `#${value}`,
          display: "inline-block",
          marginLeft: 1,
        }}
      >
        {value}
      </Box>
    ),
  },
  { id: "priorityWeight", label: "우선순위", minWidth: 100 },
  {
    id: "createdAt",
    label: "생성일",
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
    id: "updatedAt",
    label: "수정일",
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

export default function AdminTags() {
  const fetchBe = useFetchBe();

  // 목록 데이터를 위한 상태
  const [allData, setAllData] = useState([]);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(25);

  const [order, setOrder] = useState("desc");
  const [orderBy, setOrderBy] = useState("lastReadTime");
  const [selected, setSelected] = useState([]);
  const [dense, setDense] = useState(false);

  const [openModal, setOpenModal] = useState(false);

  // 신규 태그 등록을 위한 상태
  const [code, setCode] = useState("");
  const [label, setLabel] = useState("");
  const [userDesc, setUserDesc] = useState("");
  const [llmDesc, setLlmDesc] = useState("");
  const [colorHex, setColorHex] = useState("");
  const [priorityWeight, setPriorityWeight] = useState("");

  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");

  // 편집 셀을 위한 상태
  const [editingCell, setEditingCell] = useState({
    rowId: null,
    column: null,
    value: "",
  });
  const editingInputRef = useRef(null);

  console.log(editingCell);

  const handleTagRegistration = () => {
    setCode("");
    setLabel("");
    setUserDesc("");
    setLlmDesc("");
    setColorHex("");
    setPriorityWeight("");
    setOpenModal(true);
  };

  const handleModalClose = () => {
    setOpenModal(false);
  };

  const handleApiResponse = (success, action, doRefreshData = true) => {
    setSnackbarMessage(
      `태그 ${action}${success ? "에 성공" : "에 실패"}했습니다.`
    );
    setSnackbarOpen(true);
    if (success && doRefreshData) refreshData();
  };

  const handleSubmit = () => {
    fetchBe("/admin/tags", "POST", {
      code,
      label,
      userDesc,
      llmDesc,
      colorHex,
      priorityWeight,
    })
      .then((doc) => {
        handleApiResponse(true, "등록");
        setOpenModal(false);
      })
      .catch((err) => {
        console.error(err);
        handleApiResponse(false, "등록");
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

  const handleDelete = (id) => {
    fetchBe(`/admin/tags/${id}`, "DELETE", {})
      .then((doc) => {
        handleApiResponse(true, "삭제");
      })
      .catch((err) => {
        console.error(err);
        handleApiResponse(false, "삭제");
      });
  };

  const handleCellSave = (id, column) => {
    fetchBe(`/admin/tags/${id}`, "PATCH", { [column]: editingCell.value })
      .then((doc) => {
        const updatedData = allData.map((row) =>
          row.id === id ? { ...row, [column]: editingCell.value } : row
        );
        setAllData(updatedData);
        setEditingCell({ rowId: null, column: null, value: "" });
        handleApiResponse(true, "업데이트");
      })
      .catch((err) => {
        console.error(err);
        handleApiResponse(false, "업데이트");
      });
  };

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
    fetchBe("/tags").then((doc) => setAllData(doc));
  }, [fetchBe]);

  useEffect(() => {
    refreshData();
  }, []);

  // 선택된 셀에 각종 이벤트 헨들러 추가 (외부 클릭 및 Escape 키)
  useEffect(() => {
    if (editingCell.rowId !== null) {
      const handleClickOutside = (e) => {
        if (
          editingInputRef.current &&
          !editingInputRef.current.contains(e.target)
        ) {
          setEditingCell({ rowId: null, column: null, value: "" });
        }
      };
      const handleKeyDown = (e) => {
        if (e.key === "Escape") {
          setEditingCell({ rowId: null, column: null, value: "" });
        }
      };
      document.addEventListener("mousedown", handleClickOutside);
      document.addEventListener("keydown", handleKeyDown);
      return () => {
        document.removeEventListener("mousedown", handleClickOutside);
        document.removeEventListener("keydown", handleKeyDown);
      };
    }
  }, [editingCell]);

  return (
    <AdminPage>
      <Paper sx={{ width: "100%", overflow: "hidden", padding: 3 }}>
        <Button
          variant="contained"
          color="primary"
          sx={{ mb: 2 }}
          onClick={handleTagRegistration}
        >
          태그 등록
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
                  <TableRow hover role="checkbox" tabIndex={-1} key={row.code}>
                    {columns.map((column) => {
                      const isEditable = ![
                        "code",
                        "createdAt",
                        "updatedAt",
                      ].includes(column.id);
                      return (
                        <TableCell key={column.id} align={column.align}>
                          {editingCell.rowId === row.code &&
                          editingCell.column === column.id ? (
                            <TextField
                              variant="standard"
                              value={editingCell.value}
                              ref={editingInputRef}
                              onChange={(e) =>
                                setEditingCell({
                                  ...editingCell,
                                  value: e.target.value,
                                })
                              }
                              onKeyDown={(e) => {
                                if (e.key === "Enter") {
                                  handleCellSave(row.code, column.id);
                                }
                              }}
                              autoFocus
                              onFocus={(e) => {
                                const len = e.target.value.length;
                                e.target.setSelectionRange(len, len);
                              }}
                            />
                          ) : (
                            <span
                              style={
                                isEditable
                                  ? {
                                      cursor: "pointer",
                                      display: "inline-block",
                                    }
                                  : {}
                              }
                              onClick={() =>
                                isEditable &&
                                setEditingCell({
                                  rowId: row.code,
                                  column: column.id,
                                  value: row[column.id],
                                })
                              }
                            >
                              {!row[column.id] && row[column.id] !== 0 && (
                                <i>없음</i>
                              )}
                              {column.format
                                ? column.format(row[column.id], row)
                                : row[column.id]}
                            </span>
                          )}
                        </TableCell>
                      );
                    })}
                    <TableCell align="center" padding="normal">
                      <Tooltip title="삭제">
                        <IconButton
                          onClick={() => handleDelete(row.code)}
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

      <Dialog
        open={openModal}
        onClose={handleModalClose}
        fullWidth
        maxWidth="sm"
      >
        <DialogTitle>태그 등록</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="코드"
            fullWidth
            variant="outlined"
            value={code}
            onChange={(e) => setCode(e.target.value)}
          />
          <TextField
            margin="dense"
            label="레이블"
            fullWidth
            variant="outlined"
            value={label}
            onChange={(e) => setLabel(e.target.value)}
          />
          <TextField
            margin="dense"
            label="사용자 설명"
            fullWidth
            variant="outlined"
            value={userDesc}
            onChange={(e) => setUserDesc(e.target.value)}
          />
          <TextField
            margin="dense"
            label="LLM 설명"
            fullWidth
            variant="outlined"
            value={llmDesc}
            onChange={(e) => setLlmDesc(e.target.value)}
          />
          <TextField
            margin="dense"
            label="색상 (Hex)"
            fullWidth
            variant="outlined"
            type="color"
            value={`#${colorHex}`}
            onChange={(e) => setColorHex(e.target.value.replace("#", ""))}
          />
          <TextField
            margin="dense"
            label="우선순위"
            fullWidth
            variant="outlined"
            type="number"
            value={priorityWeight}
            onChange={(e) => setPriorityWeight(e.target.value)}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleModalClose}>취소</Button>
          <Button onClick={handleSubmit} variant="contained" color="primary">
            제출
          </Button>
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
    </AdminPage>
  );
}
