import React, { ReactNode } from "react";
import "./Button.scss";
import {DefaultComponentType} from "../DefaultComponentType.ts";
import clsx from "clsx";

const Button: React.FC<DefaultComponentType> = (
  {
    children,
    className,
    onClick
  }
) => {
  return <button className={clsx('button', className)} onClick={onClick}>
    {children}
  </button>;
};
  
export default Button;
