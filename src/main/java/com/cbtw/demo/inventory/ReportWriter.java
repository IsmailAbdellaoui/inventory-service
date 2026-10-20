package com.cbtw.demo.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/** Writes a JSON report of the inventory (Jackson). */
public class ReportWriter {

    private final ObjectMapper json = new ObjectMapper();

    public Map<String, Object> buildReport(String warehouse, InventoryService service, int lowStockThreshold) {
        Map<String, Object> report = new LinkedHashMap<>();
        report.put("warehouse", warehouse);
        report.put("productCount", service.size());
        report.put("totalValue", service.totalValue());
        report.put("lowStock", service.lowStock(lowStockThreshold));
        report.put("products", service.all());
        return report;
    }

    public void write(Map<String, Object> report, Path file) {
        try {
            if (file.getParent() != null) {
                Files.createDirectories(file.getParent());
            }
            json.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), report);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot write report " + file, e);
        }
    }
}
