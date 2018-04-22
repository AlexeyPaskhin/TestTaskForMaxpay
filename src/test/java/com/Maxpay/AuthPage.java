package com.Maxpay;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthPage extends AbstractPage {

    private static final String emailInputLocator = "//input[@id='login-email']";
    private static final String passInputLocator = "//input[@id='login-password']";
    private static final String submitButtonLocator = "//button[text()='Войти']";

    @FindBy(xpath = emailInputLocator) WebElement emailInput;
    @FindBy(xpath = passInputLocator) WebElement passInput;
    @FindBy(xpath = submitButtonLocator) WebElement submitButton;


    public AuthPage(WebDriver driver) {
        super(driver);
        if (!driver.getCurrentUrl().equalsIgnoreCase("https://my-sandbox.maxpay.com/#/signin")) {
            driver.navigate().to("https://my-sandbox.maxpay.com/#/signin");
        }
    }

    AuthPage setToInput(WebElement input, String text) {
        input.clear();
        input.sendKeys(text);
        return this;
    }


    public DashboardPage submitRightAuthForm() {
        submitButton.click();
        return new DashboardPage(driver);
    }

    public void submitInvalidAuthForm() {
        submitButton.click();
    }
}
