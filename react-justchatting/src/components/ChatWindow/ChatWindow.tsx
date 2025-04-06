import React, {useEffect, useState} from "react";
import "./ChatWindow.scss";
import SendMessage from "./SendMessage/SendMessage.tsx";
import Messages from "./Messages/Messages.tsx";
import api from "../../axios-spring.ts";
import {ChatElement} from "../store/chatStore.ts";
import {MessageType} from "./Messages/types.ts";

const ChatWindow:React.FC<{ chatId: number }> = ({chatId}) => {

  const [messages, setMessages] = useState<Array<MessageType>>()

  useEffect(() => {
    api
      .get<Array<MessageType>>(`/chat/${chatId}/messages`)
      .then((response) => {
        setMessages(response.data);
      });
  }, [chatId]);

  return (
    <div className="chat-window">
      <div className="messages-window">
        {messages && <Messages messages={messages} />}
      </div>
      <SendMessage />
    </div>
  );
};

export default ChatWindow;
