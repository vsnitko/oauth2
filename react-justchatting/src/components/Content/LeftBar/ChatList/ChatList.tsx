import React, {useEffect} from "react";
import "./ChatList.scss";
import {Link} from "wouter";
import api from "../../../../axios-spring.ts";
import {ChatElement, useChatStore} from "../../../store/chatStore.ts";

const ChatList = () => {
  
  const { chatList, setChatList } = useChatStore();

  useEffect(() => {
    api
      .get<Array<ChatElement>>("/chats")
      .then((response) => {
        setChatList(response.data);
      });
  }, []);

  return (
    <div className="chat-list">
      {
        chatList.map((chat: ChatElement, index) =>
          <Link
            to={"/" + chat.id}
            className="chat-element-preview"
            key={index}
          >
            <img
              className="chat-avatar"
              src={import.meta.env.VITE_API_URL + "/media/photo/" + chat.avatar}
              alt="chat-avatar"
            />
            <div className="chat-preview">
              <div className="chat-info-top-line">
                {chat.name}
              </div>
              <div className="chat-info-bottom-line">
                {chat.lastMessage}
              </div>
            </div>
          </Link>
        )
      }
    </div>
  );
};

export default ChatList;
