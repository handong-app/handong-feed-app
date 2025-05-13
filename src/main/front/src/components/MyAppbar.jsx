import { useState } from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Menu from "@mui/material/Menu";
import Container from "@mui/material/Container";
import MenuItem from "@mui/material/MenuItem";
import FeedIcon from "@mui/icons-material/Feed";
import GitHubIcon from "@mui/icons-material/GitHub";
import InfoIcon from "@mui/icons-material/Info";

import { IconButton, Slide, Tooltip, useScrollTrigger } from "@mui/material";

const settings = ["Profile", "Account", "Dashboard", "Logout"];

function HideOnScroll(props) {
  const { children, window } = props;
  // Note that you normally won't need to set the window ref as useScrollTrigger
  // will default to window.
  // This is only being set here because the demo is in an iframe.
  const trigger = useScrollTrigger({
    target: window ? window() : undefined,
  });

  return (
    <Slide appear={false} direction="down" in={!trigger}>
      {children ?? <div />}
    </Slide>
  );
}

function MyAppbar() {
  const [anchorElUser, setAnchorElUser] = useState(null);

  // eslint-disable-next-line unused-imports/no-unused-vars
  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  return (
    <HideOnScroll>
      <AppBar
        position="fixed"
        sx={{ zIndex: 99, backgroundColor: "white", color: "black" }}
      >
        <Container maxWidth="xl">
          <Toolbar disableGutters sx={{ justifyContent: "center" }}>
            <Typography
              variant="h6"
              noWrap
              component="a"
              href="#app-bar-with-responsive-menu"
              sx={{
                mr: 2,
                display: {
                  xs: "flex",
                  // xs: "none",
                  // md: "flex"
                },
                // fontFamily: "monospace",
                fontWeight: 700,
                letterSpacing: ".3rem",
                color: "inherit",
                textDecoration: "none",
              }}
            >
              한동피드
            </Typography>

            <Box
              sx={{
                flexGrow: 0,
                position: "absolute",
                display: "flex",
                right: 0,
              }}
            >
              <Tooltip title="Github">
                <IconButton
                  color="inherit"
                  component="a"
                  href="https://github.com/orgs/handong-app/repositories?q=handong-feed"
                  target="_blank"
                  rel="noopener noreferrer"
                  aria-label="Github"
                >
                  <GitHubIcon />
                </IconButton>
              </Tooltip>
              <Tooltip title="Website">
                <IconButton
                  color="inherit"
                  component="a"
                  href="https://board.handong.app/feed"
                  target="_blank"
                  rel="noopener noreferrer"
                  aria-label="Website"
                >
                  <InfoIcon />
                </IconButton>
              </Tooltip>
            </Box>

            {/* <Box
            sx={{
              flexGrow: 1,
              display: {
                xs: "flex",
                // xs: "none",
                // md: "flex",
              },
            }}
          ></Box> */}
            <Box sx={{ flexGrow: 0 }}>
              {/* <Tooltip title="Open settings">
              <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                <Avatar alt="Remy Sharp" src="/static/images/avatar/2.jpg" />
              </IconButton>
            </Tooltip> */}
              <Menu
                sx={{ mt: "45px" }}
                id="menu-appbar"
                anchorEl={anchorElUser}
                anchorOrigin={{
                  vertical: "top",
                  horizontal: "right",
                }}
                keepMounted
                transformOrigin={{
                  vertical: "top",
                  horizontal: "right",
                }}
                open={Boolean(anchorElUser)}
                onClose={handleCloseUserMenu}
              >
                {settings.map((setting) => (
                  <MenuItem key={setting} onClick={handleCloseUserMenu}>
                    <Typography sx={{ textAlign: "center" }}>
                      {setting}
                    </Typography>
                  </MenuItem>
                ))}
              </Menu>
            </Box>
          </Toolbar>
        </Container>
      </AppBar>
    </HideOnScroll>
  );
}
export default MyAppbar;
