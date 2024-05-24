package com.omshinde.capstone.pages.accounts;

import com.omshinde.capstone.pages.BasePage;
import com.omshinde.capstone.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProfilePage extends BasePage {
    @FindBy(xpath = "//*[@id=\"MainContent\"]/div/div[2]/div[2]/p")
    private WebElement accountDetailsEle;

    @FindBy(xpath = "//*[@id=\"MainContent\"]/div/div[1]/a")
    private WebElement logOutBtn;

    @FindBy(xpath = "//*[@id=\"RowOrder\"]/a")
    private WebElement orderNumber;

    @FindBy(xpath = "//*[@id=\"MainContent\"]/div/div[2]/div[1]/table/tbody/tr[1]/td[3]")
    private WebElement paymentStatus;

    @FindBy(xpath = "//*[@id=\"MainContent\"]/div/div[2]/div[1]/table/tbody/tr[1]/td[5]")
    private WebElement total;

    @FindBy(xpath = "//*[@id=\"MainContent\"]/div/div[2]/div[1]/table/tbody/tr[1]/td[2]/time")
    private WebElement date;

    public OrderDetailsPage navToOrderDetails(){
        buttonActions.click(orderNumber);
        return new OrderDetailsPage(webDriver);
    }

    public String getPaymentStatus(){
        return buttonActions.getText(paymentStatus);
    }

    public String getDate(){
        return buttonActions.getText(date);
    }

    public String getTotal(){
        return buttonActions.getText(total);
    }

    public ProfilePage(WebDriver webDriver) {
        super(webDriver);
    }

    public String getAccountDetails() {
        return webActions.getText(accountDetailsEle);
    }


    public HomePage logOut(){
        buttonActions.click(logOutBtn);
        return new HomePage(webDriver);
    }

}
