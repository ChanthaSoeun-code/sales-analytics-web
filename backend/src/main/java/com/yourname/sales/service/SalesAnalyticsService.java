package com.yourname.sales.service;

import com.yourname.sales.model.Order;

import java.time.YearMonth;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SalesAnalyticsService {

    private final List<Order> orders;

    public SalesAnalyticsService(List<Order> orders) {
        this.orders = Collections.unmodifiableList(new ArrayList<>(orders));
    }

    /** បង្កើត service ថ្មីដែលមានតែ order ដែលត្រូវនឹង Predicate (ឧ. ទិន្នន័យតែមួយខែ) */
    public SalesAnalyticsService subset(Predicate<Order> predicate) {
        return new SalesAnalyticsService(filter(predicate));
    }

    public int orderCount() {
        return orders.size();
    }

    /** ច្រោះ order តាម Predicate */
    public List<Order> filter(Predicate<Order> predicate) {
        return orders.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /** បញ្ជីប្រភេទផលិតផល (មិនស្ទួន, តម្រៀប) */
    public List<String> categories() {
        return orders.stream()
                .map(Order::getCategory)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    /** ចំណូលសរុប (តាម filter) */
    public double totalRevenue(Predicate<Order> predicate) {
        return orders.stream()
                .filter(predicate)
                .mapToDouble(Order::getTotal)
                .sum();
    }

    /** តម្លៃមធ្យមក្នុង 1 order */
    public double averageOrderValue() {
        return orders.stream()
                .mapToDouble(Order::getTotal)
                .average()
                .orElse(0.0);
    }

    /** ចំណូលតាមប្រភេទ */
    public Map<String, Double> revenueByCategory() {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCategory,
                        TreeMap::new,
                        Collectors.summingDouble(Order::getTotal)));
    }

    /** ចំណូលតាមខែ (តម្រៀបតាមខែ) */
    public Map<YearMonth, Double> revenueByMonth() {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getMonth,
                        TreeMap::new,
                        Collectors.summingDouble(Order::getTotal)));
    }

    /** Top N ផលិតផលលក់ដាច់បំផុត (តាមចំនួន) */
    public List<Map.Entry<String, Integer>> topProducts(int n) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getProductName,
                        Collectors.summingInt(Order::getQuantity)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(n)
                .collect(Collectors.toList());
    }

    /** អតិថិជនចំណាយច្រើនបំផុត */
    public Optional<Map.Entry<String, Double>> topCustomer() {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomerName,
                        Collectors.summingDouble(Order::getTotal)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue());
    }
}
