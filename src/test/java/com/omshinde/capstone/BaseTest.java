package com.omshinde.capstone;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;

public class BaseTest {
    private static final String URL="https://web-playground.ultralesson.com/";

    @BeforeMethod(alwaysRun = true)
    public synchronized void setUp() {
        WebDriver driver = DriverFactory.getInstance().getDriver();
        driver.manage().window().maximize();
        launch(driver);
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            captureScreenshot(result.getMethod().getMethodName());
        }
        DriverFactory.getInstance().quitDriver();
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] captureScreenshot(String screenshotName) {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    protected synchronized void launch(WebDriver driver) {
        driver.get(URL);
    }

    protected synchronized WebDriver getWebDriver() {
        return DriverFactory.getInstance().getDriver();
    }
}
