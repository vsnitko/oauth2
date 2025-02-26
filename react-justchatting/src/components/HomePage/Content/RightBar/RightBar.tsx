import React from 'react';
import "./RightBar.css"
import ChatInfo from "./ChatInfo/ChatInfo.tsx";
import ChatWindow from "./ChatWindow/ChatWindow.tsx";

export default function RightBar() {

    return (
        <div className="right-bar">
            <ChatInfo/>
            <ChatWindow/>
        </div>
    );
}
