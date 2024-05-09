package com.omshinde.capstone.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TextBox extends WebActions{
    public TextBox(WebDriver webDriver) {
        super(webDriver);
    }

    public void type(WebElement element,String input){
        webDriverWait.until(ExpectedConditions.visibilityOf(element)).sendKeys(input);
    }
    public void type(WebElement element, int input1) {
        String input = String.valueOf(Math.max(input1, 0));
        webDriverWait.until(ExpectedConditions.visibilityOf(element)).sendKeys(input);
    }
}
