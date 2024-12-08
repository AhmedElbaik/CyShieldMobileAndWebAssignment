package com.testing.todorvachev.tests;

import com.testing.todorvachev.models.RegistrationData;
import com.testing.todorvachev.pages.RegisterFormPage;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

@Epic("User Registration")
@Feature("Registration Form Functionality")
public class RegisterFormTest extends BaseTest {

    private RegisterFormPage createRegisterFormPage() {
        return new RegisterFormPage(getDriver());
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Verify Registration Form Elements")
    @Description("Ensure all elements are visible on the registration form")
    @Severity(SeverityLevel.BLOCKER)
    public void testAllElementsAreVisible() {
        RegisterFormPage registerFormPage = createRegisterFormPage();
        boolean areAllElementsVisible = registerFormPage.areAllElementsVisible();
        Assert.assertTrue(areAllElementsVisible, "Not all elements are visible on the registration form page.");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Registration with Invalid Data")
    @Description("Validate the system's response to registration with invalid input")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("REG-001")
    public void testRegistrationWithInvalidData() {
        RegisterFormPage registerFormPage = createRegisterFormPage();
        RegistrationData invalidData = new RegistrationData(false);

        registerFormPage.fillRegistrationForm(
                invalidData.getUserId(),
                invalidData.getUsername(),
                invalidData.getPassword(),
                invalidData.getAddress(),
                invalidData.getCountry(),
                invalidData.getZip(),
                invalidData.getEmail(),
                invalidData.getGender(),
                invalidData.isSpeaksEnglish(),
                invalidData.getAbout()
        );
        registerFormPage.submitForm();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            String errorMessage = registerFormPage.getAlertText();
            Assert.assertTrue(true, "An alert should be present for invalid registration data. Alert message is: " + errorMessage);
        } catch (WebDriverException e) {
            Assert.fail("No alert was present for invalid registration data.");
        }
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Story("Registration with Valid Data")
    @Description("Validate successful registration with valid input")
    @Severity(SeverityLevel.BLOCKER)
    @Issue("REG-002")
    public void testRegistrationWithValidData() {
        RegisterFormPage registerFormPage = createRegisterFormPage();
        RegistrationData validData = new RegistrationData(true);

        registerFormPage.fillRegistrationForm(
                validData.getUserId(),
                validData.getUsername(),
                validData.getPassword(),
                validData.getAddress(),
                validData.getCountry(),
                validData.getZip(),
                validData.getEmail(),
                validData.getGender(),
                validData.isSpeaksEnglish(),
                validData.getAbout()
        );
        registerFormPage.submitForm();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Assert.fail("No alert should be present for valid registration data.");
        } catch (WebDriverException e) {
            // No alert present, test passes
        }
    }
}