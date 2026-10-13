import axios from "axios";

const API_BASE = process.env.REACT_APP_API_URL || "https://smart-incident-city-traffic-management-platform-production.up.railway.app";

const API = axios.create({
  baseURL: API_BASE.endsWith("/api") ? API_BASE : `${API_BASE}/api`,
  headers: {
    "Content-Type": "application/json"
  }
});

API.interceptors.request.use((config) => {
  const token = sessionStorage.getItem("smartCityToken");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default API;
