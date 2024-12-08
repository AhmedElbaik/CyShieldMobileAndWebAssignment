package com.testing.todorvachev.pages;

import com.testing.todorvachev.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class RegisterFormPage extends BasePage {

    // Locators for the registration form fields
    private final By userIdField = By.xpath("//input[@name='userid']");
    private final By usernameField = By.xpath("//input[@name='username']");
    private final By passwordField = By.xpath("//input[@name='passid']");
    private final By addressField = By.xpath("//input[@name='address']");
    private final By countryDropdownMenu = By.xpath("//select[@name='country']");
    private final By zIPField = By.xpath("//input[@name='zip']");
    private final By emailField = By.xpath("//input[@name='email']");
    private final By maleRadio = By.xpath("//input[@value='Male']");
    private final By femaleRadio = By.xpath("//input[@value='Female']");
    private final By languageCheckbox = By.xpath("//input[@name='languageQuestion']");
    private final By aboutTextArea = By.xpath("//textarea[@name='desc']");
    private final By registerButton = By.xpath("//input[@name='submit']");

    // Constructor
    public RegisterFormPage(WebDriver driver) {
        super(driver);
    }

    @Step("Checking if all elements are visible on the registration form page")
    public boolean areAllElementsVisible() {
        // List of all locators to check
        Map<By, String> elementsToCheck = Map.of(
                userIdField, "User ID Field",
                usernameField, "Username Field",
                passwordField, "Password Field",
                addressField, "Address Field",
                countryDropdownMenu, "Country Dropdown Menu",
                zIPField, "ZIP Field",
                emailField, "Email Field",
                languageCheckbox, "Language Checkbox",
                aboutTextArea, "About Text Area",
                registerButton, "Register Button"
        );

        boolean allVisible = true;
        StringBuilder errorMessage = new StringBuilder("The following elements are not visible:\n");

        for (Map.Entry<By, String> entry : elementsToCheck.entrySet()) {
            By locator = entry.getKey();
            String elementName = entry.getValue();

            // Check element visibility
            if (!WaitUtils.isElementPresent(driver, locator)) {
                allVisible = false;
                errorMessage.append("- ").append(elementName).append("\n");
            }
        }

        // Log error message if any elements are not visible
        if (!allVisible) {
            System.err.println(errorMessage.toString());
        }

        return allVisible;
    }


    @Step("Filling the registration form with the provided data")
    public void fillRegistrationForm(String userId, String username, String password, String address, String country, String zip, String email, String gender, boolean speaksEnglish, String about) {
        // Fill in the registration form fields
        clearAndType(userIdField, userId);
        clearAndType(usernameField, username);
        clearAndType(passwordField, password);
        clearAndType(addressField, address);
        selectCountry(country);
        clearAndType(zIPField, zip);
        clearAndType(emailField, email);
        selectGender(gender);
        setEnglishLanguage(speaksEnglish);
        clearAndType(aboutTextArea, about);
    }

    @Step("Submitting the registration form")
    public void submitForm() {
        // Click the register button to submit the form
        WaitUtils.waitForElementClickable(driver, registerButton).click();
    }

    private void clearAndType(By locator, String text) {
        // Wait for the element to be visible, clear it, and type the provided text
        WebElement element = WaitUtils.waitForElementVisible(driver, locator);
        element.clear();
        element.sendKeys(text);
    }

    private void selectCountry(String country) {
        // Select a country from the dropdown menu
        WebElement countryDropdown = WaitUtils.waitForElementVisible(driver, countryDropdownMenu);
        Select dropdown = new Select(countryDropdown);
        dropdown.selectByVisibleText(country);
    }

    private void selectGender(String gender) {
        // Select the gender radio button based on the input
        if (gender.equalsIgnoreCase("Male")) {
            WaitUtils.waitForElementClickable(driver, maleRadio).click();
        } else {
            WaitUtils.waitForElementClickable(driver, femaleRadio).click();
        }
    }

    private void setEnglishLanguage(boolean speaksEnglish) {
        // Check or uncheck the language checkbox based on input
        WebElement languageCheckboxElement = WaitUtils.waitForElementVisible(driver, languageCheckbox);
        if (speaksEnglish) {
            if (!languageCheckboxElement.isSelected()) {
                languageCheckboxElement.click();
            }
        } else {
            if (languageCheckboxElement.isSelected()) {
                languageCheckboxElement.click(); // Deselect the checkbox
            }
        }
    }

    @Step("Checking if an alert is shown and Getting the text from the alert popup")
    public String getAlertText() {
        // Wait for the alert to be present, get its text, and accept it
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        driver.switchTo().defaultContent();
        return alertText;
    }
}