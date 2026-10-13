import { useEffect, useState } from "react";
import { Bar, Doughnut } from "react-chartjs-2";
import { Chart as ChartJS, BarElement, CategoryScale, ArcElement, LinearScale, Tooltip, Legend } from "chart.js";
import API from "../services/api";

ChartJS.register(BarElement, CategoryScale, LinearScale, ArcElement, Tooltip, Legend);

export default function DashboardPage() {
  const [summary, setSummary] = useState({});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    API.get("/dashboard/summary")
      .then((response) => setSummary(response.data))
      .finally(() => setLoading(false));
  }, []);

  const summaryCards = [
    { label: "Total Intersections", value: summary.totalIntersections || 0, icon: "fa-map-location-dot", color: "primary" },
    { label: "High Congestion", value: summary.highCongestionIntersections || 0, icon: "fa-traffic-light", color: "warning" },
    { label: "Active Incidents", value: summary.activeIncidents || 0, icon: "fa-triangle-exclamation", color: "danger" },
    { label: "Available Resources", value: summary.availableEmergencyResources || 0, icon: "fa-ambulance", color: "success" }
  ];

  const trafficChart = {
    labels: ["Central", "Town Hall", "Market", "Airport"],
    datasets: [{
      label: "Congestion Index",
      data: [68, 55, 81, 40],
      backgroundColor: ["#0d6efd", "#ffc107", "#dc3545", "#198754"]
    }]
  };

  const incidentChart = {
    labels: ["Accident", "Medical", "Fire", "Road Blockage"],
    datasets: [{
      data: [8, 5, 3, 6],
      backgroundColor: ["#0d6efd", "#20c997", "#fd7e14", "#6f42c1"]
    }]
  };

  if (loading) return <div className="loading-box">Loading dashboard...</div>;

  return (
    <div>
      <div className="row g-3 mb-4">
        {summaryCards.map((item) => (
          <div key={item.label} className="col-md-6 col-xl-3">
            <div className="stat-card border-${item.color}">
              <div className="stat-icon bg-${item.color}"><i className={`fa-solid ${item.icon}`} /></div>
              <div>
                <small>{item.label}</small>
                <h3>{item.value}</h3>
              </div>
            </div>
          </div>
        ))}
      </div>

      <div className="row g-3">
        <div className="col-lg-8">
          <div className="panel-card">
            <h5>Traffic Congestion by Intersection</h5>
            <Bar data={trafficChart} options={{ responsive: true, maintainAspectRatio: false }} />
          </div>
        </div>
        <div className="col-lg-4">
          <div className="panel-card">
            <h5>Incidents by Type</h5>
            <Doughnut data={incidentChart} options={{ responsive: true, maintainAspectRatio: false }} />
          </div>
        </div>
      </div>

      <div className="row g-3 mt-2">
        <div className="col-md-6">
          <div className="panel-card">
            <h5>City Status</h5>
            <ul className="summary-list">
              <li>Critical incidents: {summary.criticalIncidents || 0}</li>
              <li>Pending civic issues: {summary.pendingCivicIssues || 0}</li>
              <li>Resolved issues: {summary.resolvedIssues || 0}</li>
              <li>Average response time: {summary.averageIncidentResponseTimeHours || "N/A"} hours</li>
            </ul>
          </div>
        </div>
        <div className="col-md-6">
          <div className="panel-card">
            <h5>Operational Notes</h5>
            <p className="mb-1">The prototype uses configurable demo thresholds and illustrative traffic timings.</p>
            <p className="mb-0 text-muted">This project is designed for academic demonstration, not live public signal control.</p>
          </div>
        </div>
      </div>
    </div>
  );
}
