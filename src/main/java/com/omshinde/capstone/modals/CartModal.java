package com.omshinde.capstone.modals;

import com.omshinde.capstone.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartModal extends BasePage {

    WebDriverWait webDriverWait=new WebDriverWait(webDriver, Duration.ofSeconds(10));

    @FindBy(xpath = "//*[@id=\"cart-notification\"]/div[1]/h2")
    private WebElement addToCartSuccessMessage;

    public String getSuccessMessage(){
        webDriverWait.until(ExpectedConditions.visibilityOf(addToCartSuccessMessage));
        return webActions.getText(addToCartSuccessMessage);
    }
    public CartModal(WebDriver webDriver) {
        super(webDriver);
    }
}
