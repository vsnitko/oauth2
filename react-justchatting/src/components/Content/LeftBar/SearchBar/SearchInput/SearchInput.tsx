import React from "react";
import "./SearchInput.scss";

const SearchInput = () => (
  <div className="input-box search-input-box">
    <img
      src="/search.svg"
      alt="logo"
    />
    <input
      type="text"
      placeholder="Search"
    />
  </div>
);

export default SearchInput;
