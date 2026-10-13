import { useEffect, useState } from "react";
import API from "../services/api";

export default function TrafficPage() {
  const [intersections, setIntersections] = useState([]);
  const [observations, setObservations] = useState([]);
  const [form, setForm] = useState({ code: "INT-01", name: "Central Junction", latitude: 12.9716, longitude: 77.5946 });
  const [observation, setObservation] = useState({ intersectionId: "", vehicleCount: 120 });
  const [loading, setLoading] = useState(true);

  const fetchData = async () => {
    setLoading(true);
    const [intersectionRes, observationRes] = await Promise.all([
      API.get("/intersections"),
      API.get("/traffic/observations")
    ]);
    setIntersections(intersectionRes.data);
    setObservations(observationRes.data);
    setObservation((prev) => ({ ...prev, intersectionId: intersectionRes.data[0]?.id || "" }));
    setLoading(false);
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleCreateIntersection = async (e) => {
    e.preventDefault();
    await API.post("/intersections", form);
    fetchData();
  };

  const handleObservation = async (e) => {
    e.preventDefault();
    await API.post("/traffic/observations", null, {
      params: {
        intersectionId: observation.intersectionId,
        vehicleCount: observation.vehicleCount
      }
    });
    fetchData();
  };

  if (loading) return <div className="loading-box">Loading traffic data...</div>;

  return (
    <div>
      <div className="row g-3">
        <div className="col-lg-6">
          <div className="panel-card">
            <h5>Add Intersection</h5>
            <form onSubmit={handleCreateIntersection}>
              <div className="mb-2"><input className="form-control" placeholder="Code" value={form.code} onChange={(e) => setForm({ ...form, code: e.target.value })} /></div>
              <div className="mb-2"><input className="form-control" placeholder="Name" value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} /></div>
              <div className="row">
                <div className="col"><input className="form-control" placeholder="Latitude" value={form.latitude} onChange={(e) => setForm({ ...form, latitude: Number(e.target.value) })} /></div>
                <div className="col"><input className="form-control" placeholder="Longitude" value={form.longitude} onChange={(e) => setForm({ ...form, longitude: Number(e.target.value) })} /></div>
              </div>
              <button className="btn btn-primary mt-3">Save Intersection</button>
            </form>
          </div>
        </div>

        <div className="col-lg-6">
          <div className="panel-card">
            <h5>Traffic Observation</h5>
            <form onSubmit={handleObservation}>
              <div className="mb-2">
                <select className="form-select" value={observation.intersectionId} onChange={(e) => setObservation({ ...observation, intersectionId: e.target.value })}>
                  {intersections.map((item) => (
                    <option key={item.id} value={item.id}>{item.name}</option>
                  ))}
                </select>
              </div>
              <div className="mb-2">
                <input className="form-control" type="number" value={observation.vehicleCount} onChange={(e) => setObservation({ ...observation, vehicleCount: Number(e.target.value) })} />
              </div>
              <button className="btn btn-success">Record Observation</button>
            </form>
          </div>
        </div>
      </div>

      <div className="panel-card mt-3">
        <h5>Intersections</h5>
        <table className="table table-striped mb-0">
          <thead>
            <tr><th>Code</th><th>Name</th><th>Latitude</th><th>Longitude</th><th>Congestion</th></tr>
          </thead>
          <tbody>
            {intersections.map((item) => (
              <tr key={item.id}>
                <td>{item.code}</td>
                <td>{item.name}</td>
                <td>{item.latitude}</td>
                <td>{item.longitude}</td>
                <td><span className="badge bg-info">{item.congestionLevel || "LOW"}</span></td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="panel-card mt-3">
        <h5>Recent Observations</h5>
        <table className="table table-sm table-light">
          <thead><tr><th>Intersection</th><th>Vehicle Count</th><th>Density</th><th>Level</th></tr></thead>
          <tbody>
            {observations.map((item) => (
              <tr key={item.id}>
                <td>{item.intersection?.name || "Unknown"}</td>
                <td>{item.vehicleCount}</td>
                <td>{item.densityScore || 0}</td>
                <td>{item.congestionLevel || "LOW"}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
