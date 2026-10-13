import { useEffect, useState } from "react";
import API from "../services/api";

export default function IncidentsPage() {
  const [incidents, setIncidents] = useState([]);
  const [resources, setResources] = useState([]);
  const [form, setForm] = useState({ type: "ACCIDENT", description: "Vehicle collision on main road", severity: "HIGH", latitude: 12.9716, longitude: 77.5946 });
  const [loading, setLoading] = useState(true);

  const fetchData = async () => {
    setLoading(true);
    const [incidentRes, resourceRes] = await Promise.all([
      API.get("/incidents"),
      API.get("/resources")
    ]);
    setIncidents(incidentRes.data);
    setResources(resourceRes.data);
    setLoading(false);
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleCreate = async (e) => {
    e.preventDefault();
    await API.post("/incidents", form);
    fetchData();
  };

  const handleAssign = async (incidentId, resourceId) => {
    await API.put(`/incidents/${incidentId}/assign`, null, { params: { resourceId } });
    fetchData();
  };

  if (loading) return <div className="loading-box">Loading incident data...</div>;

  return (
    <div>
      <div className="panel-card mb-3">
        <h5>Report Incident</h5>
        <form onSubmit={handleCreate}>
          <div className="row g-2">
            <div className="col-md-3"><select className="form-select" value={form.type} onChange={(e) => setForm({ ...form, type: e.target.value })}><option>ACCIDENT</option><option>FIRE</option><option>MEDICAL</option><option>ROAD_BLOCKAGE</option><option>OTHER</option></select></div>
            <div className="col-md-3"><select className="form-select" value={form.severity} onChange={(e) => setForm({ ...form, severity: e.target.value })}><option>LOW</option><option>MEDIUM</option><option>HIGH</option><option>CRITICAL</option></select></div>
            <div className="col-md-3"><input className="form-control" placeholder="Latitude" value={form.latitude} onChange={(e) => setForm({ ...form, latitude: Number(e.target.value) })} /></div>
            <div className="col-md-3"><input className="form-control" placeholder="Longitude" value={form.longitude} onChange={(e) => setForm({ ...form, longitude: Number(e.target.value) })} /></div>
          </div>
          <div className="mt-2"><textarea className="form-control" placeholder="Description" value={form.description} onChange={(e) => setForm({ ...form, description: e.target.value })} /></div>
          <button className="btn btn-primary mt-3">Submit Incident</button>
        </form>
      </div>

      <div className="panel-card">
        <h5>Incident Feed</h5>
        <table className="table table-striped">
          <thead><tr><th>Type</th><th>Severity</th><th>Status</th><th>Location</th><th>Action</th></tr></thead>
          <tbody>
            {incidents.map((incident) => (
              <tr key={incident.id}>
                <td>{incident.type}</td>
                <td>{incident.severity}</td>
                <td>{incident.status}</td>
                <td>{incident.latitude}, {incident.longitude}</td>
                <td>
                  <select className="form-select form-select-sm" onChange={(e) => handleAssign(incident.id, e.target.value)} defaultValue="">
                    <option value="">Assign resource</option>
                    {resources.map((resource) => (
                      <option key={resource.id} value={resource.id}>{resource.name} ({resource.type})</option>
                    ))}
                  </select>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
