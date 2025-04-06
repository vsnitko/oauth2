import React from "react";
import "./Content.css";
import LeftBar from "./LeftBar/LeftBar.tsx";
import RightBar from "./RightBar/RightBar.tsx";
import {Route} from "wouter";
import Oauth2Redirect from "./Oauth2Redirect.tsx";

const Content = () => (
  <div className="content-wrapper">
    <LeftBar />
    <Route path="/:chatId">
      {params => <RightBar chatId={Number(params.chatId)} />}
    </Route>
    <Route path="/oauth2/redirect">
      <Oauth2Redirect />
    </Route>
  </div>
);

export default Content;
