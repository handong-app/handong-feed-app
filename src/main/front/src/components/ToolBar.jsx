import {
  Box,
  IconButton,
  InputAdornment,
  styled,
  TextField,
} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import React, { useEffect, useRef, useState } from "react";
import PropTypes from "prop-types";
import useTags from "../hooks/useTags";
import TagChip from "./TagChip";

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
  const [search, setSearch] = useState("");
  const [searchTags, setSearchTags] = useState([]);

  const searchRef = useRef(search);

  const [tags] = useTags();

  useEffect(() => {
    searchRef.current = search;
  }, [search]);

  useEffect(() => {
    doSearch({ squery: searchRef.current, stags: searchTags });
  }, [searchTags]);
  return (
    <Box display={"flex"} justifyContent={"space-between"} alignItems="end">
      <Box>
        {tags.map((tag) => (
          <TagChip
            key={tag.code}
            code={tag.code}
            label={tag.label}
            colorHex={tag.colorHex}
            tint={!searchTags.includes(tag.code)}
            outline={!searchTags.includes(tag.code)}
            onClick={() => {
              setSearchTags((prev) =>
                prev.includes(tag.code)
                  ? prev.filter((code) => code !== tag.code)
                  : [...prev, tag.code]
              );
            }}
          />
        ))}
      </Box>
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
              doSearch({ squery: search, stags: searchTags });
            }
          }}
          slotProps={{
            input: {
              endAdornment: (
                <InputAdornment position="end">
                  <IconButton
                    aria-label="search"
                    size="small"
                    onClick={() =>
                      doSearch({ squery: search, stags: searchTags })
                    }
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
  );
}

ToolBar.propTypes = {
  doSearch: PropTypes.func.isRequired,
};

export default ToolBar;
