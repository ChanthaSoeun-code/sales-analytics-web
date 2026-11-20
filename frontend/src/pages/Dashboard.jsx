import { useEffect, useState } from "react";
import { getDashboard } from "../services/analyticsApi.js";
import StatCard from "../components/StatCard.jsx";
import BarChart from "../components/BarChart.jsx";
import TopProductsTable from "../components/TopProductsTable.jsx";

const money = (v) => `$${Number(v).toFixed(2)}`;

export default function Dashboard() {
  const [month, setMonth] = useState("");
  const [months, setMonths] = useState([]);
  const [data, setData] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    setError("");
    getDashboard(month)
      .then((d) => {
        setData(d);
        // ចងចាំបញ្ជីខែ ពេលមិនទាន់ filter
        if (month === "") setMonths(Object.keys(d.revenueByMonth));
      })
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [month]);

  if (error) return <p className="error">Could not load data: {error}</p>;
  if (!data) return <p className="muted">Loading...</p>;

  return (
    <div>
      <div className="toolbar">
        <label>
          Month{" "}
          <select value={month} onChange={(e) => setMonth(e.target.value)}>
            <option value="">All time</option>
            {months.map((m) => (
              <option key={m} value={m}>{m}</option>
            ))}
          </select>
        </label>
        {loading && <span className="muted">Updating...</span>}
      </div>

      <div className="grid stats">
        <StatCard label="Total revenue" value={money(data.totalRevenue)} />
        <StatCard label="Orders" value={data.totalOrders} />
        <StatCard label="Avg. order value" value={money(data.averageOrderValue)} />
        <StatCard
          label="Top customer"
          value={data.topCustomerName}
          hint={money(data.topCustomerSpend)}
        />
      </div>

      <div className="grid two">
        <BarChart title="Revenue by category" data={data.revenueByCategory} formatValue={money} />
        <BarChart title="Revenue by month" data={data.revenueByMonth} formatValue={money} />
      </div>

      <TopProductsTable products={data.topProducts} />
    </div>
  );
}
