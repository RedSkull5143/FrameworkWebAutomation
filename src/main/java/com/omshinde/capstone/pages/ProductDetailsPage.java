package com.omshinde.capstone.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductDetailsPage extends BasePage{

    @FindBy(className = "product__title")
    private WebElement productName;

    public String getProductName(){
        return webActions.getText(productName);
    }
    public ProductDetailsPage(WebDriver webDriver) {
        super(webDriver);
    }
}
