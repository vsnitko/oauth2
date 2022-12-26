import React from "react";
import {Route, Routes} from "react-router-dom";
import {NotFound} from "./components/NotFound";
import Navbar from "./components/Navbar";
import Homepage from "./components/Homepage";
import EditPage from "./components/EditPage";
import AuthenticatedAccess from "./components/access/AuthenticatedAccess";
import VerifiedAccess from "./components/access/VerifiedAccess";
import AdminAccess from "./components/access/AdminAccess";
import OAuth2RedirectHandler from "./components/auth/OAuth2RedirectHandler";
import {Box, Flex} from "@chakra-ui/react";

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
          </Routes>
        </Box>
      </Flex>
    </div>
  );
};

export { App };
