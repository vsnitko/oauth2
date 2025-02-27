import React from "react";
import "./Content.css";
import LeftBar from "./LeftBar/LeftBar.tsx";
import RightBar from "./RightBar/RightBar.tsx";

const Content = () => (
  <div className="content-wrapper">
    <LeftBar />
    <RightBar />
  </div>
);

export default Content;
