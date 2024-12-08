# CyShieldAssignment

This project is a Java-based test automation framework for testing the "https://testing.todorvachev.com/register-form/" website. The project uses a combination of Selenium WebDriver and Appium to handle both desktop browser and mobile browser testing.

## Project Structure

The project structure can be seen in the attached image. The main components are:

- `src/main/java/com/testing/todorvachev/`: This package contains the core classes of the framework, such as the `BaseTest` class, the `DriverFactory` and `AppiumDriverFactory` classes, and various other utility classes.
- `src/test/java/com/testing/todorvachev/tests/`: This package contains the actual test classes, such as `BaseTest`.
- `resources/`: This folder contains the configuration files, such as `appSettings.json`, which holds the settings for the test run.
- `pom.xml`: The Maven Project Object Model (POM) file, which manages the project's dependencies and build process.

## Dependencies

The project's dependencies are managed using Maven. The key dependencies include:

- **Selenium WebDriver**: For browser automation on desktop platforms.
- **Appium**: For mobile browser automation on Android devices.
- **TestNG**: The testing framework used for writing and running the tests.
- **Allure**: For generating rich test reports.
- **Java Faker**: For generating test data.
- **Apache Commons IO**: For file management utilities.

## Test Run Types

The project supports two different test run types, controlled by the `testRunType` setting in the `appSettings.json` file:

1. **LOCAL**: In this mode, the tests will run using Selenium WebDriver on the local desktop browser.
2. **ANDROID**: In this mode, the tests will run using Appium on an emulated Android device.

When running in the **ANDROID** mode, you'll need to have the following prerequisites:

1. **Appium Server**: The Appium server must be running and accessible for the tests to connect to it.
2. **Android Studio**: You'll need to have Android Studio installed and an Android emulator running to execute the Appium-based tests.

The `AppiumDriverFactory` class is responsible for initializing the Appium driver, while the `DriverFactory` class is responsible for initializing the Selenium WebDriver.

## Running the Tests

To run the tests, you can use the following Maven command:

```
mvn clean test
```

This will execute all the tests in the project. The test reports will be generated in the `target/allure-results` directory, which can be viewed using the Allure Report tool.