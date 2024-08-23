# Regression Testing Guide

This document outlines the regression testing process for our project using Maven profiles.

## Available Profiles

There are two Maven profiles set up for regression testing:

1. `regression-testing-rest`: For REST API tests
2. `regression-testing-ui`: For UI tests using Selenium

## Running Tests

### REST API Tests

To run the REST API regression tests, use the following command:
```bash
mvn clean verify -P regression-testing-rest
```
This profile will:
- Skip the default test phase
- Run tests during the integration-test phase
- Include only test files matching the pattern `**/rest/**/*Test.java`

### UI Tests

To run the UI regression tests, use the following command:
```bash
mvn clean verify -P regression-testing-ui
```
This profile will:
- Skip the default test phase
- Run tests during the integration-test phase
- Include only test files matching the pattern `**/selenium/**/*Test.java`

## Configuration Details

Both profiles use the Maven Surefire Plugin with the following key configurations:

- Tests are skipped during the default test phase
- Tests are executed during the integration-test phase
- All tests are initially excluded, then specific patterns are included

## Notes

- Ensure that your test files are named appropriately and located in the correct directories to be picked up by the include patterns.
- The REST tests should be in directories containing "rest" in the path and end with "Test.java"
- The UI tests should be in directories containing "selenium" in the path and end with "Test.java"
- You may need to adjust your project structure or test naming conventions if tests are not being picked up as expected.

For more detailed information about the Maven profiles and plugin configurations, refer to the `pom.xml` file in the project root.