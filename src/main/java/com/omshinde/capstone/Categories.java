package com.omshinde.capstone;

import com.omshinde.capstone.modals.FilterByPriceModal;
import com.omshinde.capstone.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Categories extends BasePage {

    @FindBy(xpath = "//*[@id=\"FacetsWrapperDesktop\"]/details[1]/summary/div/span")
    private WebElement availabiltyFilter;

    @FindBy(xpath = "//*[@id=\"FacetsWrapperDesktop\"]/details[2]/summary/div/span")
    private WebElement priceFilter;

    @FindBy(xpath = "//*[@id=\"FacetsWrapperDesktop\"]/details[3]/summary/div/span")
    private WebElement productTypeFilter;

    @FindBy(xpath = "//*[@id=\"FacetsWrapperDesktop\"]/details[4]/summary/div/span")
    private WebElement brandTypeFilter;

    @FindBy(xpath = "//*[@id=\"FacetsWrapperDesktop\"]/details[1]/summary/div/span")
    private WebElement sizeFilter;

    public FilterByPriceModal openFilterModal(){
        buttonActions.click(priceFilter);
        return new FilterByPriceModal(webDriver);
    }

    public Categories(WebDriver webDriver) {
        super(webDriver);
    }
}
