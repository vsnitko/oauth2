import React from 'react';
import "./SearchBar.css"
import SearchInput from "./SearchInput/SearchInput.tsx";
import Button from "../../../../reuse/Button.tsx";

export default function SearchBar() {

    return (
        <div className="search-bar">
            <SearchInput />
            <Button className="new-room-button">
                <img className="new-room-img" src="/new-room.svg" alt="logo"/>
                New Room
            </Button>
            
        </div>
    );
}
