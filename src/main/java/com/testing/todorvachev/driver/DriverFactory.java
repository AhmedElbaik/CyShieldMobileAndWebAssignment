package com.testing.todorvachev.driver;

import com.testing.todorvachev.config.TestSettings;
import com.testing.todorvachev.enums.TestConstants;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class DriverFactory {
    private static final ThreadLocal<String> downloadFolderPath = new ThreadLocal<>();

    public static WebDriver initializeDriver(TestSettings testSettings) {
        WebDriver driver;

        if (testSettings.getTestRunType() == TestConstants.TestRunType.LOCAL) {
            driver = getWebDriver(testSettings);
        } else {
            driver = getRemoteWebDriver(testSettings);
        }

        driver.manage().window().maximize();
        driver.navigate().to(testSettings.getApplicationUrl());
        return driver;
    }

    private static WebDriver getWebDriver(TestSettings testSettings) {
        return switch (testSettings.getBrowserType()) {
            case CHROME -> new ChromeDriver(getChromeOptions(testSettings));
            case FIREFOX -> new FirefoxDriver(getFirefoxOptions(testSettings));
            case EDGE -> new EdgeDriver(getEdgeOptions(testSettings));
            default -> throw new UnsupportedOperationException("The Browser is Not Supported");
        };
    }

    private static WebDriver getRemoteWebDriver(TestSettings testSettings) {
        try {
            URL gridUrl = new URI(testSettings.getGridUri()).toURL();
            return switch (testSettings.getBrowserType()) {
                case CHROME -> new RemoteWebDriver(gridUrl, getChromeOptions(testSettings));
                case FIREFOX -> new RemoteWebDriver(gridUrl, getFirefoxOptions(testSettings));
                default -> new RemoteWebDriver(gridUrl, getEdgeOptions(testSettings));
            };
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid grid URL", e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static ChromeOptions getChromeOptions(TestSettings testSettings) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(testSettings.getBrowserMode());
        options.addArguments("--disable-extensions", "--disable-popup-blocking", "--disable-notifications");
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setCapability("se:recordVideo", true);

        return options;
    }

    private static FirefoxOptions getFirefoxOptions(TestSettings testSettings) {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-private");
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf,application/octet-stream");
        options.addPreference("browser.download.manager.showWhenStarting", false);
        options.addPreference("dom.webnotifications.enabled", false);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setCapability("se:recordVideo", true);

        return options;
    }

    private static EdgeOptions getEdgeOptions(TestSettings testSettings) {
        EdgeOptions options = new EdgeOptions();
        options.addArguments(testSettings.getBrowserMode());
        options.addArguments("--disable-extensions", "--disable-popup-blocking", "--disable-notifications");
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setCapability("se:recordVideo", true);

        return options;
    }

}
