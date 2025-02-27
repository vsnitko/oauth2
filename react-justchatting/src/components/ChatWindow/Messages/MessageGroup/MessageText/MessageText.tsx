import React from "react";
import "./MessageText.css";
import { MessageType } from "../../types.ts";

type MessageComponent = {
  message: MessageType,
  firstMessageFromSender: boolean,
  lastMessageFromSender: boolean,
};

const MessageText: React.FC<MessageComponent> = (
  {
    message,
    firstMessageFromSender,
    lastMessageFromSender
  }
) => {
  const style: React.CSSProperties = {
    backgroundColor: message.mine ? "#ed64a6" : undefined,
    color: message.mine ? "white" : undefined,
    borderBottomRightRadius: message.mine && lastMessageFromSender ? 0 : undefined,
    borderBottomLeftRadius: !message.mine && lastMessageFromSender ? 0 : undefined
  };

  return (
    <div
      className="message"
      style={style}
    >
      {!message.mine && firstMessageFromSender &&
       <div className="sender-name">
         <span className="pointer">{message.senderName}</span>
       </div>
      }
      <span className="message-text">
        {message.messageText}
      </span>
    </div>
  );
};

export default MessageText;
