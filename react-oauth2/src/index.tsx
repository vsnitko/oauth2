import React from "react";
import ReactDOM from "react-dom/client";
import {App} from "./App";
import {Provider} from "react-redux";
import {setupStore} from "./store/store";
import {BrowserRouter} from "react-router-dom";
import {ChakraProvider} from "@chakra-ui/react";
import axios from "axios";
import {config} from "./properties";

const root = ReactDOM.createRoot(document.getElementById("root") as HTMLElement);

axios.defaults.baseURL = config.backendPath;
axios.defaults.headers.common["Authorization"] = localStorage.getItem("access-token") || "";

root.render(
  <Provider store={setupStore()}>
    <BrowserRouter>
      <ChakraProvider>
        <App />
      </ChakraProvider>
    </BrowserRouter>
  </Provider>
);
