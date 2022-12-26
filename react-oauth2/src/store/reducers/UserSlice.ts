import {User} from "../../model/User";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";

interface UserState {
  principal: User | null;
}

const initialState: UserState = {
  principal: null,
};

export const signUpSlice = createSlice({
  name: "signIn",
  initialState,
  reducers: {
    saveUser(state, action: PayloadAction<User | null>) {
      state.principal = action.payload;
    },
  },
});

export const { saveUser } = signUpSlice.actions;
export default signUpSlice.reducer;
