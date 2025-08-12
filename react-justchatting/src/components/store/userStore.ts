import {create} from "zustand/react";

export type User = {
  id: number;
  avatar: string | undefined;
  username: string;
  email: string;
}

export interface UserStore {
  user: User | null | undefined;
  setUser: (user: User | null) => void;
}

export const useUserStore = create<UserStore>((set) => ({
  user: undefined,
  setUser: (user) => set({ user: user }),
}));
