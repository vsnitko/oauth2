import React from 'react';
import "./SearchInput.css"

export default function SearchInput() {

    return (
        <div className="input-box search-box">
            <img className="search-icon" src="/search.svg" alt="logo"/>
            <input className="input" type="text" placeholder="Search" />
        </div>
    );
}
