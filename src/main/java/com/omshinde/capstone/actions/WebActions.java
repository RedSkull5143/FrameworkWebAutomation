package com.omshinde.capstone.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebActions {
    private WebDriver webDriver;
    protected WebDriverWait webDriverWait;

    public WebActions(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.webDriverWait=new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }
    public String getText(WebElement element){
        return webDriverWait.until(ExpectedConditions.visibilityOf(element)).getText();
    }
}
