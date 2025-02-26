import React, {useState} from 'react';
import "./ChatList.css";

type ChatElement = {
    avatarLink: string | undefined;
    roomName: string;
    message: string | undefined;
}

export default function ChatList() {

    const [chatList, setChatList] = useState<Array<ChatElement>>(new Array(15).fill(
        {
            avatarLink: "/ТОПОПРОВОДНИК.jpg",
            roomName: "Сонечка",
            message: "Ты мой топопровопровопроводник",
        }
    ));
    
    // const [chatList, setChatList] = useState<Array<ChatElement>>([]);

    return (
        <div className="chat-list">
            {
                chatList.map((chat: ChatElement, index) =>
                    <div className="chat-element-preview" key={index}>
                        <img className="chat-avatar" src={chat.avatarLink} alt="chat-avatar"/>
                        <div className="chat-preview">
                            <div className="chat-info-top-line">
                                {chat.roomName}
                            </div>
                            <div className="chat-info-bottom-line">
                                {chat.message}
                            </div>
                        </div>
                    </div>
                )
            }

        </div>
    );
}
