package com.omshinde.capstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductDetailsPage extends BasePage{

    @FindBy(className = "product__title")
    private WebElement productName;

    @FindBy(xpath = "//*[@id=\"product-form-template--15328405717213__main\"]/div/button")
    private WebElement addToCartBtn;

    @FindBy(xpath = "/html[1]/body[1]/main[1]/section[1]/section[1]/div[1]/div[2]/div[1]/div[3]/quantity-input[1]/input[1]")
    private WebElement quantitySelected;

    @FindBy(xpath = "//*[@id=\"price-template--15328405717213__main\"]/div/div/div[1]/span[2]")
    private WebElement productPrice;

    public String getProductName(){
        return webActions.getText(productName);
    }

    public double getProductPrice(){
        String getPrice= webActions.getText(productPrice);
        String numberString = getPrice.replaceAll("[^0-9.]", "");
        return Double.parseDouble(numberString);
    }

    public int getQuantitySelected(){
        return Integer.parseInt(webActions.getText(quantitySelected).trim());
    }
    public String getSizeSelected() {
        WebElement selectedRadio = null;
        List<WebElement> sizeRadioButtons = webDriver.findElements(By.name("Size"));

        for (WebElement i : sizeRadioButtons) {
            if (i.isSelected()) {
                selectedRadio = i;
                return i.getAttribute("value");
            }
        }
        return null;
    }

    public boolean isProductSoldOut(){
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
        try {
            WebElement isSoldOut = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"price-template--15328405717213__main\"]/div/span[2]")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public CartPage clickAddToCart(){
        buttonActions.click(addToCartBtn);
        return new CartPage(webDriver);
    }

    public ProductDetailsPage(WebDriver webDriver) {
        super(webDriver);
    }
}
