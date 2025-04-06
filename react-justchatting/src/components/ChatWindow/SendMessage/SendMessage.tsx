import React from "react";
import "./SendMessage.scss";

const SendMessage = () => (
  <div className="send-message-padding">
    <div className="send-message-input-box input-box">
      <input
        type="text"
        placeholder="Message"
      />
    </div>
  </div>
);

export default SendMessage;
