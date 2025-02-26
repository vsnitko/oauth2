import './App.css'
import {Route, Switch} from "wouter";
import UserEdit from "./components/UserEdit.tsx";
import User from "./components/User.tsx";
import Page404 from "./components/Page404.tsx";
import HomePage from "./components/HomePage/HomePage.tsx";
import React from "react";

function App() {

    return (
        <>
            <Switch>
                <Route path="/" component={HomePage}/>
                <Route path="/user" nest>
                    <Route path="/:id">
                        {params => <User id={params.id}/>}
                    </Route>
                    <Route path="/edit" component={UserEdit}/>
                </Route>
                <Route component={Page404}/>
            </Switch>
        </>
    )
}

export default App
