package com.omshinde.capstone.pages.accounts;

import com.omshinde.capstone.models.User;
import com.omshinde.capstone.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    public ForgotPasswordPage navigateToForgotPasswordPage(){
        buttonActions.click(forgotPasswordLinkEle);
        return new ForgotPasswordPage(webDriver);
    }
    public RegistrationPage navigateToRegistrationPage(){
        buttonActions.click(createAccountLinkEle);
        return new RegistrationPage(webDriver);
    }

    public String getErrorMessage(){
        return webActions.getText(errorBoxEle);
    }

    public String forgotPasswordMessage(){
        WebDriverWait webDriverWait=new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement message= webDriver.findElement(By.xpath("//*[@id=\"MainContent\"]/div/div[2]/h3"));
        webDriverWait.until(ExpectedConditions.visibilityOf(message));
        return message.getText();
    }
}
