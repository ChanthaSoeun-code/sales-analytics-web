package com.yourname.sales;

import com.yourname.sales.filter.OrderFilters;
import com.yourname.sales.model.Customer;
import com.yourname.sales.model.Order;
import com.yourname.sales.model.Product;
import com.yourname.sales.service.SalesAnalyticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SalesAnalyticsServiceTest {

    private SalesAnalyticsService service;

    @BeforeEach
    void setUp() {
        Product coffee = new Product("P1", "Coffee", "Drink", 2.50);
        Product cake   = new Product("P2", "Cake", "Food", 4.00);
        Customer sokha = new Customer("C1", "Sokha");
        Customer dara  = new Customer("C2", "Dara");

        List<Order> orders = Arrays.asList(
                new Order("O1", sokha, coffee, 4, LocalDate.of(2026, 10, 1)), // 10.0
                new Order("O2", dara,  cake,   2, LocalDate.of(2026, 10, 5)), // 8.0
                new Order("O3", sokha, coffee, 2, LocalDate.of(2026, 11, 2))  // 5.0
        );
        service = new SalesAnalyticsService(orders);
    }

    @Test
    void totalRevenue_allOrders() {
        assertEquals(23.0, service.totalRevenue(OrderFilters.all()), 0.001);
    }

    @Test
    void totalRevenue_withPredicate() {
        assertEquals(15.0, service.totalRevenue(OrderFilters.byCategory("Drink")), 0.001);
    }

    @Test
    void predicates_canBeCombined() {
        double result = service.totalRevenue(
                OrderFilters.byCategory("Drink").and(OrderFilters.minTotal(10)));
        assertEquals(10.0, result, 0.001);
    }

    @Test
    void revenueByCategory_groupsCorrectly() {
        Map<String, Double> result = service.revenueByCategory();
        assertEquals(15.0, result.get("Drink"), 0.001);
        assertEquals(8.0, result.get("Food"), 0.001);
    }

    @Test
    void revenueByMonth_isSortedByMonth() {
        Map<YearMonth, Double> result = service.revenueByMonth();
        assertEquals(Arrays.asList(YearMonth.of(2026, 10), YearMonth.of(2026, 11)),
                Arrays.asList(result.keySet().toArray()));
        assertEquals(18.0, result.get(YearMonth.of(2026, 10)), 0.001);
    }

    @Test
    void topProducts_returnsHighestQuantityFirst() {
        assertEquals("Coffee", service.topProducts(1).get(0).getKey());
        assertEquals(6, service.topProducts(1).get(0).getValue());
    }

    @Test
    void topCustomer_isSokha() {
        assertEquals("Sokha", service.topCustomer().get().getKey());
    }

    @Test
    void subset_onlyContainsMatchingOrders() {
        SalesAnalyticsService october = service.subset(OrderFilters.inMonth(YearMonth.of(2026, 10)));
        assertEquals(2, october.orderCount());
        assertEquals(18.0, october.totalRevenue(OrderFilters.all()), 0.001);
    }

    @Test
    void categories_areDistinctAndSorted() {
        assertEquals(Arrays.asList("Drink", "Food"), service.categories());
    }

    @Test
    void emptyOrders_areHandledSafely() {
        SalesAnalyticsService empty = new SalesAnalyticsService(Collections.emptyList());
        assertEquals(0.0, empty.totalRevenue(OrderFilters.all()), 0.001);
        assertEquals(0.0, empty.averageOrderValue(), 0.001);
        assertFalse(empty.topCustomer().isPresent());
        assertTrue(empty.topProducts(3).isEmpty());
    }
}
