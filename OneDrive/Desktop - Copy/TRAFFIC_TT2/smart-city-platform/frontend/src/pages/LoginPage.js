import { useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../services/api";
import { useAuth } from "../context/AuthContext";

export default function LoginPage() {
  const [isRegister, setIsRegister] = useState(false);
  const [form, setForm] = useState({
    username: "admin",
    password: "admin123",
    email: "",
    firstName: "",
    lastName: ""
  });
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    setSuccess("");

    if (isRegister) {
      if (!form.username || !form.password || !form.email) {
        setError("Username, email, and password are required.");
        setLoading(false);
        return;
      }
      try {
        await API.post("/auth/register", {
          username: form.username,
          email: form.email,
          password: form.password,
          firstName: form.firstName || undefined,
          lastName: form.lastName || undefined,
          roles: ["CITIZEN"]
        });
        setSuccess("Registration successful! You can now log in.");
        setIsRegister(false);
        setForm((prev) => ({ ...prev, password: "" }));
      } catch (err) {
        setError(err.response?.data?.message || err.response?.data || "Registration failed. Please try again.");
      } finally {
        setLoading(false);
      }
    } else {
      try {
        const response = await API.post("/auth/login", {
          username: form.username,
          password: form.password
        });
        login(response.data.accessToken, response.data.username, response.data.role);
        navigate("/");
      } catch (err) {
        setError(err.response?.data?.message || "Login failed. Please check your username and password.");
      } finally {
        setLoading(false);
      }
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h2>Smart City Platform</h2>
        <p>{isRegister ? "Create a citizen account" : "Sign in to the operational command center"}</p>

        <div className="d-flex mb-3 border-bottom pb-2 gap-2">
          <button
            type="button"
            className={`btn btn-sm ${!isRegister ? "btn-primary" : "btn-outline-secondary"}`}
            onClick={() => { setIsRegister(false); setError(""); setSuccess(""); }}
          >
            Sign In
          </button>
          <button
            type="button"
            className={`btn btn-sm ${isRegister ? "btn-primary" : "btn-outline-secondary"}`}
            onClick={() => { setIsRegister(true); setError(""); setSuccess(""); }}
          >
            Sign Up
          </button>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Username</label>
            <input
              className="form-control"
              value={form.username}
              required
              onChange={(e) => setForm({ ...form, username: e.target.value })}
            />
          </div>

          {isRegister && (
            <>
              <div className="mb-3">
                <label className="form-label">Email</label>
                <input
                  type="email"
                  className="form-control"
                  value={form.email}
                  required
                  placeholder="name@example.com"
                  onChange={(e) => setForm({ ...form, email: e.target.value })}
                />
              </div>
              <div className="row mb-3">
                <div className="col">
                  <label className="form-label">First Name</label>
                  <input
                    className="form-control"
                    value={form.firstName}
                    onChange={(e) => setForm({ ...form, firstName: e.target.value })}
                  />
                </div>
                <div className="col">
                  <label className="form-label">Last Name</label>
                  <input
                    className="form-control"
                    value={form.lastName}
                    onChange={(e) => setForm({ ...form, lastName: e.target.value })}
                  />
                </div>
              </div>
            </>
          )}

          <div className="mb-3">
            <label className="form-label">Password</label>
            <input
              type="password"
              className="form-control"
              value={form.password}
              required
              onChange={(e) => setForm({ ...form, password: e.target.value })}
            />
          </div>

          {error && <div className="alert alert-danger">{error}</div>}
          {success && <div className="alert alert-success">{success}</div>}

          <button type="submit" className="btn btn-primary w-100" disabled={loading}>
            {loading ? (isRegister ? "Creating account..." : "Signing in...") : (isRegister ? "Register" : "Login")}
          </button>
        </form>

        {!isRegister && (
          <small className="demo-note d-block mt-3 text-muted">
            Demo accounts: admin / admin123, operator / operator123, citizen / citizen123
          </small>
        )}
      </div>
    </div>
  );
}
