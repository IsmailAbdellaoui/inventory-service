package com.cbtw.demo.inventory;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationFormatterTest {

    @Test
    void formatsLowStockMessage() {
        Product p = new Product("MS-2040", "Wireless mouse", 7, new BigDecimal("24.50"), List.of());
        assertEquals("Low stock: Wireless mouse (MS-2040) has only 7 unit(s) left",
                new NotificationFormatter().lowStock(p));
    }
}
