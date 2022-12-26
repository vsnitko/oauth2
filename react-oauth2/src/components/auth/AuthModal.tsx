import React, {useRef, useState} from "react";
import {Modal, ModalContent, ModalOverlay} from "@chakra-ui/react";
import SelectRegistryModalContent from "./SelectRegistryModalContent";
import BasicSignInModalContent from "./BasicSignInModalContent";
import BasicSignUpModalContent from "./BasicSignUpModalContent";

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
            <SelectRegistryModalContent setBasicLoginOpened={setBasicLoginOpened} isSignIn={isSignIn} />
          )}
        </ModalContent>
      </Modal>
    </div>
  );
}
