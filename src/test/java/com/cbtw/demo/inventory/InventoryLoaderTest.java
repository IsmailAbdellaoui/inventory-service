package com.cbtw.demo.inventory;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InventoryLoaderTest {

    @Test
    void loadsBundledInventory() {
        Inventory inventory = new InventoryLoader().loadFromClasspath("inventory.yaml");
        assertEquals("Tunis-North", inventory.getWarehouse());
        assertEquals(5, inventory.getProducts().size());

        Product monitor = inventory.getProducts().get(2);
        assertEquals("MN-2701", monitor.getSku());
        assertEquals(3, monitor.getQuantity());
        assertEquals(0, new BigDecimal("279.00").compareTo(monitor.getUnitPrice()));
        assertEquals(2, monitor.getTags().size());
    }
}
