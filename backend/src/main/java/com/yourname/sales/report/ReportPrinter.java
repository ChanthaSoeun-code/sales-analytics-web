package com.yourname.sales.report;

import com.yourname.sales.filter.OrderFilters;
import com.yourname.sales.service.SalesAnalyticsService;

import java.util.function.Consumer;

/**
 * ប្រើ Consumer<String> ជា output → អាចបោះពុម្ពទៅ console, file, ឬ test បាន។
 */
public class ReportPrinter {

    private final SalesAnalyticsService service;
    private final Consumer<String> out;

    public ReportPrinter(SalesAnalyticsService service) {
        this(service, System.out::println);
    }

    public ReportPrinter(SalesAnalyticsService service, Consumer<String> out) {
        this.service = service;
        this.out = out;
    }

    public void printFullReport() {
        out.accept("========== SALES REPORT ==========");
        out.accept(String.format("Total revenue      : $%.2f", service.totalRevenue(OrderFilters.all())));
        out.accept(String.format("Average order value: $%.2f", service.averageOrderValue()));

        out.accept("\n--- Revenue by category ---");
        service.revenueByCategory().forEach((category, revenue) ->
                out.accept(String.format("%-12s $%.2f", category, revenue)));

        out.accept("\n--- Revenue by month ---");
        service.revenueByMonth().forEach((month, revenue) ->
                out.accept(String.format("%-12s $%.2f", month, revenue)));

        out.accept("\n--- Top 3 products ---");
        service.topProducts(3).forEach(e ->
                out.accept(String.format("%-15s %d sold", e.getKey(), e.getValue())));

        out.accept("\n--- Top customer ---");
        service.topCustomer().ifPresent(e ->
                out.accept(String.format("%s ($%.2f)", e.getKey(), e.getValue())));
        out.accept("==================================");
    }
}
