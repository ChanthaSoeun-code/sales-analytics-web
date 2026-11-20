import { useEffect, useState } from "react";
import { getCategories, getOrders } from "../services/analyticsApi.js";
import OrdersTable from "../components/OrdersTable.jsx";

export default function Orders() {
  const [categories, setCategories] = useState([]);
  const [filters, setFilters] = useState({ category: "", customer: "", minTotal: "" });
  const [orders, setOrders] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    getCategories().then(setCategories).catch((e) => setError(e.message));
  }, []);

  useEffect(() => {
    setError("");
    getOrders(filters).then(setOrders).catch((e) => setError(e.message));
  }, [filters]);

  const update = (key) => (e) => setFilters({ ...filters, [key]: e.target.value });

  return (
    <div>
      <div className="toolbar">
        <label>
          Category{" "}
          <select value={filters.category} onChange={update("category")}>
            <option value="">All</option>
            {categories.map((c) => (
              <option key={c} value={c}>{c}</option>
            ))}
          </select>
        </label>

        <label>
          Customer{" "}
          <input
            type="text"
            placeholder="e.g. Sokha"
            value={filters.customer}
            onChange={update("customer")}
          />
        </label>

        <label>
          Min total ($){" "}
          <input
            type="number"
            min="0"
            value={filters.minTotal}
            onChange={update("minTotal")}
          />
        </label>
      </div>

      {error ? <p className="error">Could not load orders: {error}</p> : <OrdersTable orders={orders} />}
    </div>
  );
}
