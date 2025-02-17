import React from "react";
import {Route, Routes} from "react-router-dom";
import {NotFound} from "./components/NotFound";
import Navbar from "./components/navbar/Navbar";
import Homepage from "./components/Homepage";
import EditPage from "./components/user/EditPage";
import AuthenticatedAccess from "./components/navbar/navbar-content/access-enpoints/AuthenticatedAccess";
import VerifiedAccess from "./components/navbar/navbar-content/access-enpoints/VerifiedAccess";
import AdminAccess from "./components/navbar/navbar-content/access-enpoints/AdminAccess";
import OAuth2RedirectHandler from "./components/auth/OAuth2RedirectHandler";
import {Box, Flex} from "@chakra-ui/react";
import EmailVerification from "./components/auth/EmaiVerification";


const App = () => {
  return (
    <div>
      <Navbar />
      <Flex justifyContent="center">
        <Box w="80%">
          <Routes>
            <Route path="/" element={<Homepage />} />
            <Route path="*" element={<NotFound />} />
            <Route path="/edit" element={<EditPage />} />
            <Route path="authenticated" element={<AuthenticatedAccess />} />
            <Route path="verified" element={<VerifiedAccess />} />
            <Route path="/admin" element={<AdminAccess />} />
            <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler />} />
            <Route path="/verification" element={<EmailVerification />} />
          </Routes>
        </Box>
      </Flex>
    </div>
  );
};

export { App };
