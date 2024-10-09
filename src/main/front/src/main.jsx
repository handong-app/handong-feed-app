import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { RecoilRoot } from "recoil";
import LoginProtected from "./components/LoginProtected";
import MainScreen from "./pages/MainScreen";

import { register as registerServiceWorker } from "./serviceWorkerRegistration";
import { createTheme, ThemeProvider } from "@mui/material";
import MainFeed from "./pages/MainFeed";
import AllFeed from "./pages/AllFeed";
import FavFeed from "./pages/FavFeed";

const router = createBrowserRouter([
  {
    path: "/land",
    element: <MainScreen />,
  },
  {
    path: "/",
    element: <LoginProtected comp={MainFeed} />,
  },
  {
    path: "/all",
    element: <LoginProtected comp={AllFeed} />,
  },
  {
    path: "/favorite",
    element: <LoginProtected comp={FavFeed} />,
  },
]);

const theme = createTheme({
  typography: {
    fontFamily:
      '-apple-system, BlinkMacSystemFont, "Apple SD Gothic Neo", "Pretendard Variable", Pretendard, Roboto, "Noto Sans KR", "Segoe UI", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;',
  },
});

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <RecoilRoot>
        <RouterProvider router={router} />
      </RecoilRoot>
    </ThemeProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals

registerServiceWorker();
