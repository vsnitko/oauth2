import {Button} from "@chakra-ui/react";

export function AuthButtons({ openModal }: { openModal: (isSignIn: boolean) => void }) {
  return (
    <>
      <Button fontSize={"sm"} fontWeight={600} variant="outline" onClick={() => openModal(true)}>
        Sign In
      </Button>
      <Button
        onClick={() => openModal(false)}
        fontSize={"sm"}
        fontWeight={600}
        color={"white"}
        bg={"pink.400"}
        _hover={{
          bg: "pink.300",
        }}
      >
        Sign Up
      </Button>
    </>
  );
}
