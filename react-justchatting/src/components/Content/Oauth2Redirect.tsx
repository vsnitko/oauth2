import {useEffect} from 'react';
import {useLocation} from "wouter";
import {useUserStore} from "../store/userStore.ts";

const Oauth2Redirect = () => {

  const [location, setLocation] = useLocation();
  const {setUser} = useUserStore();

  useEffect(() => {
    setLocation('/');
  }, []);

  return null;
}

export default Oauth2Redirect;
