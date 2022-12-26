import jwtDecode from "jwt-decode";
import {User} from "../model/User";

export const jwtToUser = (jwt: string): User => {
  const decoded: any = jwtDecode(jwt);
  return {
    id: decoded.sub,
    name: decoded.name,
    email: decoded.email,
    avatar: decoded.avatar
  };
};
