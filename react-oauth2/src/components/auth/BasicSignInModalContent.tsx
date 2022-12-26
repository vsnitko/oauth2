import React, {ChangeEvent, useRef, useState} from "react";
import {
    Button,
    FormControl,
    FormLabel,
    IconButton,
    Input,
    InputGroup,
    InputRightElement,
    ModalBody,
    ModalCloseButton,
    ModalFooter,
    ModalHeader,
    Text,
    useToast
} from "@chakra-ui/react";
import axios from "axios";
import {useAppDispatch} from "../../hooks/redux";
import {FaChevronLeft} from "react-icons/fa";
import {SignInResponse} from "../../model/SignInResponse";
import {signIn} from "../../utils/AuthUtils";

export default function BasicSignInModalContent({
  setBasicLoginOpened,
  closeModal,
}: {
  setBasicLoginOpened: React.Dispatch<React.SetStateAction<boolean>>;
  closeModal: () => void;
}) {
  const initialRef = useRef(null);
  const toast = useToast();
  const dispatch = useAppDispatch();

  const [email, setEmail] = useState("");
  const onEmailChanged = (e: ChangeEvent<HTMLInputElement>) => setEmail(e.target.value);
  const [password, setPassword] = useState("");
  const onPasswordChanged = (e: ChangeEvent<HTMLInputElement>) => setPassword(e.target.value);
  const [showPassword, setShowPassword] = useState(false);
  const onShowPasswordChanged = () => setShowPassword(!showPassword);

  const handleSignIn = () => {
    axios
      .post<SignInResponse>(
        "auth/sign-in",
        {
          email: email,
          password: password,
        },
        { withCredentials: true }
      )
      .then((response) => {
        signIn(response.data.token, dispatch);
        closeModal();
        setBasicLoginOpened(false);
      })
      .catch((err) => {
        toast({
          title: typeof err.response.data === "string" ? err.response.data : "Invalid input",
          status: "error",
          duration: 5000,
          position: "bottom-right",
          isClosable: true,
        });
      });
  };

  return (
    <>
      <IconButton
        onClick={() => setBasicLoginOpened(false)}
        variant="outline"
        border="none"
        colorScheme="gray"
        aria-label="Back"
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          position: "absolute",
          top: "var(--chakra-space-2)",
          left: "var(--chakra-space-3)",
          width: "var(--chakra-sizes-8)",
          height: "var(--chakra-sizes-8)",
          minWidth: "var(--chakra-sizes-8)",
        }}
        icon={<FaChevronLeft />}
      />
      <ModalHeader />
      <ModalCloseButton />
      <ModalBody pb={6}>
        <Text as="b" fontSize="1.8rem" display="flex" justifyContent="center">
          Sign In
        </Text>
        <FormControl>
          <FormLabel>Email</FormLabel>
          <Input ref={initialRef} value={email} onChange={onEmailChanged} placeholder="Email" />
        </FormControl>
        <FormControl mt={4}>
          <FormLabel>Password</FormLabel>
          <InputGroup size="md">
            <Input
              type={showPassword ? "text" : "password"}
              value={password}
              onChange={onPasswordChanged}
              placeholder="Password"
            />
            <InputRightElement width="4.5rem">
              <Button h="1.75rem" size="sm" onClick={onShowPasswordChanged}>
                {showPassword ? "Hide" : "Show"}
              </Button>
            </InputRightElement>
          </InputGroup>
        </FormControl>
      </ModalBody>
      <ModalFooter>
        <Button onClick={handleSignIn} bg={"pink.400"} mr={3}>
          Sign In
        </Button>
        <Button onClick={() => setBasicLoginOpened(false)}>Cancel</Button>
      </ModalFooter>
    </>
  );
}
