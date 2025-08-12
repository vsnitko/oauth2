import "./App.css";
import React, {useState} from "react";
import Content from "./components/Content/Content.tsx";
import Navbar from "./components/Navbar/Navbar.tsx";
import Modal from "./components/global/Modal/Modal.tsx";
import UserLoader from "./components/UserLoader/UserLoader.tsx";


function App() {
  return (
    <>
      <UserLoader />
      <Navbar />
      <Content />
    </>
  );
}

export default App;
