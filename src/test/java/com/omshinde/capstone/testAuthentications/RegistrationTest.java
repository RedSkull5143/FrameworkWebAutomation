package com.omshinde.capstone.testAuthentications;

import com.omshinde.capstone.util.BaseTest;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Test class for user registration functionality.
 */
@Epic("User Management")
@Feature("Registration")
public class RegistrationTest extends BaseTest {

    // Logger for logging in this class
    private static final Logger logger = LogManager.getLogger(RegistrationTest.class);

    /**
     * Verifies that a new user is able to register successfully.
     */
    @Test(testName = "VerifyUserRegistrationSuccess", description = "Verify that a new user is able to register on the website by creating an account and accessing their profile page.", groups = "smoke")
    @Story("User Registration Success")
    public void VerifyUserRegistrationSuccess() {
        try {
            // Arrange
            User user = User.builder().build().init();
            logger.info("Initializing user for registration: {}", user);
            HomePage homePage = new HomePage(getWebDriver());
            LoginPage loginPage = homePage.getHeader().navigateToLoginPage();
            logger.info("Navigated to Login page");

            // Act
            RegistrationPage registrationPage = loginPage.navigateToRegistrationPage();
            logger.info("Navigated to Registration page");
            registrationPage.createAccount(user);
            logger.info("Account created successfully");

            ProfilePage profilePage = homePage.getHeader().navigateToProfilePage();
            logger.info("Navigated to Profile page");

            // Assert
            String accountDetails = profilePage.getAccountDetails();
            Assert.assertTrue(accountDetails.contains(user.getFirst_name()));
            logger.info("Account details verified: {}", accountDetails);
        } catch (Exception e) {
            logger.error("Error occurred during test execution: VerifyUserRegistrationSuccess", e);
            throw e; // Rethrow the exception to mark the test as failed
        }
    }

    /**
     * Verifies that user registration fails when email field is empty.
     */
    @Test(testName = "VerifyRegistrationWithEmailRequired", description = "Verifies that the user is unable to register without providing an email address by attempting to create an account with an empty email field.", groups = "smoke")
    @Story("User Registration with Email Required")
    public void verifyThatUserIsNotAbleToRegisterWithEmptyEmail() {
        try {
            // Arrange
            User user = User.builder().build().userWithoutEmail();
            logger.info("Initializing user without email: {}", user);
            HomePage homePage = new HomePage(getWebDriver());
            LoginPage loginPage = homePage.getHeader().navigateToLoginPage();
            logger.info("Navigated to Login page");

            // Act
            RegistrationPage registrationPage = loginPage.navigateToRegistrationPage();
            logger.info("Navigated to Registration page");
            registrationPage.createAccount(user);
            logger.info("Account creation attempted");

            // Assert
            String errorMessage = registrationPage.errorMessage();
            Assert.assertTrue(errorMessage.contains("Email can't be blank"));
            logger.info("Error message verified: {}", errorMessage);
        } catch (Exception e) {
            logger.error("Error occurred during test execution: verifyThatUserIsNotAbleToRegisterWithEmptyEmail", e);
            throw e; // Rethrow the exception to mark the test as failed
        }
    }

    /**
     * Verifies that user registration fails when password field is empty.
     */
    @Test(testName = "RegistrationFailsWithEmptyPassword", description = "Validates that the user is prevented from registering without providing a password by attempting to create an account with an empty password field.")
    @Story("User Registration Fails with Empty Password")
    public void verifyThatUserIsNotAbleToRegisterWithEmptyPassword() {
        try {
            // Arrange
            User user = User.builder().build().userWithoutPassword();
            logger.info("Initializing user without password: {}", user);
            HomePage homePage = new HomePage(getWebDriver());
            LoginPage loginPage = homePage.getHeader().navigateToLoginPage();
            logger.info("Navigated to Login page");

            // Act
            RegistrationPage registrationPage = loginPage.navigateToRegistrationPage();
            logger.info("Navigated to Registration page");
            registrationPage.createAccount(user);
            logger.info("Account creation attempted");

            // Assert
            String errorMessage = registrationPage.errorMessage();
            Assert.assertTrue(errorMessage.contains("Password can't be blank"));
            logger.info("Error message verified: {}", errorMessage);
        } catch (Exception e) {
            logger.error("Error occurred during test execution: verifyThatUserIsNotAbleToRegisterWithEmptyPassword", e);
            throw e; // Rethrow the exception to mark the test as failed
        }
    }
}
