import React from "react";
import "./RightBar.scss";
import ChatInfo from "./ChatInfo/ChatInfo.tsx";
import ChatWindow from "../../ChatWindow/ChatWindow.tsx";

const RightBar = () => (
  <div className="right-bar">
    <ChatInfo />
    <ChatWindow />
  </div>
);

export default RightBar;
