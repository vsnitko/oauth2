import React from "react";
import "./SearchBar.scss";
import SearchInput from "./SearchInput/SearchInput.tsx";
import Button from "../../../global/Button.tsx";

const SearchBar = () => (
  <div className="search-bar">
    <SearchInput />
    <Button className="new-room-button">
      <img
        className="new-room-img"
        src="/new-room.svg"
        alt="logo"
      />
      New Room
    </Button>
  </div>
);

export default SearchBar;
