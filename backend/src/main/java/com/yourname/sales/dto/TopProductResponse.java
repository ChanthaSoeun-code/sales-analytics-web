package com.yourname.sales.dto;

import java.util.Map;

public class TopProductResponse {
    private final String name;
    private final int quantitySold;

    public TopProductResponse(String name, int quantitySold) {
        this.name = name;
        this.quantitySold = quantitySold;
    }

    public static TopProductResponse from(Map.Entry<String, Integer> entry) {
        return new TopProductResponse(entry.getKey(), entry.getValue());
    }

    public String getName() { return name; }
    public int getQuantitySold() { return quantitySold; }
}
