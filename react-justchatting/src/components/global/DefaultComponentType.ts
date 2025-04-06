import React, {ReactElement, ReactNode, RefObject} from "react";

export type DefaultComponentType = {
  children: ReactNode;
  className?: string;
  onClick?: () => void;
}

export type OpenableComponentType = Omit<DefaultComponentType, 'onClick'> & {
  trigger?: ReactElement<{
    onClick?: React.MouseEventHandler;
  }>;
}
