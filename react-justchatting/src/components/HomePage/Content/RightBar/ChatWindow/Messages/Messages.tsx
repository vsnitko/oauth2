import React, {useEffect, useState} from 'react';
import "./Messages.css"

type Message = {
    senderId: string, senderName: string, avatarLink: string | undefined, messageText: string; mine: boolean;
}

export default function Messages() {

    let allMessages: Message[] = [{
        senderId: "1",
        senderName: "Сонечка",
        avatarLink: "/ТОПОПРОВОДНИК.jpg",
        messageText: "Ты мой топопровопровопроводник1",
        mine: false,
    }, {
        senderId: "1",
        senderName: "Сонечка",
        avatarLink: "/ТОПОПРОВОДНИК.jpg",
        messageText: "Ты мой топопровопровопроводник",
        mine: false,
    }, {
        senderId: "2",
        senderName: "Писечка",
        avatarLink: "/ТОПОПРОВОДНИК.jpg",
        messageText: "Ты мой топопровопровопFDsdfjusdfhАЫВАЫВАЫВАЫАроводник",
        mine: false,
    }, {
        senderId: "3",
        senderName: "Я",
        avatarLink: "",
        messageText: "НЕт ТЫ НЕт ТЫНЕт ТЫ НЕт ТЫН Ет ТЫНЕт ТЫ",
        mine: true,
    }, {
        senderId: "2",
        senderName: "Писечка",
        avatarLink: "/ТОПОПРОВОДНИК.jpg",
        messageText: "Ты мой топопропопровопровАВВВВВВВВВВВВВВВВВВВВВо пFDsdFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdовопровопроводник",
        mine: false,
    }, {
        senderId: "2",
        senderName: "Писечка",
        avatarLink: "/ТОПОПРОВОДНИК.jpg",
        messageText: "Ты мой топопропопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdовопровопроводник",
        mine: false,
    }, {
        senderId: "2",
        senderName: "Писечка",
        avatarLink: "/ТОПОПРОВОДНИК.jpg",
        messageText: "Ты мой топопропопровопровАВВВВВВВВВВВВВВВВВВВВВо пFDsdFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdовопровопроводник",
        mine: false,
    }, {
        senderId: "2",
        senderName: "Писечка",
        avatarLink: "/ТОПОПРОВОДНИК.jpg",
        messageText: "Ты мой топопропопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdовопровопроводник",
        mine: false,
    }, {
        senderId: "2",
        senderName: "Писечка",
        avatarLink: "/ТОПОПРОВОДНИК.jpg",
        messageText: "Ты мой топопропопровопровАВВВВВВВВВВВВВВВВВВВВВо пFDsdFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdовопровопроводник",
        mine: false,
    }, {
        senderId: "2",
        senderName: "Писечка",
        avatarLink: "/ТОПОПРОВОДНИК.jpg",
        messageText: "Ты мой топопропопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdовопровопроводник",
        mine: false,
    }, {
        senderId: "2",
        senderName: "Писечка",
        avatarLink: "/ТОПОПРОВОДНИК.jpg",
        messageText: "Ты мой топопропопровопровАВВВВВВВВВВВВВВВВВВВВВо пFDsdFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdовопровопроводник",
        mine: false,
    }, {
        senderId: "2",
        senderName: "Писечка",
        avatarLink: "/ТОПОПРОВОДНИК.jpg",
        messageText: "Ты мой топопропопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdопопровопровопFDsdовопровопроводник",
        mine: false,
    }]

    const [messageGroups, setMessageGroups] = useState<Array<Array<Message>>>([[]]);

    useEffect(() => {
        let tempMessageGroups: Array<Array<Message>> = [[]];
        let j = 0;
        for (const message of allMessages) {
            if (tempMessageGroups[j] && tempMessageGroups[j][tempMessageGroups[j].length - 1]?.senderId === message.senderId || tempMessageGroups[j].length === 0) {
                tempMessageGroups[j].push(message);
            } else {
                tempMessageGroups.push([message]);
                j++;
            }

        }
        setMessageGroups(tempMessageGroups);
    }, []);

    return (<div className="messages-centered">
            <div className="messages">
                {messageGroups.map((messageGroup, messageGroupIndex) => {
                    return <div className="message-group" style={messageGroup[0]?.mine ? {alignSelf: "flex-end"} : {}}>
                        {!messageGroup[0]?.mine &&
                            <img className="chat-avatar avatar-box" src={messageGroup[0]?.avatarLink} alt="avatar"/>}
                        <div className="text-group">
                            {messageGroup.map((message, messageIndex) => {
                                let style: React.CSSProperties = {}
                                if (message.mine) {
                                    style = {
                                        ...style,
                                        borderBottomRightRadius: 0,
                                        backgroundColor: "#ed64a6",
                                        color: "white"
                                    }
                                } else {
                                    if (messageIndex === 0) {
                                        style = {...style, borderBottomLeftRadius: 0}
                                    }
                                }
                                return <div className="message-container" key={messageGroupIndex + messageIndex}>
                                    <div className="message" style={style}>
                                        {!message.mine && messageIndex === messageGroup.length - 1 &&
                                            <p className="sender-name">
                                                {message.senderName}
                                            </p>}
                                        <span className="message-text">
                                        {message.messageText} {messageGroupIndex}{messageIndex}
                                    </span>
                                    </div>
                                </div>;
                            })}
                        </div>
                    </div>;
                })}
            </div>
        </div>
    );
}
