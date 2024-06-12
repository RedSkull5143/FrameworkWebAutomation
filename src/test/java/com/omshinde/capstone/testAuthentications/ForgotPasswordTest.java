package com.omshinde.capstone.testAuthentications;

import com.omshinde.capstone.util.BaseTest;
import com.omshinde.capstone.models.User;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.accounts.ForgotPasswordPage;
import com.omshinde.capstone.pages.accounts.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Password Management")
@Feature("Forgot Password")
public class ForgotPasswordTest extends BaseTest {

    // Logger for general test logging
    private static final Logger logger = LogManager.getLogger(ForgotPasswordTest.class);
    // Logger specifically for navigation-related actions
    private static final Logger navigationLogger = LogManager.getLogger("NavigationLogger");
    // Logger specifically for user-related actions
    private static final Logger userLogger = LogManager.getLogger("UserLogger");

    // Test to validate that the user can be redirected to the "Forgot Password" page
    @Test(testName = "testRedirectUserToForgotPasswordPage",
            description = "Validates that the user can be redirected to the \"Forgot Password\" page from the login page, and confirms the presence of the \"Reset your password\" heading.")
    @Story("User initiates password reset")
    public void testRedirectUserToForgotPasswordPage() {
        logger.info("Executing testRedirectUserToForgotPasswordPage: Validating user redirection to the Forgot Password page");

        try {
            // Initialize pages
            HomePage homePage = new HomePage(getWebDriver());

            // Navigate to the "Forgot Password" page
            navigationLogger.info("Navigating to Login Page");
            LoginPage loginPage = homePage.getHeader().navigateToLoginPage();
            navigationLogger.info("Navigating to Forgot Password Page");
            ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordPage();

            // Verify the presence of the "Reset your password" heading
            String resetPasswordPageHeading = forgotPasswordPage.getResetPasswordPageHeading();
            Assert.assertTrue(resetPasswordPageHeading.contains("Reset your password"));
            logger.info("Reset Password page heading verified successfully");
        } catch (Exception e) {
            logger.error("Error occurred during test execution", e);
            throw e; // Rethrow the exception to mark the test as failed
        }
    }

    // Test to verify the successful submission of a password reset request
    @Test(testName = "testForgotPasswordSuccessful",
            description = "Verifies the successful submission of a password reset request by entering the user's email address. Confirms the display of the success message indicating that an email with a password reset link has been sent.")
    @Story("User successfully resets password")
    public void testForgotPasswordSuccessful() {
        logger.info("Executing testForgotPasswordSuccessful: Verifying successful submission of password reset request");

        try {
            // Initialize user data
            User user = User.builder().build().init();
            userLogger.info("User initialized with email: {}", user.getEmailID());

            // Initialize pages
            HomePage homePage = new HomePage(getWebDriver());

            // Submit a password reset request
            navigationLogger.info("Navigating to Login Page");
            LoginPage loginPage = homePage.getHeader().navigateToLoginPage();
            navigationLogger.info("Navigating to Forgot Password Page");
            ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPasswordPage();
            navigationLogger.info("Submitting password reset request");
            LoginPage afterForgotPasswordPage = forgotPasswordPage.forgotPassword(user);

            // Verify the display of the success message
            String message = afterForgotPasswordPage.forgotPasswordMessage();
            Assert.assertTrue(message.contains("We've sent you an email with a link to update your password."));
            logger.info("Success message verified: {}", message);
        } catch (Exception e) {
            logger.error("Error occurred during test execution", e);
            throw e; // Rethrow the exception to mark the test as failed
        }
    }
}
