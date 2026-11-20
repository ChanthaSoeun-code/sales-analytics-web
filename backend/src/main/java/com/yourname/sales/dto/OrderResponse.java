package com.yourname.sales.dto;

import com.yourname.sales.model.Order;

public class OrderResponse {
    private final String id;
    private final String customer;
    private final String product;
    private final String category;
    private final int quantity;
    private final double total;
    private final String date; // ISO format: 2026-11-17

    public OrderResponse(String id, String customer, String product, String category,
                         int quantity, double total, String date) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.category = category;
        this.quantity = quantity;
        this.total = total;
        this.date = date;
    }

    public static OrderResponse from(Order o) {
        return new OrderResponse(
                o.getId(),
                o.getCustomerName(),
                o.getProductName(),
                o.getCategory(),
                o.getQuantity(),
                Math.round(o.getTotal() * 100.0) / 100.0,
                o.getDate().toString());
    }

    public String getId() { return id; }
    public String getCustomer() { return customer; }
    public String getProduct() { return product; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public double getTotal() { return total; }
    public String getDate() { return date; }
}
