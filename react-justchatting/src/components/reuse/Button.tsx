import React, {ReactNode} from 'react';
import "./Button.css"

interface ButtonType {
    children: ReactNode,
    className?: string
}

export default function Button({children, className}: ButtonType) {

    return (
        <button className={"button " + className}>
            {children}
        </button>
    );
}
