package com.omshinde.capstone.components;

import com.omshinde.capstone.modals.SearchModal;
import com.omshinde.capstone.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderComponent extends BasePage {

    @FindBy(xpath = "//*[@id=\"shopify-section-header\"]/sticky-header/header/div/details-modal/details/summary/span")
    private WebElement searchIconEle;

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


    public HeaderComponent(WebDriver webDriver) {
        super(webDriver);
    }
}
