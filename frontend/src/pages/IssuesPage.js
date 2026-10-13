import { useEffect, useState } from "react";
import API from "../services/api";

export default function IssuesPage() {
  const [issues, setIssues] = useState([]);
  const [form, setForm] = useState({ title: "Broken streetlight", description: "Streetlight not working near bus terminal", category: "STREETLIGHT", location: "Block 14", priority: "HIGH" });

  const fetchData = async () => {
    const response = await API.get("/civic-issues");
    setIssues(response.data);
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    await API.post("/civic-issues", form);
    fetchData();
  };

  return (
    <div>
      <div className="panel-card mb-3">
        <h5>Report Civic Issue</h5>
        <form onSubmit={handleSubmit} className="row g-2">
          <div className="col-md-6"><input className="form-control" value={form.title} onChange={(e) => setForm({ ...form, title: e.target.value })} /></div>
          <div className="col-md-3"><select className="form-select" value={form.category} onChange={(e) => setForm({ ...form, category: e.target.value })}><option>POTHOLE</option><option>GARBAGE_COLLECTION</option><option>WATER_LEAKAGE</option><option>STREETLIGHT</option><option>DRAINAGE</option></select></div>
          <div className="col-md-3"><select className="form-select" value={form.priority} onChange={(e) => setForm({ ...form, priority: e.target.value })}><option>LOW</option><option>MEDIUM</option><option>HIGH</option><option>CRITICAL</option></select></div>
          <div className="col-md-6"><input className="form-control" value={form.location} onChange={(e) => setForm({ ...form, location: e.target.value })} /></div>
          <div className="col-md-6"><textarea className="form-control" value={form.description} onChange={(e) => setForm({ ...form, description: e.target.value })} /></div>
          <div className="col-12"><button className="btn btn-primary">Submit Issue</button></div>
        </form>
      </div>

      <div className="panel-card">
        <h5>Issue Board</h5>
        <table className="table table-striped">
          <thead><tr><th>Title</th><th>Category</th><th>Location</th><th>Priority</th><th>Status</th></tr></thead>
          <tbody>
            {issues.map((issue) => (
              <tr key={issue.id}>
                <td>{issue.title}</td>
                <td>{issue.category}</td>
                <td>{issue.location}</td>
                <td>{issue.priority}</td>
                <td><span className="badge bg-warning text-dark">{issue.status}</span></td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
