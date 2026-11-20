# Sales Analytics Web

A full-stack sales dashboard that turns raw order data into answers a shop owner can act on.

![Dashboard](docs/dashboard.png)

## The problem

Shop owners ask the same questions every week: *What sells best? Which category earns the most?
Who are my best customers? Is revenue growing month to month?*
This app answers them in one screen.

## Features

- **Dashboard**: total revenue, order count, average order value, top customer
- **Charts**: revenue by category and by month
- **Top products**: best sellers ranked by quantity sold
- **Orders explorer**: filter by category, customer and minimum total
- **Month filter**: view the whole dashboard for a single month

![Orders](docs/orders.png)

## Tech stack

| Layer | Technology |
|-------|------------|
| Backend | Java 17+, Spring Boot 3.5, Maven |
| Business logic | Java Stream API, functional interfaces (`Predicate`, `Consumer`), method references |
| Frontend | React 18, Vite |
| Testing | JUnit 5 |

## How it works

Filters are small reusable `Predicate`s that are combined at runtime, and all analytics are
built with Streams and Collectors:

```java
// Filters are composed from the query parameters
Predicate<Order> filter = OrderFilters.byCategory("Drink")
        .and(OrderFilters.minTotal(10));

// Top N products by quantity sold
orders.stream()
      .collect(Collectors.groupingBy(Order::getProductName,
               Collectors.summingInt(Order::getQuantity)))
      .entrySet().stream()
      .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
      .limit(5)
      .collect(Collectors.toList());
```

## Project structure

```
sales-analytics-web/
  backend/    Spring Boot REST API (port 8080)
    controller/  REST endpoints
    service/     Stream-based analytics logic
    filter/      Reusable Predicates
    dto/         API response models
    model/       Product, Customer, Order
  frontend/   React app (port 5173)
    pages/       Dashboard, Orders
    components/  StatCard, BarChart, tables
    services/    API client
```

## Getting started

**Requirements:** JDK 17+, Maven 3.6.3+, Node.js 18+

**1. Start the backend**
```bash
cd backend
mvn spring-boot:run
```
Check it works: http://localhost:8080/api/dashboard

**2. Start the frontend** (in a second terminal)
```bash
cd frontend
npm install
npm run dev
```
Open the URL shown in the terminal (usually http://localhost:5173).
Vite proxies `/api` to `localhost:8080`, so no extra CORS setup is needed in development.

## API

| Endpoint | Description |
|----------|-------------|
| `GET /api/dashboard?month=2026-11` | Totals, revenue by category/month, top products, top customer (`month` is optional) |
| `GET /api/orders?category=&customer=&minTotal=` | Filtered orders (filters combined with `Predicate.and`) |
| `GET /api/top-products?limit=5` | Best-selling products |
| `GET /api/categories` | Distinct categories |

## Tests

```bash
cd backend
mvn test
```

## Roadmap

- [ ] Load orders from CSV / database (Spring Data JPA)
- [ ] Date-range filter and CSV export
- [ ] Docker setup and live demo (Render / Railway)

## Author

**Your Name**
GitHub: [ChanthaSoeun-code](https://github.com/ChanthaSoeun-code) - soeunchantha07@gmail.com
