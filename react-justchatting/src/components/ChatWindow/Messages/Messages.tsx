import React, { useEffect, useState } from "react";
import "./Messages.scss";
import { MessageType } from "./types.ts";
import MessageGroup from "./MessageGroup/MessageGroup.tsx";

const Messages = ({messages}:{messages: Array<MessageType>}) => {

  const [messageGroups, setMessageGroups] = useState<Array<Array<MessageType>>>([[]]);

  useEffect(() => {
    const tempMessageGroups: Array<Array<MessageType>> = [[]];
    let j = 0;
    if (messages === undefined) {
      return;
    }
    for (const message of messages) {
      const lastMessage = tempMessageGroups[j].at(-1);
      const firstMessageFromSender = lastMessage?.senderId === message.senderId;
      if (firstMessageFromSender || tempMessageGroups[j].length === 0) {
        tempMessageGroups[j].push(message);
      } else {
        tempMessageGroups.push([message]);
        j++;
      }
    }
    setMessageGroups(tempMessageGroups);
  }, [messages]);

  return (
    <div className="messages">
      {messageGroups.map((messageGroup: MessageType[], messageGroupIndex: number) =>
        <MessageGroup
          mine={messageGroup[0]?.mine}
          avatar={messageGroup[0]?.avatar}
          messageGroup={messageGroup}
          key={messageGroupIndex}
        />)}
    </div>
  );
};

export default Messages;
