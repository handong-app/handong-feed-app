import { useRecoilValue } from "recoil";
import { serverRootUrl } from "../constants";
import { authJwtAtom } from "../recoil/authAtom";
import { useMemo } from "react";

export const fetchBe = (jwtValue, path, method = "GET", body) =>
  new Promise((res, rej) => {
    const initStuff = {
      headers: {},
      method,
    };
    if (body && !["GET", "HEAD"].includes(method)) {
      if (body instanceof FormData) {
        initStuff["body"] = body;
      } else {
        initStuff.headers["Content-Type"] = "application/json";
        initStuff["body"] = JSON.stringify(body);
      }
    }
    if (jwtValue) initStuff.headers.Authorization = `Bearer ${jwtValue}`;

    fetch(serverRootUrl + path, initStuff)
      .then((doc) => {
        if (doc.status === 401) {
          // user not logged in
          localStorage.clear();
          window.location.href = "/land"; // back to home screen.
          return rej({ errorMsg: "로그인을 다시해주세요." });
        }
        doc
          .json()
          .then((json) => {
            // If User not exist (due to db reset, etc)
            if (path === "/user/get" && !json?.email) {
              alert("유저가 존재하지 않습니다. 로그인을 다시해주세요.");
              localStorage.clear();
              window.location.reload();
              return rej({
                errorMsg: "유저가 존재하지 않습니다. 로그인을 다시해주세요.",
              });
            }
            return res(json);
          })
          .catch(() => res(null));
      })

      .catch((err) => rej(err));
  });

export const useFetchBe = () => {
  const jwtValue = useRecoilValue(authJwtAtom);
  return useMemo(
    () =>
      (path, method = "GET", body) =>
        fetchBe(jwtValue, path, method, body),
    [jwtValue]
  );
};
