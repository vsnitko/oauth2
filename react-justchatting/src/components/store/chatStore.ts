import {create} from "zustand/react";

export type ChatElement = {
  id: number;
  avatar: string | undefined;
  name: string;
  lastMessage: string;
}

interface ChatStore {
  chatList: ChatElement[];
  selectedChat: ChatElement | null;
  setChatList: (chats: ChatElement[]) => void;
  setSelectedChat: (chat: ChatElement) => void;
}

export const useChatStore = create<ChatStore>((set) => ({
  chatList: [],
  selectedChat: null,
  setChatList: (chatList) => set({ chatList: chatList }),
  setSelectedChat: (chat) => set({ selectedChat: chat }),
}));
