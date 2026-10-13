import { NavLink, Outlet } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const navItems = [
  { to: "/", label: "Dashboard", icon: "fa-chart-line" },
  { to: "/traffic", label: "Traffic", icon: "fa-traffic-light" },
  { to: "/incidents", label: "Incidents", icon: "fa-triangle-exclamation" },
  { to: "/resources", label: "Resources", icon: "fa-ambulance" },
  { to: "/issues", label: "Civic Issues", icon: "fa-city" },
  { to: "/notifications", label: "Notifications", icon: "fa-bell" }
];

export default function Layout() {
  const { username, role, logout } = useAuth();

  return (
    <div className="app-shell">
      <aside className="sidebar">
        <div className="brand-box">
          <div className="brand">SC</div>
          <div>
            <strong>Smart City</strong>
            <small>Control Center</small>
          </div>
        </div>
        <nav className="nav-menu">
          {navItems.map((item) => (
            <NavLink key={item.to} to={item.to} className={({ isActive }) => `nav-link ${isActive ? "active" : ""}`}>
              <i className={`fa-solid ${item.icon}`} />
              <span>{item.label}</span>
            </NavLink>
          ))}
        </nav>
      </aside>

      <main className="main-panel">
        <header className="topbar">
          <div>
            <h4>Operations Dashboard</h4>
          </div>
          <div className="user-menu">
            <span>{username}</span>
            <span className="badge bg-primary">{role}</span>
            <button className="btn btn-outline-secondary btn-sm" onClick={logout}>Logout</button>
          </div>
        </header>

        <div className="page-content">
          <Outlet />
        </div>
      </main>
    </div>
  );
}
