import React from 'react';
import "./Content.css"
import LeftBar from "./LeftBar/LeftBar.tsx";
import RightBar from "./RightBar/RightBar.tsx";

export default function Content() {

    return (
        <div className="content-wrapper">
            <div className="content">
                <LeftBar/>
                <RightBar/>
            </div>
        </div>
    );
}
