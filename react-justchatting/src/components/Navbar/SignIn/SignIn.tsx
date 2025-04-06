import React, {useState, useTransition} from 'react';
import "./SignIn.scss"
import Button from "../../global/Button/Button.tsx";
import Modal from "../../global/Modal/Modal.tsx";
import Person from "../../global/icons/Person.tsx";
import api from "../../../axios-spring.ts";
import {User, useUserStore} from "../../store/userStore.ts";
import {useTransitionRender} from "../../global/hook/useTransitionRender.ts";

type AuthResponse = {
  accessToken: string;
  user: User;
}

const SignIn = () => {

  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [emailExists, setEmailExists] = useState(false);
  const [isPending, startTransition] = useTransition();
  const [page, setPage] = useState(0);
  const {setUser} = useUserStore();
  const transitionRender = useTransitionRender();

  const checkEmail = () => {
    startTransition(() => {
      api
        .get<boolean>(`/user/exists?email=${email}`)
        .then((res) => {
          setEmailExists(res.data);
          setPage(prev => prev + 1);
        })
        .catch((err) => {
          setPage(prev => prev + 1);
        });
    });
  }

  const signIn = () => {
    api
      .post<AuthResponse>(
        "auth/sign-in",
        {
          email: email,
          password: password,
        }
      )
      .then((res) => {
        localStorage.setItem("access-token", res.data.accessToken);
        setUser(res.data.user);
        transitionRender.setOpened(false);
      });
  }

  const signUp = () => {
    api
      .post<AuthResponse>(
        "auth/sign-up",
        {
          username: username,
          email: email,
          password: password,
        }
      )
      .then((res) => {
        localStorage.setItem("access-token", res.data.accessToken);
        setUser(res.data.user);
        transitionRender.setOpened(false);
      });
  }


  return (
    <>
      <Button
        onClick={() => {
          transitionRender.setOpened(true)
        }}
      >
        Sign In
      </Button>
      <Modal
        page={page}
        setPage={setPage}
        transitionRender={transitionRender}
      >
        <>
          <a href={import.meta.env.VITE_API_URL + "/oauth2/authorization/google"}>
            <div className="sign-in-option">
              <div className="logo-box">
                <div className="sign-in-logo google" />
              </div>
              <span>Continue with Google</span>
            </div>
          </a>
          <a href={import.meta.env.VITE_API_URL + "/oauth2/authorization/github"}>
            <div className="sign-in-option">
              <div className="logo-box">
                <div className="sign-in-logo github" />
              </div>
              <span>Continue with GitHub</span>
            </div>
          </a>
          <div
            onClick={() => setPage(prev => prev + 1)}
            className="sign-in-option"
          >
            <div className="logo-box">
              <Person className="sign-in-logo email" />
            </div>
            <span>Use Email</span>
          </div>
        </>
        <>
          <label
            className="sign-in-label"
            htmlFor="email"
          >Email to Sing In or Register</label>
          <div className="enter-email-flex">
            <div className="input-box modal-input-box">
              <input
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                type="text"
                id="email"
                name="email"
                placeholder="Email"
              />
            </div>
            <Button
              onClick={checkEmail}
              className="next-button"
            >
              {isPending ? "Loading..." : "Next"}
            </Button>
          </div>
        </>
        <>
          {emailExists
            ? <>
              <label
                className="sign-in-label"
                htmlFor="password"
              >User found. Enter password.</label>
              <div className="input-box modal-input-box">
                <input
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  type="text"
                  id="password"
                  name="password"
                  placeholder="Password"
                />
              </div>
              <Button
                onClick={signIn}
                className="modal-sign-in-button"
              >
                Sign In
              </Button>
            </>
            : <>
              <label
                className="sign-in-label"
                htmlFor="password"
              >Create name for the new user.</label>
              <div className="input-box modal-input-box">
                <input
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  type="text"
                  id="username"
                  name="username"
                  placeholder="Username"
                />
              </div>
              <label
                className="sign-in-label"
                htmlFor="password"
              >Create password for a new user.</label>
              <div className="input-box modal-input-box">
                <input
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  type="text"
                  id="password"
                  name="password"
                  placeholder="Password"
                />
              </div>
              <Button
                onClick={signUp}
                className="modal-sign-in-button"
              >
                Register
              </Button>
            </>
          }
        </>
      </Modal>
    </>
  );
};

export default SignIn;

