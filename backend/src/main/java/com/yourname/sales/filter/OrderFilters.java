package com.yourname.sales.filter;

import com.yourname.sales.model.Order;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.function.Predicate;

/**
 * Reusable Predicates. អាចផ្សំគ្នាបានដោយ and(), or(), negate().
 */
public final class OrderFilters {

    private OrderFilters() {}

    public static Predicate<Order> all() {
        return o -> true;
    }

    public static Predicate<Order> byCategory(String category) {
        return o -> o.getCategory().equalsIgnoreCase(category);
    }

    public static Predicate<Order> byCustomer(String customerName) {
        return o -> o.getCustomerName().equalsIgnoreCase(customerName);
    }

    public static Predicate<Order> minTotal(double min) {
        return o -> o.getTotal() >= min;
    }

    public static Predicate<Order> inMonth(YearMonth month) {
        return o -> o.getMonth().equals(month);
    }

    public static Predicate<Order> between(LocalDate from, LocalDate to) {
        return o -> !o.getDate().isBefore(from) && !o.getDate().isAfter(to);
    }
}
