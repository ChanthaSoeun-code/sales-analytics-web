package com.yourname.sales.dto;

import com.yourname.sales.filter.OrderFilters;
import com.yourname.sales.service.SalesAnalyticsService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DashboardResponse {
    private final double totalRevenue;
    private final double averageOrderValue;
    private final int totalOrders;
    private final String topCustomerName;
    private final double topCustomerSpend;
    private final Map<String, Double> revenueByCategory;
    private final Map<String, Double> revenueByMonth;
    private final List<TopProductResponse> topProducts;

    private DashboardResponse(double totalRevenue, double averageOrderValue, int totalOrders,
                              String topCustomerName, double topCustomerSpend,
                              Map<String, Double> revenueByCategory,
                              Map<String, Double> revenueByMonth,
                              List<TopProductResponse> topProducts) {
        this.totalRevenue = totalRevenue;
        this.averageOrderValue = averageOrderValue;
        this.totalOrders = totalOrders;
        this.topCustomerName = topCustomerName;
        this.topCustomerSpend = topCustomerSpend;
        this.revenueByCategory = revenueByCategory;
        this.revenueByMonth = revenueByMonth;
        this.topProducts = topProducts;
    }

    /** សង់ DashboardResponse ពី SalesAnalyticsService */
    public static DashboardResponse from(SalesAnalyticsService service) {
        Map<String, Double> byCategory = new LinkedHashMap<>();
        service.revenueByCategory().forEach((k, v) -> byCategory.put(k, round2(v)));

        Map<String, Double> byMonth = new LinkedHashMap<>();
        service.revenueByMonth().forEach((k, v) -> byMonth.put(k.toString(), round2(v)));

        List<TopProductResponse> top = service.topProducts(5).stream()
                .map(TopProductResponse::from)
                .collect(Collectors.toList());

        String customerName = service.topCustomer().map(Map.Entry::getKey).orElse("-");
        double customerSpend = service.topCustomer().map(Map.Entry::getValue).orElse(0.0);

        return new DashboardResponse(
                round2(service.totalRevenue(OrderFilters.all())),
                round2(service.averageOrderValue()),
                service.orderCount(),
                customerName,
                round2(customerSpend),
                byCategory,
                byMonth,
                top);
    }

    private static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    public double getTotalRevenue() { return totalRevenue; }
    public double getAverageOrderValue() { return averageOrderValue; }
    public int getTotalOrders() { return totalOrders; }
    public String getTopCustomerName() { return topCustomerName; }
    public double getTopCustomerSpend() { return topCustomerSpend; }
    public Map<String, Double> getRevenueByCategory() { return revenueByCategory; }
    public Map<String, Double> getRevenueByMonth() { return revenueByMonth; }
    public List<TopProductResponse> getTopProducts() { return topProducts; }
}
