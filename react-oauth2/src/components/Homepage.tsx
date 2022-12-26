import React, {useEffect, useState} from "react";
import axios from "axios";
import {Alert, AlertIcon} from "@chakra-ui/react";

export default function Homepage() {
  const [content, setContent] = useState("");

  useEffect(() => {
    axios.get("check-access/public").then((response) => {
      setContent(response.data);
    });
  }, []);

  return <div><Alert status="success">
    <AlertIcon />
    {content}
  </Alert></div>;
}
