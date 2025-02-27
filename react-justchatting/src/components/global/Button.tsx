import React, { ReactNode } from "react";
import "./Button.scss";

interface ButtonType {
  children: ReactNode;
  className?: string;
  onClick?: () => void;
}

const Button: React.FC<ButtonType> = (
  {
    children,
    className,
    onClick
  }
) => {
  return <button className={"button " + className} onClick={onClick}>
    {children}
  </button>;
};
  
export default Button;
