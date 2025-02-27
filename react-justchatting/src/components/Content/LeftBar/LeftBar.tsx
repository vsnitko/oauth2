import React, { useRef, useState } from "react";
import SearchBar from "./SearchBar/SearchBar.tsx";
import ChatList from "./ChatList/ChatList.tsx";
import "./LeftBar.scss";

const LeftBar = () => {

  const [width, setWidth] = useState<number>();
  const leftBarRef = useRef<HTMLDivElement>(null!);
  const isResizing = useRef(false);

  const horizontalResize = (e: React.MouseEvent) => {
    e.preventDefault();
    isResizing.current = true;

    const initialX = e.clientX;
    const initialWidth = leftBarRef.current.offsetWidth;

    const handleMouseMove = (moveEvent: MouseEvent) => {
      if (isResizing.current) {
        const newWidth = initialWidth + (
          moveEvent.clientX - initialX
        );
        setWidth(newWidth);
      }
    };

    const handleMouseUp = () => {
      isResizing.current = false;
      document.removeEventListener("mousemove", handleMouseMove);
      document.removeEventListener("mouseup", handleMouseUp);
      document.body.style.cursor = "default";
    };

    document.addEventListener("mousemove", handleMouseMove);
    document.addEventListener("mouseup", handleMouseUp);
    document.body.style.cursor = "ew-resize";
  };


  return (
    <div
      className="left-bar"
      ref={leftBarRef}
      style={{ width: `${width}px` }}
    >
      <SearchBar />
      <ChatList />
      <div
        className="resizer"
        onMouseDown={horizontalResize}
      />
    </div>
  );
};

export default LeftBar;
