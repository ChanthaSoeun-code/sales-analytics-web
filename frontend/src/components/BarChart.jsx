// Bar chart សាមញ្ញដោយ CSS (មិនចាំបាច់ដំឡើង library បន្ថែម)
export default function BarChart({ title, data, formatValue = (v) => v }) {
  const entries = Object.entries(data || {});
  const max = Math.max(...entries.map(([, v]) => v), 1);

  return (
    <div className="card">
      <h2>{title}</h2>
      {entries.length === 0 && <p className="muted">No data</p>}
      {entries.map(([label, value]) => (
        <div className="bar-row" key={label}>
          <span className="bar-label">{label}</span>
          <div className="bar-track">
            <div className="bar-fill" style={{ width: `${(value / max) * 100}%` }} />
          </div>
          <span className="bar-value">{formatValue(value)}</span>
        </div>
      ))}
    </div>
  );
}
