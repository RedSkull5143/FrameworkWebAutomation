package com.omshinde.capstone.actions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ButtonAction extends WebActions {
    public ButtonAction(WebDriver webDriver) {
        super(webDriver);
    }

    public void click(WebElement webElement){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", webElement);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement)).click();
    }
}
