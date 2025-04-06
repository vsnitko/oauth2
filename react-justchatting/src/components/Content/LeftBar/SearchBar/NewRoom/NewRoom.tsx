import React, {ChangeEvent, useRef, useState} from 'react';
import "./NewRoom.scss"
import Button from "../../../../global/Button/Button.tsx";
import Modal from "../../../../global/Modal/Modal.tsx";
import {useTransitionRender} from "../../../../global/hook/useTransitionRender.ts";
import api from "../../../../../axios-spring.ts";
import Upload from "../../../../global/icons/Upload.tsx";
import {ChatElement, useChatStore} from "../../../../store/chatStore.ts";

const NewRoom = () => {

  const fileInputRef = useRef<HTMLInputElement>(null);
  const transitionRender = useTransitionRender();
  const [roomName, setRoomName] = useState<string>("");
  const [avatar, setAvatar] = useState<File | null>(null);
  const {chatList, setChatList} = useChatStore();

  const createRoom = () => {
    const formData = new FormData();
    formData.append('roomName', roomName);
    if (avatar) {
      formData.append('avatar', avatar);
    }
    api
      .post<ChatElement>(
        "chat/create",
        formData,
        {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        }
      )
      .then((res) => {
        setChatList([...chatList, res.data]);
        transitionRender.setOpened(false);
        setRoomName("")
        setAvatar(null)
      });
  }

  const shortenFileName = (name: string): string => {
    if (name.length > 40) {
      return name.substring(0, 16) + "..." + name.substring(name.length - 24, name.length);
    }
    return name;
  }

  const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      setAvatar(e.target.files[0]);
    }
  };

  return (
    <>
      <Button
        className="new-room-button"
        onClick={() => {
          transitionRender.setOpened(true)
        }}
      >
        <img
          className="new-room-img"
          src="/new-room.svg"
          alt="logo"
        />
        New Room
      </Button>
      <Modal transitionRender={transitionRender}>
        <>
          <label
            className="room-name-label"
            htmlFor="room-name"
          >Create name for room</label>
          <div className="input-box modal-input-box">
            <input
              value={roomName}
              onChange={(e) => setRoomName(e.target.value)}
              type="text"
              id="room-name"
              name="room-name"
              placeholder="Room Name"
            />
          </div>
          <label
            className="room-avatar-label"
            htmlFor="room-avatar"
          >Choose room avatar</label>
          <input
            onChange={handleFileChange}
            ref={fileInputRef}
            type="file"
            accept="image/*"
            id="room-avatar"
            name="room-avatar"
            placeholder="Room Avatar"
          />
          <Button
            className="secondary-button choose-chat-avatar-file"
            onClick={() => {
              fileInputRef.current?.click()
            }}
          >
            <Upload />
            {avatar ? shortenFileName(avatar.name) : "Choose file"}
          </Button>
          <Button
            onClick={createRoom}
            className="create-chat-button"
          >
            Create
          </Button>

        </>
      </Modal>
    </>
  );
}

export default NewRoom;
