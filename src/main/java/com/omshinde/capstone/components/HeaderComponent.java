package com.omshinde.capstone.components;

import com.omshinde.capstone.modals.SearchModal;
import com.omshinde.capstone.pages.BasePage;
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

    public HeaderComponent(WebDriver webDriver) {
        super(webDriver);
    }
}
