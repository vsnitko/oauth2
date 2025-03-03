import React from "react";
import "./AvatarBox.css";
import { MessageType } from "../../types";

const AvatarBox = (
  {
    mine,
    avatarLink
  }: Pick<MessageType, "mine" | "avatarLink">
) => (
  <div className="avatar-box">
    {!mine &&
     <img
       className="chat-avatar"
       src={avatarLink}
       alt="avatar"
     />
    }
  </div>
);

export default AvatarBox;
