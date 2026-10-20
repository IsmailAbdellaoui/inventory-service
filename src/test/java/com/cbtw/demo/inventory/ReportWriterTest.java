package com.cbtw.demo.inventory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportWriterTest {

    @Test
    void writesJsonReport(@TempDir Path dir) throws Exception {
        InventoryService service = new InventoryService(List.of(
                new Product("A-1", "Alpha", 2, new BigDecimal("10.00"), List.of())));
        ReportWriter writer = new ReportWriter();
        Path file = dir.resolve("out/report.json");

        writer.write(writer.buildReport("Test", service, 10), file);

        JsonNode json = new ObjectMapper().readTree(file.toFile());
        assertEquals("Test", json.get("warehouse").asText());
        assertEquals(1, json.get("productCount").asInt());
        assertEquals("A-1", json.get("lowStock").get(0).get("sku").asText());
    }
}
