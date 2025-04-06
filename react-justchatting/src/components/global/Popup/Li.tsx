import React from 'react';
import "./Popup.scss"
import {DefaultComponentType} from "../DefaultComponentType.ts";
import clsx from "clsx";

const Li: React.FC<DefaultComponentType> = ({children, className, onClick}) => {

  return (
    <li className={clsx('popup-li', className)} onClick={onClick}>
      {children}
    </li>
  );
}

export default Li;
