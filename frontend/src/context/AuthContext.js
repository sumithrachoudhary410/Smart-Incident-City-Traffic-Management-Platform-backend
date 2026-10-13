import { createContext, useContext, useMemo, useState } from "react";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [auth, setAuth] = useState(() => ({
    token: sessionStorage.getItem("smartCityToken") || null,
    username: sessionStorage.getItem("smartCityUsername") || null,
    role: sessionStorage.getItem("smartCityRole") || null
  }));

  const login = (token, username, role) => {
    sessionStorage.setItem("smartCityToken", token);
    sessionStorage.setItem("smartCityUsername", username);
    sessionStorage.setItem("smartCityRole", role);
    setAuth({ token, username, role });
  };

  const logout = () => {
    sessionStorage.removeItem("smartCityToken");
    sessionStorage.removeItem("smartCityUsername");
    sessionStorage.removeItem("smartCityRole");
    setAuth({ token: null, username: null, role: null });
  };

  const value = useMemo(() => ({ ...auth, login, logout }), [auth]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  return useContext(AuthContext);
}
