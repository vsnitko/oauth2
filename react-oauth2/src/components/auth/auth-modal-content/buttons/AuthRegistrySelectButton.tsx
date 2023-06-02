import React, {MouseEventHandler, ReactNode} from "react";
import {Button} from "@chakra-ui/react";

export default function AuthRegistrySelectButton(props: {
  icon: ReactNode;
  text: string;
  onClick?: MouseEventHandler;
}) {
  return (
    <>
      <Button onClick={props.onClick} style={{ width: "100%", marginBottom: 16 }}>
        <div style={{ position: "absolute", left: 12 }}>{props.icon}</div>
        {props.text}
      </Button>
    </>
  );
}
