package com.omshinde.capstone.pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchResultPage extends BasePage{
    @FindBy(xpath = "//*[@id=\"shopify-section-template--15328405553373__main\"]/div/div[1]/h1")
    private WebElement searchResultHeading;

    @FindBy(xpath = "//*[@id=\"product-grid\"]/ul")
    private WebElement allProducts;

    @Getter
    @FindBy(xpath = "//*[@id=\"ProductCountDesktop\"][1]")
    private WebElement resultsCount;

    List<WebElement> productsHeadingList = allProducts.findElements(By.xpath("//*[@id=\"product-grid\"]/ul/li/div/div[1]/div/h3"));


    @FindBy(xpath = "//*[@id=\"product-grid\"]/ul/li/div/div[1]/div/h3")
    private WebElement productName;


    public String getSearchResultHeading() {
        return webActions.getText(searchResultHeading);
    }

    public ProductDetailsPage clickToViewProductByName() {
        buttonActions.click(productName);
        return new ProductDetailsPage(webDriver);
    }

    public ProductDetailsPage clickToViewProductByIndex(int index){
        productsHeadingList.get(index).click();
        return new ProductDetailsPage(webDriver);
    }

    public String getProductName(){
        return webActions.getText(productName);
    }

    public SearchResultPage(WebDriver webDriver) {
        super(webDriver);
    }

}
