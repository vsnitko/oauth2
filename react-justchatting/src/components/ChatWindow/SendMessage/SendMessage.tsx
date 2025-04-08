import React, {useEffect, useRef, useState} from "react";
import "./SendMessage.scss";
import SockJS from "sockjs-client";
import {Client} from "@stomp/stompjs";
import {useChatStore} from "../../store/chatStore.ts";
import {MessageType} from "../Messages/types.ts";
import {useUserStore} from "../../store/userStore.ts";

const SendMessage: React.FC<{
  setMessages: React.Dispatch<React.SetStateAction<Array<MessageType>>>
}> = (
  {
    setMessages,
  }
) => {

  const [message, setMessage] = useState("");
  const clientRef = useRef<Client>(null);
  const {selectedChat} = useChatStore();
  const {user} = useUserStore();

  useEffect(() => {
    const socket = new SockJS(import.meta.env.VITE_API_URL + '/ws');
    const stompClient = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: () => {
        console.log('Connected');

        stompClient.subscribe('/topic/messages', (messageJson) => {
          if (messageJson.body) {
            const message: MessageType = JSON.parse(messageJson.body);
            setMessages(prev => [...prev, {
              senderId: message.senderId,
              senderName: message.senderName,
              avatar: message.avatar,
              messageText: message.messageText,
              mine: user?.id === message.senderId
            }])
            console.log(message);
          }
        });
      },
      onStompError: (frame) => {
        console.error('STOMP Error', frame);
      },
    });

    stompClient.activate();
    clientRef.current = stompClient;

    return () => {
      stompClient.deactivate();
    };
  }, [user]);

  const sendMessage = (e: React.KeyboardEvent) => {
    if (e.key !== 'Enter') {
      return;
    }
    clientRef.current!.publish({
      destination: '/app/send',
      body: JSON.stringify({
        chatId: selectedChat?.id,
        messageText: message
      }),
    });
    setMessage("")
  }

  return (
    <div className="send-message-padding">
      <div className="send-message-input-box input-box">
        <input
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          onKeyUp={sendMessage}
          type="text"
          placeholder="Message"
        />
      </div>
    </div>
  );
};

export default SendMessage;
