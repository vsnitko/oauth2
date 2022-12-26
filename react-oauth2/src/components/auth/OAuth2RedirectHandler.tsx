import React, { useEffect, useLayoutEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { useAppDispatch } from "../../hooks/redux";
import { signIn } from "../../utils/AuthUtils";

export default function OAuth2RedirectHandler() {
  const [searchParams] = useSearchParams();

  let navigate = useNavigate();
  const dispatch = useAppDispatch();

  useLayoutEffect(() => {
    const token = searchParams.get("token");
    if (token !== null) {
      signIn(token, dispatch);
    }
  });

  useEffect(() => {
    navigate("/");
  });

  return <></>;
}
