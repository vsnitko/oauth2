import React from "react";
import "./RightBar.scss";
import ChatInfo from "./ChatInfo/ChatInfo.tsx";
import ChatWindow from "../../ChatWindow/ChatWindow.tsx";

const RightBar: React.FC<{ chatId: number }> = ({chatId}) => {

  return (
    <div className="right-bar">
      <ChatInfo chatId={chatId} />
      <ChatWindow chatId={chatId} />
    </div>
  );
};

export default RightBar;
