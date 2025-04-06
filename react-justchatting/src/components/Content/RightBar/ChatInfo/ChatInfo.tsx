import React, {useEffect} from "react";
import "./ChatInfo.scss";
import {useChatStore} from "../../../store/chatStore.ts";

const ChatInfo: React.FC<{ chatId: number }> = ({chatId}) => {

  const {chatList, selectedChat, setSelectedChat} = useChatStore();

  useEffect(() => {
    const chat = chatList.find(chat => chat.id == chatId);
    if (chat) {
      setSelectedChat(chat);
    }
  }, [chatList, selectedChat, chatId]);

  return (
    <div className="chat-info">
      <img
        className="chat-info-avatar"
        src={import.meta.env.VITE_FILE_PATH + selectedChat?.avatar}
        alt="chat-avatar"
      />
      <div className="chat-preview">
        <div className="chat-info-top-line chat-title">
          {selectedChat?.name}
        </div>
        <div className="chat-info-bottom-line members-number">
          subscribers
        </div>
      </div>
    </div>
  );
};

export default ChatInfo;
