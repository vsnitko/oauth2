import React, {useRef} from 'react';
import "./Modal.scss"
import Back from "../icons/Back.tsx";
import Close from "../icons/Close.tsx";
import {OpenableComponentType} from "../DefaultComponentType.ts";
import clsx from "clsx";
import {TransitionRender} from "../hook/useTransitionRender.ts";

const Modal: React.FC<OpenableComponentType & {
  page?: number;
  setPage?: React.Dispatch<React.SetStateAction<number>>,
  transitionRender: TransitionRender
}> = (
  {
    children,
    page = 0,
    setPage,
    transitionRender
  }
) => {
  const childrenArray = Array.isArray(children) ? children : [children];
  const refs = useRef<(HTMLDivElement | null)[]>([]);

  const closeModal = (e: React.MouseEvent) => {
    if (e.target !== e.currentTarget) {
      return;
    }
    transitionRender.setOpened(false);
    setTimeout(() => {
      if (setPage) {
        setPage(0);
      }
    }, 300);
  }

  return (
    <>
      {transitionRender.elementCreated && <div
          ref={transitionRender.refElementToRender}
          onClick={closeModal}
          className={clsx("modal-overlay", transitionRender.elementVisible && "opened")}
      >
          <div className="modal">
              <div className="modal-inner">
                  <div className={"modal-header" + (page !== 0 ? " not-first-page" : "")}>
                      <Back
                          onClick={() => setPage!(page => page - 1)}
                          className={"modal-button back" + (page !== 0 ? " opened" : "")}
                      />
                      <Close
                          onClick={closeModal}
                          className="modal-button"
                      />
                  </div>
                  <div
                      className="modal-content"
                      style={{
                        transform: `translateX(${page * -100 / childrenArray.length}%)`,
                        height: refs.current[page]?.getBoundingClientRect().height + "px",
                        width: `${100 * childrenArray.length}%`
                      }}
                  >
                    {childrenArray.map((child, index) => (
                      <div
                        className={"modal-page" + ((page !== index) ? " hidden" : "")}
                        key={index}
                        ref={(el) => {
                          refs.current[index] = el
                        }}
                        style={{width: `${100 / childrenArray.length}%`}}
                      >
                        {child}
                      </div>
                    ))}
                  </div>
              </div>
          </div>
      </div>}
    </>
  );
};

export default Modal;
