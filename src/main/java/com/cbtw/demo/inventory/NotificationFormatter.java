package com.cbtw.demo.inventory;

import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

/** Builds human-readable notifications from templates (Apache Commons Text). */
public class NotificationFormatter {

    static final String LOW_STOCK_TEMPLATE =
            "Low stock: ${name} (${sku}) has only ${quantity} unit(s) left";

    public String lowStock(Product product) {
        Map<String, Object> values = Map.of(
                "name", product.getName(),
                "sku", product.getSku(),
                "quantity", product.getQuantity());
        return new StringSubstitutor(values).replace(LOW_STOCK_TEMPLATE);
    }
}
