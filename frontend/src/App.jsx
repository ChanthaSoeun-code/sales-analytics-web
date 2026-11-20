import { useState } from "react";
import Dashboard from "./pages/Dashboard.jsx";
import Orders from "./pages/Orders.jsx";

const TABS = [
  { id: "dashboard", label: "Dashboard" },
  { id: "orders", label: "Orders" },
];

export default function App() {
  const [tab, setTab] = useState("dashboard");

  return (
    <div className="app">
      <header className="topbar">
        <h1>Sales Analytics</h1>
        <nav>
          {TABS.map((t) => (
            <button
              key={t.id}
              className={tab === t.id ? "tab active" : "tab"}
              onClick={() => setTab(t.id)}
            >
              {t.label}
            </button>
          ))}
        </nav>
      </header>

      <main className="content">
        {tab === "dashboard" ? <Dashboard /> : <Orders />}
      </main>
    </div>
  );
}
