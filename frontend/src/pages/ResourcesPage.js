import { useEffect, useState } from "react";
import API from "../services/api";

export default function ResourcesPage() {
  const [resources, setResources] = useState([]);
  const [form, setForm] = useState({ type: "AMBULANCE", name: "Rapid Response 12", currentLocation: "Central station" });

  const fetchData = async () => {
    const response = await API.get("/resources");
    setResources(response.data);
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    await API.post("/resources", form);
    fetchData();
  };

  return (
    <div>
      <div className="panel-card mb-3">
        <h5>Add Emergency Resource</h5>
        <form onSubmit={handleSubmit} className="row g-2">
          <div className="col-md-3"><select className="form-select" value={form.type} onChange={(e) => setForm({ ...form, type: e.target.value })}><option>AMBULANCE</option><option>POLICE_UNIT</option><option>FIRE_ENGINE</option><option>RESPONSE_TEAM</option></select></div>
          <div className="col-md-3"><input className="form-control" placeholder="Name" value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} /></div>
          <div className="col-md-3"><input className="form-control" placeholder="Current location" value={form.currentLocation} onChange={(e) => setForm({ ...form, currentLocation: e.target.value })} /></div>
          <div className="col-md-3"><button className="btn btn-primary w-100">Create</button></div>
        </form>
      </div>

      <div className="panel-card">
        <h5>Emergency Fleet</h5>
        <table className="table table-striped">
          <thead><tr><th>Name</th><th>Type</th><th>Location</th><th>Status</th></tr></thead>
          <tbody>
            {resources.map((resource) => (
              <tr key={resource.id}>
                <td>{resource.name}</td>
                <td>{resource.type}</td>
                <td>{resource.currentLocation || "N/A"}</td>
                <td><span className="badge bg-success">{resource.status || "AVAILABLE"}</span></td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
