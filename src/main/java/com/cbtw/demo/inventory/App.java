package com.cbtw.demo.inventory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Map;

/**
 * Loads an inventory, logs low-stock alerts and writes a JSON report.
 *
 * Usage: mvn -q compile exec:java [-Dexec.args="path/to/inventory.yaml"]
 */
public class App {

    private static final Logger LOG = LogManager.getLogger(App.class);
    static final int LOW_STOCK_THRESHOLD = 10;

    public static void main(String[] args) {
        InventoryLoader loader = new InventoryLoader();
        Inventory inventory = args.length > 0
                ? loader.load(Path.of(args[0]))
                : loader.loadFromClasspath("inventory.yaml");

        InventoryService service = new InventoryService(inventory.getProducts());
        LOG.info("Warehouse {}: {} products, total stock value {}",
                inventory.getWarehouse(), service.size(), service.totalValue());

        NotificationFormatter formatter = new NotificationFormatter();
        for (Product product : service.lowStock(LOW_STOCK_THRESHOLD)) {
            LOG.warn(formatter.lowStock(product));
        }

        ReportWriter writer = new ReportWriter();
        Map<String, Object> report = writer.buildReport(inventory.getWarehouse(), service, LOW_STOCK_THRESHOLD);
        Path reportFile = Path.of("target", "inventory-report.json");
        writer.write(report, reportFile);
        LOG.info("Report written to {}", reportFile.toAbsolutePath());
    }
}
