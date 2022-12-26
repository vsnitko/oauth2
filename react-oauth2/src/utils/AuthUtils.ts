import {User} from "../model/User";
import {jwtToUser} from "./JwtUtils";
import axios from "axios";
import {saveUser} from "../store/reducers/UserSlice";
import {NavigateFunction} from "react-router-dom";

export const signIn = (jwt: string, dispatch: any): void => {
  const user: User = jwtToUser(jwt);
  localStorage.setItem("access-token", jwt);
  localStorage.setItem("principal", JSON.stringify(user));
  axios.defaults.headers.common["Authorization"] = jwt;
  dispatch(saveUser(user));
};

export const logout = (dispatch?: any, navigate?: NavigateFunction): void => {
  localStorage.removeItem("principal");
  localStorage.removeItem("access-token");
  delete axios.defaults.headers.common["Authorization"];
  dispatch && dispatch(saveUser(null));
  navigate && navigate("/");
};
