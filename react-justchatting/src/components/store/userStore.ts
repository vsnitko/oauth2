import {create} from "zustand/react";

export type User = {
  id: number;
  avatar: string | undefined;
  username: string;
  email: string;
}

export interface UserStore {
  user: User | null;
  setUser: (user: User | null) => void;
}

export const useUserStore = create<UserStore>((set) => ({
  user: null,
  setUser: (user) => set({ user: user }),
}));
