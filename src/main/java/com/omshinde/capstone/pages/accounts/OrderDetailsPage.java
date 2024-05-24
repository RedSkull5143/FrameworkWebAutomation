package com.omshinde.capstone.pages.accounts;

import com.omshinde.capstone.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderDetailsPage extends BasePage {

    @FindBy(xpath = "//*[@id=\"MainContent\"]/div/div[2]/div[1]/table/tbody/tr/td[1]")
    private WebElement productName;

    @FindBy(xpath = "//*[@id=\"MainContent\"]/div/div[2]/div[1]/p/time")
    private WebElement dateAndTime;

    @FindBy(xpath = "//*[@id=\"MainContent\"]/div/div[2]/div[1]/table/tbody/tr/td[4]")
    private WebElement quantity;

    public OrderDetailsPage(WebDriver driver) {
        super(driver);
    }
    public String getProductName(){
        return webActions.getText(productName);
    }

    public String getDateAndTime(){
        return webActions.getText(dateAndTime);
    }

    public int getQuantity(){
        return Integer.parseInt(webActions.getText(quantity));
    }


}