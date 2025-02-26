import React from 'react';
import Button from "../../reuse/Button.tsx";
import "./Navbar.css"

export default function Navbar() {

    return (
        <div className="navbar">
            <div className="navbar-inner">
                <img className="logo-with-text" src="/logo-with-text.svg" alt="logo"/>
                <div className="sign-in">
                    <Button>
                        Sign In
                    </Button>
                </div>
            </div>
        </div>
    );
}
