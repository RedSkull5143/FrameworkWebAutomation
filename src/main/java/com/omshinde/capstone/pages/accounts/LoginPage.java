package com.omshinde.capstone.pages.accounts;

import com.omshinde.capstone.models.User;
import com.omshinde.capstone.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "CustomerEmail")
    private WebElement emailIdBox;

    @FindBy(id = "CustomerPassword")
    private WebElement passwordBox;

    @FindBy(xpath = "//*[@id=\"customer_login\"]/a[1]")
    private WebElement forgotPasswordLinkEle;

    @FindBy(xpath = "//*[@id=\"customer_login\"]/button")
    private WebElement signInBtnEle;

    @FindBy(xpath = "//*[@id=\"customer_login\"]/a[2]")
    private WebElement createAccountLinkEle;
    @FindBy(xpath = "//*[@id=\"customer_login\"]/div[@class='errors']//li")
    private WebElement errorBoxEle;
    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    public ProfilePage login(User user){
        textBox.type(emailIdBox, user.getEmailID());
        textBox.type(passwordBox, user.getPassword());
        buttonActions.click(signInBtnEle);
        return new ProfilePage(webDriver);
    }
    public RegistrationPage navToRegisterationPage(){
        buttonActions.click(createAccountLinkEle);
        return new RegistrationPage(webDriver);
    }

    public String getErrorMessage(){
        return webActions.getText(errorBoxEle);
    }
}
