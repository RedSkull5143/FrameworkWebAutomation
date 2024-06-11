package com.omshinde.capstone.testAuthentications;

import com.omshinde.capstone.util.BaseTest;
import com.omshinde.capstone.models.User;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.accounts.ForgotPasswordPage;
import com.omshinde.capstone.pages.accounts.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

// Annotations for Epic, Feature, and Story
@Epic("Password Management")
@Feature("Forgot Password")
public class ForgotPasswordTest extends BaseTest {

    // Test to validate that the user can be redirected to the "Forgot Password" page
    @Test(testName = "testRedirectUserToForgotPasswordPage",
            description = "Validates that the user can be redirected to the \"Forgot Password\" page from the login page, and confirms the presence of the \"Reset your password\" heading.")
    @Story("User initiates password reset")
    public void testRedirectUserToForgotPasswordPage() {
        // Initialize pages
        HomePage homePage = new HomePage(getWebDriver());

        // Navigate to the "Forgot Password" page
        ForgotPasswordPage forgotPasswordPage = homePage.getHeader().navigateToLoginPage().navigateToForgotPasswordPage();

        // Verify the presence of the "Reset your password" heading
        String resetPasswordPageHeading = forgotPasswordPage.getResetPasswordPageHeading();
        Assert.assertTrue(resetPasswordPageHeading.contains("Reset your password"));
    }

    // Test to verify the successful submission of a password reset request
    @Test(testName = "testForgotPasswordSuccessful",
            description = "Verifies the successful submission of a password reset request by entering the user's email address. Confirms the display of the success message indicating that an email with a password reset link has been sent.")
    @Story("User successfully resets password")
    public void testForgotPasswordSuccessful() {
        // Initialize user data
        User user = User.builder().build().init();

        // Initialize pages
        HomePage homePage = new HomePage(getWebDriver());

        // Submit a password reset request
        LoginPage loginPage = homePage.getHeader().navigateToLoginPage().navigateToForgotPasswordPage().forgotPassword(user);

        // Verify the display of the success message
        String message = loginPage.forgotPasswordMessage();
        Assert.assertTrue(message.contains("We've sent you an email with a link to update your password."));
    }

}
