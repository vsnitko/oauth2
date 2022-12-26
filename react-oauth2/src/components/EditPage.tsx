import React, {ChangeEvent, useEffect, useState} from "react";
import {Box, Button, Flex, FormControl, FormLabel, Input, useToast} from "@chakra-ui/react";
import axios from "axios";
import {User} from "../model/User";
import {useAppDispatch, useAppSelector} from "../hooks/redux";
import {saveUser} from "../store/reducers/UserSlice";

export default function EditPage() {
  const { principal } = useAppSelector((state) => state.userReducer);
  const [name, setName] = useState(principal && principal.name || "");
  const onNameChanged = (e: ChangeEvent<HTMLInputElement>) => setName(e.target.value);

  const dispatch = useAppDispatch();
  const toast = useToast();

  useEffect(() => {
    axios
      .get("/user")
      .then(response => {
        setName(response.data.name);
      });
  }, []);

  const edit = () => {
    axios
      .post<User>(
        "/user/edit",
        {
          name: name,
        }
      )
      .then((response) => {
        dispatch(saveUser(response.data));
        localStorage.setItem("principal", JSON.stringify(response.data))
        toast({
          title: "User was updated",
          status: "success",
          duration: 5000,
          position: "bottom-right",
          isClosable: true,
        });
      })
      .catch((err) => {
        toast({
          title: err.message,
          status: "error",
          duration: 5000,
          position: "bottom-right",
          isClosable: true,
        });
      });
  };

  return (
    <div>
      <Flex justifyContent="center">
        <Box w="40%">
          <FormControl mt={4}>
            <FormLabel>Username</FormLabel>
            <Input value={name} onChange={onNameChanged} />
          </FormControl>
          <Button
            onClick={edit}
            mt={4}
            fontSize={"sm"}
            fontWeight={600}
            color={"white"}
            bg={"pink.400"}
            _hover={{
              bg: "pink.300",
            }}
          >
            Save changes
          </Button>
        </Box>
      </Flex>
    </div>
  );
}
