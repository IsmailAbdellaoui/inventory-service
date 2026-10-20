# inventory-service (demo)

A small Java 17 / Maven app used as a **test target for cve-patcher**. It loads a warehouse
inventory from YAML, logs low-stock alerts and writes a JSON report.

**Its dependencies are deliberately outdated and vulnerable. Don't deploy it.**

## Build and run (no Java needed, uses Docker)

```bash
./build.sh                       # compile + unit tests
./build.sh compile exec:java     # run the app, writes target/inventory-report.json
./build.sh dependency:tree       # all dependencies, transitive ones included
```

## Known vulnerable dependencies

| Dependency | Version | Direct? | Example CVEs | Fixed in (same line) |
|---|---|---|---|---|
| org.apache.logging.log4j:log4j-core | 2.14.1 | direct | CVE-2021-44228 (Log4Shell), CVE-2021-45046, CVE-2021-44832 | 2.15.0 for Log4Shell, 2.17.1 for all |
| com.fasterxml.jackson.core:jackson-databind | 2.12.3 | direct | CVE-2020-36518, CVE-2022-42003 | 2.12.6.1 / 2.12.7.1 |
| org.yaml:snakeyaml | 1.27 | **transitive** (via jackson-dataformat-yaml) | CVE-2022-25857, CVE-2022-1471 | 1.31 / 2.0 (major) |
| org.apache.commons:commons-text | 1.9 | direct | CVE-2022-42889 (Text4Shell) | 1.10.0 |
| com.google.guava:guava | 30.1-jre | direct | CVE-2023-2976, CVE-2020-8908 | 32.0.0-jre |

These cover the cases cve-patcher has to handle: a simple patch bump (log4j-core), a micro bump
(jackson-databind), a transitive dependency (snakeyaml), a minor bump (commons-text) and a
major bump (guava 30 to 32, snakeyaml 1.x to 2.0).

The unit tests (`./build.sh`) let the fix agent check that an upgrade doesn't break anything.
