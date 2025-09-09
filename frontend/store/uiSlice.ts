import { createSlice } from '@reduxjs/toolkit';

interface UiState {
  initialized: boolean;
}

const initialState: UiState = {
  initialized: true,
};

const uiSlice = createSlice({
  name: 'ui',
  initialState,
  reducers: {},
});

export default uiSlice.reducer;

