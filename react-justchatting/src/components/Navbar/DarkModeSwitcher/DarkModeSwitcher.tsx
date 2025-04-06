import React, {useLayoutEffect, useState} from 'react';
import "./DarkModeSwitcher.scss"
import Sun from "../../global/icons/Sun.tsx";
import Moon from "../../global/icons/Moon.tsx";
import Button from "../../global/Button/Button.tsx";

const DarkModeSwitcher = () => {

  const [isDarkMode, setIsDarkMode] = useState<boolean>();
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

  useLayoutEffect(() => {
    let darkMode;
    const dataTheme = localStorage.getItem("data-theme");
    if (!dataTheme) {
      // setting color mode based on system preferences
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
    <Button
      className="navbar-button"
      onClick={toggleTheme}
    >
      {isDarkMode ? <Sun /> : <Moon />}
    </Button>
  );
}

export default DarkModeSwitcher;
