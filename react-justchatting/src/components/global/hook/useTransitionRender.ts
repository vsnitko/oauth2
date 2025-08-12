import {RefObject, useEffect, useRef, useState} from "react";

export type TransitionRender = {
  elementCreated: boolean,
  setOpened: (opened: boolean) => void,
  elementVisible: boolean,
  refElementToRender: RefObject<any>
};

export const useTransitionRender = () => {
  const [elementCreated, setElementCreated] = useState(false);
  const [elementVisible, setElementVisible] = useState(false);
  const refElementToRender = useRef<HTMLUListElement>(null);

  const setOpened = (opened: boolean) => {
    if (opened) {
      setElementCreated(true);
    } else {
      setElementVisible(false);
    }
  }

  useEffect(() => {
    if (elementCreated) {
      setElementVisible(true);
    }
  }, [elementCreated]);

  useEffect(() => {
    const popupElement = refElementToRender.current;

    const handleTransitionEnd = () => {
      if (!elementVisible) {
        setElementCreated(false);
      }
    };

    popupElement?.addEventListener('transitionend', handleTransitionEnd);

    return () => {
      popupElement?.removeEventListener('transitionend', handleTransitionEnd);
    };
  }, [elementVisible]);

  return {elementCreated, setOpened, elementVisible, refElementToRender};
}
