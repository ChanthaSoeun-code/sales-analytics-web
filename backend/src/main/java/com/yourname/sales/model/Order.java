package com.yourname.sales.model;

import java.time.LocalDate;
import java.time.YearMonth;

public class Order {
    private final String id;
    private final Customer customer;
    private final Product product;
    private final int quantity;
    private final LocalDate date;

    public Order(String id, Customer customer, Product product, int quantity, LocalDate date) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.date = date;
    }

    public String getId() { return id; }
    public Customer getCustomer() { return customer; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public LocalDate getDate() { return date; }

    // Convenience getters (ងាយស្រួលប្រើជា method reference)
    public String getCustomerName() { return customer.getName(); }
    public String getProductName() { return product.getName(); }
    public String getCategory() { return product.getCategory(); }
    public YearMonth getMonth() { return YearMonth.from(date); }

    public double getTotal() {
        return product.getPrice() * quantity;
    }
}
