package com.yourname.sales.config;

import com.yourname.sales.model.Customer;
import com.yourname.sales.model.Order;
import com.yourname.sales.model.Product;
import com.yourname.sales.service.SalesAnalyticsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Sample data in memory. នៅ Phase បន្ទាប់ អាចប្តូរទៅអាន CSV ឬ Database។
 */
@Configuration
public class SalesDataConfig {

    @Bean
    public SalesAnalyticsService salesAnalyticsService() {
        return new SalesAnalyticsService(sampleOrders());
    }

    private List<Order> sampleOrders() {
        Product coffee = new Product("P1", "Coffee", "Drink", 2.50);
        Product tea    = new Product("P2", "Green Tea", "Drink", 2.00);
        Product cake   = new Product("P3", "Cake", "Food", 4.00);
        Product bread  = new Product("P4", "Bread", "Food", 1.50);
        Product mug    = new Product("P5", "Mug", "Merch", 8.00);
        Product tote   = new Product("P6", "Tote Bag", "Merch", 6.00);

        Customer sokha  = new Customer("C1", "Sokha");
        Customer dara   = new Customer("C2", "Dara");
        Customer sophea = new Customer("C3", "Sophea");
        Customer vanna  = new Customer("C4", "Vanna");

        return Arrays.asList(
                new Order("O1",  sokha,  coffee, 5,  LocalDate.of(2026, 9, 3)),
                new Order("O2",  dara,   cake,   2,  LocalDate.of(2026, 9, 10)),
                new Order("O3",  sophea, tea,    10, LocalDate.of(2026, 9, 21)),
                new Order("O4",  vanna,  bread,  8,  LocalDate.of(2026, 9, 28)),
                new Order("O5",  sokha,  mug,    1,  LocalDate.of(2026, 10, 2)),
                new Order("O6",  dara,   coffee, 8,  LocalDate.of(2026, 10, 12)),
                new Order("O7",  sophea, bread,  6,  LocalDate.of(2026, 10, 25)),
                new Order("O8",  vanna,  tote,   3,  LocalDate.of(2026, 10, 29)),
                new Order("O9",  sokha,  cake,   3,  LocalDate.of(2026, 11, 1)),
                new Order("O10", dara,   coffee, 4,  LocalDate.of(2026, 11, 5)),
                new Order("O11", sophea, mug,    2,  LocalDate.of(2026, 11, 9)),
                new Order("O12", vanna,  tea,    7,  LocalDate.of(2026, 11, 14))
        );
    }
}
