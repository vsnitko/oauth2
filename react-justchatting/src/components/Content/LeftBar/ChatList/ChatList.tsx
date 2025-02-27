import React, { useState } from "react";
import "./ChatList.scss";

type ChatElement = {
  avatarLink: string | undefined;
  roomName: string;
  message: string | undefined;
}

const ChatList = () => {

  const [chatList, setChatList] = useState<Array<ChatElement>>(new Array(15).fill(
    {
      avatarLink: "/shelly.jpg",
      roomName: "Shelly",
      message: "Головы сияют на моей едкой катане"
    }
  ));
  
  return (
    <div className="chat-list">
      {
        chatList.map((chat: ChatElement, index) =>
          <div
            className="chat-element-preview"
            key={index}
          >
            <img
              className="chat-avatar"
              src={chat.avatarLink}
              alt="chat-avatar"
            />
            <div className="chat-preview">
              <div className="chat-info-top-line">
                {chat.roomName}
              </div>
              <div className="chat-info-bottom-line">
                {chat.message}
              </div>
            </div>
          </div>
        )
      }
    </div>
  );
};

export default ChatList;
