# TDG Configuration

## Project Information
- Language: Java 21
- Framework: Spring Boot 4.0.5
- Test Framework: JUnit 5 (Jupiter)

## Build Command
./gradlew build

## Test Command
./gradlew test

## Single Test Command
./gradlew test --tests "com.ddd_in_java.workshop.<TestClassName>"

## Coverage Command
./gradlew test jacocoTestReport

## Test File Patterns
- Test files: `*Tests.java`, `*Test.java`
- Test directory: `src/test/java/`
