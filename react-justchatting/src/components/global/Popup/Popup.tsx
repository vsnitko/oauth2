import React from 'react';
import "./Popup.scss"
import {OpenableComponentType} from "../DefaultComponentType.ts";
import clsx from "clsx";
import {useTransitionRender} from "../hook/useTransitionRender.ts";

const Popup: React.FC<OpenableComponentType> = ({trigger, children}) => {

  const {elementCreated, setOpened, elementVisible, refElementToRender} = useTransitionRender();
  const hideTime = 500;
  let hideTimeout: number = 0;

  const cancelHiding = () => {
    clearTimeout(hideTimeout);
  }

  const scheduleHiding = () => {
    hideTimeout = setTimeout(() => {
      setOpened(false);
    }, hideTime)
  }

  return (
    <div
      className="popup-container"
      onMouseEnter={cancelHiding}
      onMouseLeave={scheduleHiding}
    >
      {trigger && React.cloneElement(trigger, {
        onClick: () => setOpened(true)
      })}
      {elementCreated && <ul
          ref={refElementToRender}
          className={clsx('popup', elementVisible && "visible")}
      >
        {children}
      </ul>}
    </div>
  );
}

export default Popup;
