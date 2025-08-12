import React, {useEffect, useState} from "react";
import "./ChatWindow.scss";
import SendMessage from "./SendMessage/SendMessage.tsx";
import Messages from "./Messages/Messages.tsx";
import api from "../../axios-spring.ts";
import {MessageType} from "./Messages/types.ts";
import {useUserStore} from "../store/userStore.ts";

const ChatWindow: React.FC<{ chatId: number }> = ({chatId}) => {

  const [messages, setMessages] = useState<Array<MessageType>>([])
  const {user} = useUserStore();

  useEffect(() => {
    if (user === undefined) return;
    api
      .get<Array<MessageType>>(`/chat/${chatId}/messages`)
      .then((response) => {
        response.data.forEach(message => {
          message.mine = message.senderId === user?.id;
        });
        setMessages(response.data);
      });
  }, [chatId, user]);

  return (
    <div className="chat-window">
      <div className="messages-window">
        {messages
          && messages.length !== 0
          && <Messages messages={messages} />
        }
      </div>
      <SendMessage
        setMessages={setMessages}
      />
    </div>
  );
};

export default ChatWindow;
