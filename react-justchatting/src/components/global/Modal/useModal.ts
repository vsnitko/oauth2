import { useState } from "react";

export const useModal = () => {
  const [active, setActive] = useState(false);

  const open = () => {
    setActive(true);
  };
  const close = () => {
    setActive(false);
  };

  return { active, open, close };
};
