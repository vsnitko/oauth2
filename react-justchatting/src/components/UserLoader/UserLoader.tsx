import {useEffect} from 'react';
import {User, useUserStore} from "../store/userStore.ts";
import api from "../../axios-spring.ts";

const UserLoader = () => {

  const {setUser} = useUserStore();

  useEffect(() => {
    api
      .get<User>("/user")
      .then((res) => {
        setUser(res.data);
      });
  }, []);

  return null;
}

export default UserLoader;
