import React from 'react';
import "./UserAvatar.scss"
import api from "../../../axios-spring.ts";
import {UserStore} from "../../store/userStore.ts";
import Popup from "../../global/Popup/Popup.tsx";
import Li from "../../global/Popup/Li.tsx";

const UserAvatar: React.FC<UserStore> = ({user, setUser}) => {

  const logout = () => {
    api
      .post("/logout")
      .then((res) => {
        setUser(null);
      })
  }

  return (
    <Popup
      trigger={
        <img
          className="user-avatar"
          src={user!.avatar}
          alt="user-avatar"
          referrerPolicy="no-referrer"
        />
      }
    >
      <Li>
        Edit
      </Li>
      <Li onClick={logout}>
        Logout
      </Li>
    </Popup>
  );
}

export default UserAvatar;
