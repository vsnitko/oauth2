import React from "react";
import "./AvatarBox.css";
import { MessageType } from "../../types";

const AvatarBox = (
  {
    mine,
    avatar
  }: Pick<MessageType, "mine" | "avatar">
) => (
  <div className="avatar-box">
    {!mine &&
     <img
       className="chat-avatar"
       src={avatar}
       alt="avatar"
     />
    }
  </div>
);

export default AvatarBox;
