import React from "react";
import "./SearchBar.scss";
import SearchInput from "./SearchInput/SearchInput.tsx";
import Button from "../../../global/Button/Button.tsx";
import Modal from "../../../global/Modal/Modal.tsx";
import {useTransitionRender} from "../../../global/hook/useTransitionRender.ts";
import NewRoom from "./NewRoom/NewRoom.tsx";

const SearchBar = () => {
  
  return (
    <div className="search-bar">
      <SearchInput />
      <NewRoom />
    </div>
  );
};

export default SearchBar;
