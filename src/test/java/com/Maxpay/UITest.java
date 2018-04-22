package com.Maxpay;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class UITest {
    private WebDriver driver;
    private WebDriver.Options options;
    private AuthPage authPage;

    @BeforeClass
    public void setupClass() {

        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        options = driver.manage();
        options.timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test(dataProvider = "RightCredentials", dataProviderClass = DataProviders.class)
    public void positiveTest(String login, String pass) {
        authPage = new AuthPage(driver);
        authPage.setToInput(authPage.emailInput, login)
                .setToInput(authPage.passInput, pass)
                .submitRightAuthForm();
        authPage.waitForLoading();
        assertTrue(driver.getCurrentUrl().equalsIgnoreCase("https://my-sandbox.maxpay.com/app.php#/app/dashboard"),
                "Dashboard page isn't loaded after submitting auth form with right credentials!");
    }

    @Test(dataProvider = "InvalidCredentials", dataProviderClass = DataProviders.class)
    public void negativeTest(String login, String pass) {
        authPage = new AuthPage(driver);
        authPage.setToInput(authPage.emailInput, login)
                .setToInput(authPage.passInput, pass)
                .submitInvalidAuthForm();
        authPage.waitForLoading();
        assertFalse(driver.getCurrentUrl().contains("/app/dashboard"),
                "Dashboard page is loaded after submitting auth form with invalid credentials!");
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
