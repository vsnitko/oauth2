import React from "react";
import "./ChatWindow.scss";
import SendMessage from "./SendMessage/SendMessage.tsx";
import Messages from "./Messages/Messages.tsx";

const ChatWindow = () => (
  <div className="chat-window">
    <div className="messages-window">
      <Messages />
    </div>
    <SendMessage />
  </div>
);

export default ChatWindow;
