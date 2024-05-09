package com.omshinde.capstone;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    private static ThreadLocal<WebDriver> driverThreadLocal=new ThreadLocal<>();
    private static final String URL="https://web-playground.ultralesson.com/";

    @BeforeMethod(alwaysRun = true)
    public synchronized void setUp(){
        WebDriver driver = new ChromeDriver();
        driverThreadLocal.set(driver);
        launch();
        driverThreadLocal.get().manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void tearDown(){
        driverThreadLocal.get().quit();
    }
    protected synchronized void launch(){
        driverThreadLocal.get().get(URL);
    }
    protected synchronized WebDriver getWebDriver(){
        return driverThreadLocal.get();
    }
}
