export default function TopProductsTable({ products }) {
  return (
    <div className="card">
      <h2>Top products</h2>
      <table>
        <thead>
          <tr>
            <th>#</th>
            <th>Product</th>
            <th className="num">Sold</th>
          </tr>
        </thead>
        <tbody>
          {products.map((p, i) => (
            <tr key={p.name}>
              <td>{i + 1}</td>
              <td>{p.name}</td>
              <td className="num">{p.quantitySold}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
