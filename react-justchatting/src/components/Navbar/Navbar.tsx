import React from "react";
import "./Navbar.scss";
import SignIn from "./SignIn/SignIn.tsx";
import {useUserStore} from "../store/userStore.ts";
import UserAvatar from "./UserAvatar/UserAvatar.tsx";
import DarkModeSwitcher from "./DarkModeSwitcher/DarkModeSwitcher.tsx";

const Navbar = () => {

  const {user, setUser} = useUserStore();

  return (
    <div className="navbar">
      <div className="navbar-inner">
        <img
          className="logo-with-text"
          src="/logo-with-text.svg"
          alt="logo"
        />
        <div className="navbar-right">
          <DarkModeSwitcher />
          {user
            ? <UserAvatar
              user={user}
              setUser={setUser}
            />
            : <SignIn />
          }
        </div>
      </div>
    </div>
  );
};

export default Navbar;
