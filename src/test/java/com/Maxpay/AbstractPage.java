package com.Maxpay;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public abstract class AbstractPage {
    private static final String loaderLocator = "//div[@id='page-loader']";
    @FindBy(xpath = loaderLocator)
    WebElement loader;


    protected WebDriver driver;
    private NgWebDriver ngWebDriver;
    WebDriverWait explWait;
    JavascriptExecutor jseDriver;

    public AbstractPage (WebDriver driver) {
        this.driver = driver;
        explWait = new WebDriverWait(driver, 10);
        this.jseDriver = (JavascriptExecutor) driver;
        ngWebDriver = new NgWebDriver(jseDriver);
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this);
    }

    public void waitForLoading() {
            try {   // try - на случай, если js в каком то кейсе не начал выполнятся, попытка перехода на другую страницу не произвелась
                new WebDriverWait(driver, 1).until(visibilityOf(loader));
            } catch (TimeoutException e) {
                return;
            }
            explWait.until(attributeToBe(loader, "style", "display: none;"));
        }

    public AbstractPage waitForAngularRequestsToFinish() {
        ngWebDriver.waitForAngularRequestsToFinish();
        return this;
    }

}
