import React from "react";
import "./MessageGroup.css";
import { MessageGroupType, MessageType } from "../types.ts";
import MessageText from "./MessageText/MessageText.tsx";
import AvatarBox from "./AvatarBox/AvatarBox.tsx";

const MessageGroup: React.FC<MessageGroupType> = (
  {
    mine,
    avatarLink,
    messageGroup
  }
) => (
  <div
    className="message-group"
    style={mine ? { alignSelf: "flex-end" } : {}}
  >
    <AvatarBox
      mine={mine}
      avatarLink={avatarLink}
    />
    <div
      className="text-group"
      style={mine ? { alignItems: "flex-end" } : {}}
    >
      {messageGroup.map((message: MessageType, messageIndex: number) =>
        <MessageText
          message={message}
          firstMessageFromSender={messageIndex === 0}
          lastMessageFromSender={messageIndex === messageGroup.length - 1}
          key={messageIndex}
        />
      )}
    </div>
  </div>
);

export default MessageGroup;
