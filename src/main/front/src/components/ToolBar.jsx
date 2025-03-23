import {
  Box,
  Button,
  IconButton,
  InputAdornment,
  styled,
  TextField,
} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import React from "react";
import PropTypes from "prop-types";

const CssTextField = styled(TextField)({
  "& label.Mui-focused": {
    color: "#272727",
  },
  "& .MuiOutlinedInput-root": {
    "&": {
      paddingRight: 4,
    },
    "& fieldset": {
      borderColor: "#A0AAB4",
    },
    "&:hover fieldset": {
      borderColor: "#272727",
    },
    "&.Mui-focused fieldset": {
      borderColor: "#272727",
    },
  },
});

function ToolBar({ doSearch }) {
  const [search, setSearch] = React.useState("");
  return (
    <div>
      <Box display={"flex"} justifyContent={"space-between"} alignItems="end">
        <Box></Box>
        <Box display="flex" gap={1}>
          <CssTextField
            hiddenLabel
            // variant="filled"
            size="small"
            color="transparent"
            placeholder="검색어를 입력하세요"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                doSearch(search);
              }
            }}
            slotProps={{
              input: {
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton
                      aria-label="search"
                      size="small"
                      onClick={() => doSearch(search)}
                    >
                      <SearchIcon />
                    </IconButton>
                  </InputAdornment>
                ),
              },
            }}
          />

          {/* <Button variant="contained" size="small" color="#373C45">
            검색
          </Button> */}
        </Box>
      </Box>
    </div>
  );
}

ToolBar.propTypes = {
  doSearch: PropTypes.func.isRequired,
};

export default ToolBar;
