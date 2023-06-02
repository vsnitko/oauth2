import {Box, Button, Flex, Stack, useColorMode, useColorModeValue, useDisclosure} from "@chakra-ui/react";
import {MoonIcon, SunIcon} from "@chakra-ui/icons";
import {useAppDispatch, useAppSelector} from "../../hooks/redux";
import AuthModal from "../auth/AuthModal";
import {useEffect, useState} from "react";
import {saveUser} from "../../store/reducers/UserSlice";
import {UserNavbarMenu} from "./navbar-content/UserNavbarMenu";
import {AuthButtons} from "./navbar-content/AuthButtons";
import {Link} from "react-router-dom";

export default function Navbar() {
  const { colorMode, toggleColorMode } = useColorMode();
  const signInModal = useDisclosure();
  const [isSignIn, setIsSignIn] = useState(true);

  const { principal } = useAppSelector((state) => state.userReducer);
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (principal) {
      return;
    }
    let principalString: string | null = localStorage.getItem("principal");
    if (principalString) {
      dispatch(saveUser(JSON.parse(principalString)));
    }
  });

  const openModal = (isSignIn: boolean) => {
    setIsSignIn(isSignIn);
    signInModal.onOpen();
  };

  return (
    <>
      <AuthModal isOpen={signInModal.isOpen} onClose={signInModal.onClose} isSignIn={isSignIn} />

      <Box bg={useColorModeValue("gray.100", "gray.900")} px={4} mb={4}>
        <Flex h={16} alignItems={"center"} justifyContent={"space-between"}>
          <Box>
            <Link to="/" style={{ marginRight: 48 }}>
              Home
            </Link>
            <Link to="authenticated" style={{ marginRight: 36 }}>
              Authenticated
            </Link>
            <Link to="/verified" style={{ marginRight: 36 }}>
              Verified
            </Link>
            <Link to="/admin" style={{ marginRight: 36 }}>
              Admin
            </Link>
          </Box>

          <Flex alignItems={"center"}>
            <Stack direction={"row"} spacing={7}>
              <Button onClick={toggleColorMode}>{colorMode === "light" ? <MoonIcon /> : <SunIcon />}</Button>

              {principal ? <UserNavbarMenu principal={principal} /> : <AuthButtons openModal={openModal} />}
            </Stack>
          </Flex>
        </Flex>
      </Box>
    </>
  );
}
