import React, { useEffect, useState } from "react";
import "./Messages.scss";
import { MessageType } from "./types.ts";
import MessageGroup from "./MessageGroup/MessageGroup.tsx";

const Messages = () => {
  const allMessages: MessageType[] = [
    {
      senderId: "1",
      senderName: "Shelly",
      avatarLink: "/shelly.jpg",
      messageText: "провопровопроводник1",
      mine: false
    }, {
      senderId: "1",
      senderName: "Shelly",
      avatarLink: "/shelly.jpg",
      messageText: "провопровопроводник",
      mine: false
    }, {
      senderId: "2",
      senderName: "ТОП",
      avatarLink: "/ТОПОПРОВОДНИК.jpg",
      messageText: "fjusdfhАЫВАЫВАЫВАЫАроводник",
      mine: false
    }, {
      senderId: "3",
      senderName: "Я",
      avatarLink: "",
      messageText: "НЕт ТЫ НЕт ТЫНЕт ТЫ НЕт ТЫН Ет ТЫНЕт ТЫ",
      mine: true
    }, {
      senderId: "2",
      senderName: "ТОП",
      avatarLink: "/ТОПОПРОВОДНИК.jpg",
      messageText: "ВВВВВВВВВВВВВВВВВВо пFDsdFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdовопровопроводник",
      mine: false
    }, {
      senderId: "2",
      senderName: "ТОП",
      avatarLink: "/ТОПОПРОВОДНИК.jpg",
      messageText: "попровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdовопровопроводник",
      mine: false
    }, {
      senderId: "2",
      senderName: "ТОП",
      avatarLink: "/ТОПОПРОВОДНИК.jpg",
      messageText: "овопровАВВВВВВВВВВВВВВВВВВВВВо пFDsdFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdовопровопроводник",
      mine: false
    }, {
      senderId: "3",
      senderName: "Я",
      avatarLink: "",
      messageText: "АААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААААА",
      mine: true
    }, {
      senderId: "3",
      senderName: "Я",
      avatarLink: "",
      messageText: "ю",
      mine: true
    }, {
      senderId: "2",
      senderName: "ТОП",
      avatarLink: "/ТОПОПРОВОДНИК.jpg",
      messageText: "ровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdовопровопроводник",
      mine: false
    }, {
      senderId: "1",
      senderName: "Shelly",
      avatarLink: "/shelly.jpg",
      messageText: "ровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdовопровопроводник",
      mine: false
    }, {
      senderId: "1",
      senderName: "Shelly",
      avatarLink: "/shelly.jpg",
      messageText: "ровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdовопровопроводник",
      mine: false
    }, {
      senderId: "1",
      senderName: "Shelly",
      avatarLink: "/shelly.jpg",
      messageText: "ровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdовопровопроводник",
      mine: false
    }
  ];

  const [messageGroups, setMessageGroups] = useState<Array<Array<MessageType>>>([[]]);

  useEffect(() => {
    const tempMessageGroups: Array<Array<MessageType>> = [[]];
    let j = 0;
    for (const message of allMessages) {
      const lastMessage = tempMessageGroups[j][tempMessageGroups[j].length - 1];
      const firstMessageFromSender = lastMessage?.senderId === message.senderId;
      if (firstMessageFromSender || tempMessageGroups[j].length === 0) {
        tempMessageGroups[j].push(message);
      } else {
        tempMessageGroups.push([message]);
        j++;
      }
    }
    setMessageGroups(tempMessageGroups);
  }, []);

  return (
    <div className="messages">
      {messageGroups.map((messageGroup: MessageType[], messageGroupIndex: number) =>
        <MessageGroup
          mine={messageGroup[0]?.mine}
          avatarLink={messageGroup[0]?.avatarLink}
          messageGroup={messageGroup}
          key={messageGroupIndex}
        />)}
    </div>
  );
};

export default Messages;
