package com.cbtw.demo.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Reads an inventory from a YAML file (Jackson + SnakeYAML). */
public class InventoryLoader {

    private final ObjectMapper yaml = new ObjectMapper(new YAMLFactory());

    public Inventory load(Path file) {
        try (InputStream in = Files.newInputStream(file)) {
            return yaml.readValue(in, Inventory.class);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read inventory " + file, e);
        }
    }

    public Inventory loadFromClasspath(String resource) {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(resource)) {
            if (in == null) {
                throw new IllegalArgumentException("Resource not found: " + resource);
            }
            return yaml.readValue(in, Inventory.class);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read inventory resource " + resource, e);
        }
    }
}
