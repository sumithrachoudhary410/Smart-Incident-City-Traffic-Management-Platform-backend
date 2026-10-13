import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { AuthProvider, useAuth } from "./context/AuthContext";
import LoginPage from "./pages/LoginPage";
import DashboardPage from "./pages/DashboardPage";
import TrafficPage from "./pages/TrafficPage";
import IncidentsPage from "./pages/IncidentsPage";
import ResourcesPage from "./pages/ResourcesPage";
import IssuesPage from "./pages/IssuesPage";
import NotificationsPage from "./pages/NotificationsPage";
import Layout from "./components/Layout";

function ProtectedRoute({ children }) {
  const { token } = useAuth();
  return token ? children : <Navigate to="/login" replace />;
}

function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/" element={<ProtectedRoute><Layout /></ProtectedRoute>}>
          <Route index element={<DashboardPage />} />
          <Route path="traffic" element={<TrafficPage />} />
          <Route path="incidents" element={<IncidentsPage />} />
          <Route path="resources" element={<ResourcesPage />} />
          <Route path="issues" element={<IssuesPage />} />
          <Route path="notifications" element={<NotificationsPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <AppRoutes />
    </AuthProvider>
  );
}
