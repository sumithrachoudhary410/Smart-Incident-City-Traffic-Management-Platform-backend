import { useEffect, useState } from "react";
import API from "../services/api";

export default function NotificationsPage() {
  const [notifications, setNotifications] = useState([]);

  const fetchData = async () => {
    const response = await API.get("/notifications");
    setNotifications(response.data);
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div className="panel-card">
      <h5>Inbox</h5>
      <div className="list-group">
        {notifications.length === 0 ? <div className="list-group-item">No notifications yet.</div> : notifications.map((item) => (
          <div key={item.id} className="list-group-item">
            <div className="d-flex justify-content-between">
              <strong>{item.title}</strong>
              <small>{item.read ? "Read" : "New"}</small>
            </div>
            <div>{item.message}</div>
          </div>
        ))}
      </div>
    </div>
  );
}
