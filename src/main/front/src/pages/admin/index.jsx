import PeopleIcon from "@mui/icons-material/People";
import FeedIcon from "@mui/icons-material/Feed";
import InsertDriveFileIcon from "@mui/icons-material/InsertDriveFile";
import SmartToyIcon from "@mui/icons-material/SmartToy";
import SellIcon from "@mui/icons-material/Sell";

import UsersTable from "./UsersTable";
import AdminFeed from "./AdminFeed";
import AdminFiles from "./AdminFiles";
import AdminApiKeys from "./AdminApiKeys";
import AdminTags from "./AdminTags";

export const ADMINMENU = [
  {
    title: "이용자 관리",
    icon: <PeopleIcon />,
    id: "users",
    comp: UsersTable,
  },
  {
    title: "게시글 관리",
    icon: <FeedIcon />,
    id: "posts",
    comp: AdminFeed,
  },
  {
    title: "파일 관리",
    icon: <InsertDriveFileIcon />,
    id: "files",
    comp: AdminFiles,
  },
  {
    title: "태그 관리",
    icon: <SellIcon />,
    id: "tags",
    comp: AdminTags,
  },
  {
    title: "APIKeys 관리",
    icon: <SmartToyIcon />,
    id: "apikeys",
    comp: AdminApiKeys,
  },
];
