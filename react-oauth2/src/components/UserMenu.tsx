import {User} from "../model/User";
import {Avatar, Button, Center, Menu, MenuButton, MenuDivider, MenuItem, MenuList} from "@chakra-ui/react";
import {useCallback} from "react";
import {useNavigate} from "react-router-dom";
import {logout} from "../utils/AuthUtils";
import {useAppDispatch} from "../hooks/redux";

export function UserMenu({ principal }: { principal: User }) {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const linkTrigger = useCallback(() => navigate("/edit", { replace: true }), [navigate]);

  return (
    <Menu>
      <MenuButton as={Button} rounded={"full"} variant={"link"} cursor={"pointer"} minW={0}>
        <Avatar size={"sm"} src={principal.avatar || "https://avatars.dicebear.com/api/male/username.svg"} referrerPolicy="no-referrer"/>
      </MenuButton>
      <MenuList alignItems={"center"}>
        <br />
        <Center>
          <Avatar size={"2xl"} src={principal.avatar || "https://avatars.dicebear.com/api/male/username.svg"} referrerPolicy="no-referrer"/>
        </Center>
        <br />
        <Center mb={2}>
          {principal.name}
        </Center>
        <Center>
          {principal.email}
        </Center>
        <br />
        <MenuDivider />
        <MenuItem onClick={linkTrigger}>Account Settings</MenuItem>
        <MenuItem onClick={() => logout(dispatch, navigate)}>Logout</MenuItem>
      </MenuList>
    </Menu>
  );
}
