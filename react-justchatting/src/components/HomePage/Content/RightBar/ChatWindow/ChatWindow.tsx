import React from 'react';
import "./ChatWindow.css"
import SendMessage from "./SendMessage/SendMessage.tsx";
import Messages from "./Messages/Messages.tsx";

export default function ChatWindow() {

    return (
        <div className="chat-window">
            <div className="chat-window-messages">
                
                    <Messages/>
                
            </div>
            
                <SendMessage/>
            
            {/*<div className="chat-content">*/}
            {/*    <Messages/>*/}
            {/*    <SendMessage/>*/}
            {/*</div>*/}
        </div>
    );
}
