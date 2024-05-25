package com.omshinde.capstone;

import com.omshinde.capstone.models.User;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.accounts.LoginPage;
import com.omshinde.capstone.pages.accounts.ProfilePage;
import com.omshinde.capstone.pages.accounts.RegistrationPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for user registration functionality.
 */
@Epic("User Management")
@Feature("Registration")
public class RegistrationTest extends BaseTest{

    /**
     * Verifies that a new user is able to register successfully.
     */
    @Test(testName = "VerifyUserRegistrationSuccess", description = "Verify that a new user is able to register on the website by creating an account and accessing their profile page.", groups = "smoke")
    @Story("User Registration Success")
    public void VerifyUserRegistrationSuccess(){
        // Arrange
        User user= User.builder().build().init();
        HomePage homePage=new HomePage(getWebDriver());
        LoginPage loginPage = homePage.getHeader().navToLoginPage();

        // Act
        loginPage.navToRegisterationPage().createAccount(user);
        ProfilePage profilePage = homePage.getHeader().navToProfilePage();

        // Assert
        String accountDetails= profilePage.getAccountDetails();
        Assert.assertTrue(accountDetails.contains(user.getFirst_name()));
    }

    /**
     * Verifies that user registration fails when email field is empty.
     */
    @Test(testName = "VerifyRegistrationWithEmailRequired", description = "Verifies that the user is unable to register without providing an email address by attempting to create an account with an empty email field.", groups = "smoke")
    @Story("User Registration with Email Required")
    public void verifyThatUserIsNotAbleToRegisterWithEmptyEmail(){
        // Arrange
        User user= User.builder().build().userWithoutEmail();
        HomePage homePage=new HomePage(getWebDriver());
        LoginPage loginPage = homePage.getHeader().navToLoginPage();
        RegistrationPage registrationPage=new RegistrationPage(getWebDriver());

        // Act
        loginPage.navToRegisterationPage().createAccount(user);

        // Assert
        String errorMessage = registrationPage.errorMessage();
        Assert.assertTrue(errorMessage.contains("Email can't be blank"));

    }

    /**
     * Verifies that user registration fails when password field is empty.
     */
    @Test(testName = "RegistrationFailsWithEmptyPassword", description = "Validates that the user is prevented from registering without providing a password by attempting to create an account with an empty password field.")
    @Story("User Registration Fails with Empty Password")
    public void verifyThatUserIsNotAbleToRegisterWithEmptyPassword(){
        // Arrange
        User user= User.builder().build().userWithoutPassword();
        HomePage homePage=new HomePage(getWebDriver());
        LoginPage loginPage = homePage.getHeader().navToLoginPage();
        RegistrationPage registrationPage=new RegistrationPage(getWebDriver());

        // Act
        loginPage.navToRegisterationPage().createAccount(user);

        // Assert
        String errorMessage = registrationPage.errorMessage();
        Assert.assertTrue(errorMessage.contains("Password can't be blank"));
    }
}
