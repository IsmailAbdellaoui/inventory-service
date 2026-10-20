#!/usr/bin/env bash
# Builds and tests the app in a Maven container (no Java needed on your machine).
# Usage: ./build.sh [maven goals]     default: test
#   ./build.sh test                        compile + run the unit tests
#   ./build.sh compile exec:java           run the app
#   ./build.sh dependency:tree             show all dependencies, transitive ones included
cd "$(dirname "$0")"
dir="$(pwd -W 2>/dev/null || pwd)"   # Windows path under Git Bash, normal path elsewhere
MSYS_NO_PATHCONV=1 docker run --rm \
  -v "$dir":/app -w /app \
  -v inventory-service-m2:/root/.m2 \
  maven:3.9-eclipse-temurin-17 \
  mvn -B -ntp "${@:-test}"
