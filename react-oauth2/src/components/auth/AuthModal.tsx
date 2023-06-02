import React, {useRef, useState} from "react";
import {Modal, ModalContent, ModalOverlay} from "@chakra-ui/react";
import AbstractSignModalContent from "./auth-modal-content/AbstractSignModalContent";
import BasicSignInModalContent from "./auth-modal-content/basic-sign-modal-content/BasicSignInModalContent";
import BasicSignUpModalContent from "./auth-modal-content/basic-sign-modal-content/BasicSignUpModalContent";

export default function AuthModal({
  isOpen,
  onClose,
  isSignIn,
}: {
  isOpen: boolean;
  onClose: () => void;
  isSignIn: boolean;
}) {
  const initialRef = useRef(null);
  const finalRef = useRef(null);
  const [basicLoginOpened, setBasicLoginOpened] = useState(false);

  return (
    <div>
      <Modal initialFocusRef={initialRef} finalFocusRef={finalRef} size="md" isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          {basicLoginOpened ? (
            isSignIn ? (
              <BasicSignInModalContent setBasicLoginOpened={setBasicLoginOpened} closeModal={onClose} />
            ) : (
              <BasicSignUpModalContent setBasicLoginOpened={setBasicLoginOpened} closeModal={onClose} />
            )
          ) : (
            <AbstractSignModalContent setBasicLoginOpened={setBasicLoginOpened} isSignIn={isSignIn} />
          )}
        </ModalContent>
      </Modal>
    </div>
  );
}
