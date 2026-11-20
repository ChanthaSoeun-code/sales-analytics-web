package com.yourname.sales.controller;

import com.yourname.sales.dto.DashboardResponse;
import com.yourname.sales.dto.OrderResponse;
import com.yourname.sales.dto.TopProductResponse;
import com.yourname.sales.filter.OrderFilters;
import com.yourname.sales.model.Order;
import com.yourname.sales.service.SalesAnalyticsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AnalyticsController {

    private final SalesAnalyticsService service;

    public AnalyticsController(SalesAnalyticsService service) {
        this.service = service;
    }

    /** GET /api/dashboard?month=2026-11  (month ជាជម្រើស) */
    @GetMapping("/dashboard")
    public DashboardResponse dashboard(@RequestParam(required = false) String month) {
        SalesAnalyticsService scope = service;
        if (month != null && !month.isEmpty()) {
            scope = service.subset(OrderFilters.inMonth(parseMonth(month)));
        }
        return DashboardResponse.from(scope);
    }

    /** GET /api/orders?category=Drink&customer=Sokha&minTotal=10 */
    @GetMapping("/orders")
    public List<OrderResponse> orders(@RequestParam(required = false) String category,
                                      @RequestParam(required = false) String customer,
                                      @RequestParam(required = false) Double minTotal) {
        // ផ្សំ Predicate តាម parameter ដែលបានផ្ញើមក
        Predicate<Order> filter = OrderFilters.all();
        if (category != null && !category.isEmpty()) {
            filter = filter.and(OrderFilters.byCategory(category));
        }
        if (customer != null && !customer.isEmpty()) {
            filter = filter.and(OrderFilters.byCustomer(customer));
        }
        if (minTotal != null) {
            filter = filter.and(OrderFilters.minTotal(minTotal));
        }

        return service.filter(filter).stream()
                .sorted(Comparator.comparing(Order::getDate).reversed())
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    /** GET /api/top-products?limit=5 */
    @GetMapping("/top-products")
    public List<TopProductResponse> topProducts(@RequestParam(defaultValue = "5") int limit) {
        int safeLimit = Math.max(1, Math.min(limit, 50));
        return service.topProducts(safeLimit).stream()
                .map(TopProductResponse::from)
                .collect(Collectors.toList());
    }

    /** GET /api/categories */
    @GetMapping("/categories")
    public List<String> categories() {
        return service.categories();
    }

    private YearMonth parseMonth(String month) {
        try {
            return YearMonth.parse(month);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid month '" + month + "'. Use format yyyy-MM, e.g. 2026-11");
        }
    }
}
