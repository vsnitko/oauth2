import React from "react";
import "./SearchInput.scss";

const SearchInput = () => (
  <div className="input-box search-box">
    <img
      src="/search.svg"
      alt="logo"
    />
    <input
      className="input"
      type="text"
      placeholder="Search"
    />
  </div>
);

export default SearchInput;
