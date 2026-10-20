package com.cbtw.demo.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/** One product line in the warehouse. */
public final class Product {

    private final String sku;
    private final String name;
    private final int quantity;
    private final BigDecimal unitPrice;
    private final List<String> tags;

    @JsonCreator
    public Product(@JsonProperty("sku") String sku,
                   @JsonProperty("name") String name,
                   @JsonProperty("quantity") int quantity,
                   @JsonProperty("unitPrice") BigDecimal unitPrice,
                   @JsonProperty("tags") List<String> tags) {
        this.sku = Objects.requireNonNull(sku, "sku");
        this.name = Objects.requireNonNull(name, "name");
        this.quantity = quantity;
        this.unitPrice = Objects.requireNonNull(unitPrice, "unitPrice");
        this.tags = tags == null ? List.of() : List.copyOf(tags);
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public List<String> getTags() {
        return tags;
    }

    /** Stock value of this line: quantity x unit price. */
    @JsonIgnore
    public BigDecimal getStockValue() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return sku + " " + name + " x" + quantity;
    }
}
