package com.omshinde.capstone.components;

import com.omshinde.capstone.modals.SearchModal;
import com.omshinde.capstone.pages.BasePage;
import com.omshinde.capstone.pages.CartPage;
import com.omshinde.capstone.pages.accounts.LoginPage;
import com.omshinde.capstone.pages.accounts.ProfilePage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderComponent extends BasePage {

    @FindBy(xpath = "//*[@id=\"shopify-section-header\"]/sticky-header/header/div/details-modal/details/summary/span")
    private WebElement searchIconEle;

    @FindBy(xpath = "//*[@id=\"shopify-section-header\"]/sticky-header/header/div/a[1]")
    private WebElement profileBtnEle;

    @FindBy(id = "cart-icon-bubble")
    private WebElement cartBtnEle;


    public SearchModal clickSearchBtn(){
        buttonActions.click(searchIconEle);
        return new SearchModal(webDriver);
    }

    public int getCartCount() {
        int count = 0;
        try {
            WebElement countElement = webDriver.findElement(By.xpath("//*[@id=\"cart-icon-bubble\"]/div/span[1]"));
            if (countElement.isDisplayed()) {
                String initialCount = countElement.getText();
                count = Integer.parseInt(initialCount);
            }
        } catch (NoSuchElementException e) {
            // Cart count element not found, return default count (0)
        }
        return count;
    }
    public LoginPage navToLoginPage(){
        buttonActions.click(profileBtnEle);
        return new LoginPage(webDriver);
    }

    public ProfilePage navToProfilePage(){
        buttonActions.click(profileBtnEle);
        return new ProfilePage(webDriver);
    }

    public CartPage navToCartPage(){
        buttonActions.click(cartBtnEle);
        return new CartPage(webDriver);
    }

    public HeaderComponent(WebDriver webDriver) {
        super(webDriver);
    }
}
