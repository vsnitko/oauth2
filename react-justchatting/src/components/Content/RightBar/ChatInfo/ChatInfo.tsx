import React from "react";
import "./ChatInfo.scss";

const ChatInfo = () => (
  <div className="chat-info">
    <img
      className="chat-info-avatar"
      src="/shelly.jpg"
      alt="chat-avatar"
    />
    <div className="chat-preview">
      <div className="chat-info-top-line chat-title">
        Shelly
      </div>
      <div className="chat-info-bottom-line members-number">
        2 members
      </div>
    </div>
  </div>
);

export default ChatInfo;
