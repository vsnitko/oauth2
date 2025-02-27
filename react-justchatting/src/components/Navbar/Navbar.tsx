import React, { useEffect, useState } from "react";
import Button from "../global/Button.tsx";
import "./Navbar.scss";

const Navbar = () => {

  const [isDarkMode, setIsDarkMode] = useState(false);

  const setDarkMode = () => {
    localStorage.setItem("data-theme", "dark");
    document.documentElement.setAttribute("data-theme", "dark");
    setIsDarkMode(true);
  };

  const setLightMode = () => {
    localStorage.setItem("data-theme", "light");
    document.documentElement.setAttribute("data-theme", "light");
    setIsDarkMode(false);
  };

  useEffect(() => {
    let darkMode;
    const dataTheme = localStorage.getItem("data-theme");
    if (dataTheme === null) {
      darkMode = window.matchMedia("(prefers-color-scheme: dark)").matches;
    } else {
      darkMode = dataTheme === "dark";
    }
    if (darkMode) {
      setDarkMode();
    } else {
      setLightMode();
    }
  }, []);

  const toggleTheme = () => {
    const attribute = document.documentElement.getAttribute("data-theme");
    if (attribute === "dark") {
      setLightMode();
    } else {
      setDarkMode();
    }

  };

  return (
    <div className="navbar">
      <div className="navbar-inner">
        <img
          className="logo-with-text"
          src="/logo-with-text.svg"
          alt="logo"
        />
        <div className="navbar-right">
          <Button
            className="secondary"
            onClick={toggleTheme}
          >
            {isDarkMode
              ? <img
                src="/light.svg"
                alt="light"
              />
              : <img
                src="/dark.svg"
                alt="dark"
              />}
          </Button>
          <Button>
            Sign In
          </Button>
        </div>
      </div>
    </div>
  );
};

export default Navbar;
