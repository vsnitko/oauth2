import React from "react";
import {ModalBody, ModalCloseButton, ModalHeader, Text} from "@chakra-ui/react";
import AuthRegistrySelectButton from "./buttons/AuthRegistrySelectButton";
import {AiOutlineUser} from "react-icons/ai";
import {config} from "../../../properties";
import {FcGoogle} from "react-icons/fc";
import {FaGithub} from "react-icons/fa";

export default function AbstractSignModalContent({
  setBasicLoginOpened,
  isSignIn,
}: {
  setBasicLoginOpened: React.Dispatch<React.SetStateAction<boolean>>;
  isSignIn: boolean;
}) {
  return (
    <>
      <ModalHeader />
      <ModalCloseButton />
      <ModalBody pb={6}>
        <Text as="b" fontSize="1.8rem" display="flex" justifyContent="center" mb="16px">
          {isSignIn ? "Sign In" : "Sign Up"}
        </Text>

        <AuthRegistrySelectButton
          onClick={() => setBasicLoginOpened(true)}
          icon={<AiOutlineUser />}
          text={"Use email"}
        />

        <a href={config.backendPath + "/oauth2/authorization/google"}>
          <AuthRegistrySelectButton icon={<FcGoogle />} text={"Continue with Google"} />
        </a>

        <a href={config.backendPath + "/oauth2/authorization/github"}>
          <AuthRegistrySelectButton icon={<FaGithub />} text={"Continue with GitHub"} />
        </a>
      </ModalBody>
    </>
  );
}
