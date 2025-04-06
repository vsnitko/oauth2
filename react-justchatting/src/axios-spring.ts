import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  withCredentials: true,
});

api.interceptors.request.use((config) => {
  config.headers.Authorization = localStorage.getItem("access-accessToken") || "";
  return config;
});

export default api;
