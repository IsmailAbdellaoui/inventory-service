package com.cbtw.demo.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/** Content of an inventory file: a warehouse name and its products. */
public final class Inventory {

    private final String warehouse;
    private final List<Product> products;

    @JsonCreator
    public Inventory(@JsonProperty("warehouse") String warehouse,
                     @JsonProperty("products") List<Product> products) {
        this.warehouse = warehouse;
        this.products = products == null ? List.of() : List.copyOf(products);
    }

    public String getWarehouse() {
        return warehouse;
    }

    public List<Product> getProducts() {
        return products;
    }
}
