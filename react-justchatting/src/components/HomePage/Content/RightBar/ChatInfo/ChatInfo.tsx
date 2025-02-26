import React from 'react';
import "./ChatInfo.css"

export default function ChatInfo() {

    return (
        <div className="chat-info">
            <img className="chat-avatar chat-info-avatar" src="/ТОПОПРОВОДНИК.jpg" alt="chat-avatar"/>
            <div className="chat-preview">
                <div className="chat-info-top-line chat-title">
                    Сонечка
                </div>
                <div className="chat-info-bottom-line members-number">
                    2 members
                </div>
            </div>
        </div>
    );
}
