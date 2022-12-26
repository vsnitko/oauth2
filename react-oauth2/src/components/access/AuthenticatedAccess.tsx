import React, {useEffect, useState} from "react";
import axios from "axios";
import {Alert, AlertIcon} from "@chakra-ui/react";

export default function AuthenticatedAccess() {
  let NO_RIGHTS_MESSAGE = "You do not have rights to view this content";
  const [content, setContent] = useState(NO_RIGHTS_MESSAGE);

  useEffect(() => {
    axios.get("check-access/authenticated").then((response) => {
      setContent(response.data);
    });
  }, []);

  return <>
    <Alert status={content === NO_RIGHTS_MESSAGE ? "error" : "success"}>
      <AlertIcon />
      {content}
    </Alert>
  </>;
}
