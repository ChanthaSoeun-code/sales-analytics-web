// ទីតាំងតែមួយសម្រាប់ហៅ backend API ទាំងអស់
const BASE = import.meta.env.VITE_API_URL || "/api";

async function get(path, params = {}) {
  const query = new URLSearchParams(
    Object.entries(params).filter(([, v]) => v !== "" && v != null)
  ).toString();

  const res = await fetch(`${BASE}${path}${query ? `?${query}` : ""}`);
  if (!res.ok) {
    throw new Error(`Request failed (${res.status})`);
  }
  return res.json();
}

export const getDashboard = (month) => get("/dashboard", { month });
export const getOrders = (filters = {}) => get("/orders", filters);
export const getTopProducts = (limit = 5) => get("/top-products", { limit });
export const getCategories = () => get("/categories");
