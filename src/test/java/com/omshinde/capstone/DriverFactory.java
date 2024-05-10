package com.omshinde.capstone;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {
        private static DriverFactory instance = null;
        private ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

        private DriverFactory() {
            // Private constructor to prevent direct instantiation.
        }

        public static DriverFactory getInstance() {
            if (instance == null) {
                synchronized (DriverFactory.class) {
                    if (instance == null) {
                        instance = new DriverFactory();
                    }
                }
            }
            return instance;
        }

        public WebDriver getDriver() {
            if (driverThreadLocal.get() == null) {
                // Initialize the WebDriver instance, for example, with ChromeDriver
                WebDriver driver = new ChromeDriver();
                driverThreadLocal.set(driver);
            }
            return driverThreadLocal.get();
        }

        public void quitDriver() {
            if (driverThreadLocal.get() != null) {
                driverThreadLocal.get().quit();
                driverThreadLocal.remove();
            }
        }
    }

