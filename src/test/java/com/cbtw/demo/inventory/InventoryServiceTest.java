package com.cbtw.demo.inventory;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InventoryServiceTest {

    private final InventoryService service = new InventoryService(List.of(
            new Product("A-1", "Alpha", 5, new BigDecimal("10.00"), List.of("x")),
            new Product("B-2", "Beta", 20, new BigDecimal("2.50"), List.of("x", "y")),
            new Product("C-3", "Gamma", 1, new BigDecimal("100.00"), null)));

    @Test
    void lowStockIsSortedByQuantity() {
        List<Product> low = service.lowStock(10);
        assertEquals(List.of("C-3", "A-1"), low.stream().map(Product::getSku).toList());
    }

    @Test
    void totalValueSumsAllLines() {
        assertEquals(new BigDecimal("200.00"), service.totalValue());  // 50 + 50 + 100
    }

    @Test
    void findBySkuAndTags() {
        assertTrue(service.findBySku("B-2").isPresent());
        assertTrue(service.findBySku("nope").isEmpty());
        assertEquals(2, service.withTag("x").size());
    }

    @Test
    void rejectsNegativeThreshold() {
        assertThrows(IllegalArgumentException.class, () -> service.lowStock(-1));
    }

    @Test
    void rejectsDuplicateSkus() {
        Product p = new Product("D-4", "Delta", 1, BigDecimal.ONE, List.of());
        assertThrows(IllegalArgumentException.class, () -> new InventoryService(List.of(p, p)));
    }
}
