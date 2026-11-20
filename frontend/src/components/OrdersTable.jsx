export default function OrdersTable({ orders }) {
  if (orders.length === 0) {
    return <p className="muted">No orders match your filters.</p>;
  }

  return (
    <div className="card table-wrap">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Date</th>
            <th>Customer</th>
            <th>Product</th>
            <th>Category</th>
            <th className="num">Qty</th>
            <th className="num">Total</th>
          </tr>
        </thead>
        <tbody>
          {orders.map((o) => (
            <tr key={o.id}>
              <td>{o.id}</td>
              <td>{o.date}</td>
              <td>{o.customer}</td>
              <td>{o.product}</td>
              <td>{o.category}</td>
              <td className="num">{o.quantity}</td>
              <td className="num">${o.total.toFixed(2)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
