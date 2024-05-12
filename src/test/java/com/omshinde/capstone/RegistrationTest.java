package com.omshinde.capstone;

import com.omshinde.capstone.models.User;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.accounts.LoginPage;
import com.omshinde.capstone.pages.accounts.ProfilePage;
import com.omshinde.capstone.pages.accounts.RegistrationPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationTest extends BaseTest{
    @Test(testName = "VerifyUserRegistrationSuccess",description = "Verify that a new user is able to register on the website by creating an account and accessing their profile page.")
    public void VerifyUserRegistrationSuccess(){
        //arrange
        User user= User.builder().build().init();
        HomePage homePage=new HomePage(getWebDriver());
        LoginPage loginPage = homePage.getHeader().navToLoginPage();

        //act
        loginPage.navToRegisterationPage().createAccount(user);
        ProfilePage profilePage = homePage.getHeader().navToProfilePage();

        //assert
        String accountDetails= profilePage.getAccountDetails();
        Assert.assertTrue(accountDetails.contains(user.getFirst_name()));
    }

    @Test(testName = "VerifyRegistrationWithEmailRequired", description = "Verifies that the user is unable to register without providing an email address by attempting to create an account with an empty email field.")
    public void verifyThatUserIsNotAbleToRegisterWithEmptyEmail(){
        //arrange
        User user= User.builder().build().userWithoutEmail();
        HomePage homePage=new HomePage(getWebDriver());
        LoginPage loginPage = homePage.getHeader().navToLoginPage();
        RegistrationPage registrationPage=new RegistrationPage(getWebDriver());

        //act
        loginPage.navToRegisterationPage().createAccount(user);

        //assert
        String errorMessage = registrationPage.errorMessage();
        Assert.assertTrue(errorMessage.contains("Email can't be blank"));

    }

    @Test(testName = "RegistrationFailsWithEmptyPassword", description = "Validates that the user is prevented from registering without providing a password by attempting to create an account with an empty password field.")
    public void verifyThatUserIsNotAbleToRegisterWithEmptyPassword(){
        //arrange
        User user= User.builder().build().userWithoutPassword();
        HomePage homePage=new HomePage(getWebDriver());
        LoginPage loginPage = homePage.getHeader().navToLoginPage();
        RegistrationPage registrationPage=new RegistrationPage(getWebDriver());

        //act
        loginPage.navToRegisterationPage().createAccount(user);

        //assert
        String errorMessage = registrationPage.errorMessage();
        Assert.assertTrue(errorMessage.contains("Password can't be blank"));
    }
}
