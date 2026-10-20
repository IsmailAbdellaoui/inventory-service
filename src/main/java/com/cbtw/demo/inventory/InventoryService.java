package com.cbtw.demo.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/** Queries over the products of one inventory. */
public class InventoryService {

    private final ImmutableList<Product> products;
    private final ImmutableMap<String, Product> bySku;

    public InventoryService(List<Product> products) {
        Preconditions.checkNotNull(products, "products");
        this.products = ImmutableList.copyOf(products);
        this.bySku = Maps.uniqueIndex(this.products, Product::getSku);  // fails on duplicate SKUs
    }

    public int size() {
        return products.size();
    }

    public List<Product> all() {
        return products;
    }

    public Optional<Product> findBySku(String sku) {
        return Optional.ofNullable(bySku.get(sku));
    }

    /** Products whose quantity is strictly below the threshold, lowest stock first. */
    public List<Product> lowStock(int threshold) {
        Preconditions.checkArgument(threshold >= 0, "threshold must be >= 0, got %s", threshold);
        return products.stream()
                .filter(p -> p.getQuantity() < threshold)
                .sorted(Comparator.comparingInt(Product::getQuantity))
                .collect(ImmutableList.toImmutableList());
    }

    public List<Product> withTag(String tag) {
        return products.stream()
                .filter(p -> p.getTags().contains(tag))
                .collect(ImmutableList.toImmutableList());
    }

    public BigDecimal totalValue() {
        return products.stream()
                .map(Product::getStockValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
