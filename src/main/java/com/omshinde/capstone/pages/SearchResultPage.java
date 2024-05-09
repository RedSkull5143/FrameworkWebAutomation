package com.omshinde.capstone.pages;

import lombok.Data;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchResultPage extends BasePage{
    @FindBy(xpath = "//*[@id=\"shopify-section-template--15328405553373__main\"]/div/div[1]/h1")
    private WebElement searchResultHeading;

    @FindBy(xpath = "//*[@id=\"product-grid\"]/ul/li/div/div[1]/div/h3")
    private WebElement productName;

    public String getSearchResultHeading() {
        return webActions.getText(searchResultHeading);
    }

    public ProductDetailsPage clickToViewProduct() {
        buttonActions.click(productName);
        return new ProductDetailsPage(webDriver);
    }

    public String getProductName(){
        return webActions.getText(productName);
    }
    public SearchResultPage(WebDriver webDriver) {
        super(webDriver);
    }

}
