package com.omshinde.capstone;

import com.omshinde.capstone.models.User;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.accounts.ForgotPasswordPage;
import com.omshinde.capstone.pages.accounts.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ForgotPasswordTest extends BaseTest{

    @Test(testName = "RedirectUserToForgotPasswordPage",description = "Validates that the user can be redirected to the \"Forgot Password\" page from the login page, and confirms the presence of the \"Reset your password\" heading.")
    public void RedirectUserToForgotPasswordPage(){
        HomePage homePage=new HomePage(getWebDriver());
        ForgotPasswordPage forgotPasswordPage = homePage.getHeader().navToLoginPage().navToForgotPasswordPage();
        String resetPasswordPageHeading = forgotPasswordPage.getResetPasswordPageHeading();
        Assert.assertTrue(resetPasswordPageHeading.contains("Reset your password"));
    }

    @Test(testName = "ForgotPasswordSuccessful",description = "Verifies the successful submission of a password reset request by entering the user's email address. Confirms the display of the success message indicating that an email with a password reset link has been sent.")
    public void forgotPasswordSuccessful(){
        User user= User.builder().build().init();
        HomePage homePage=new HomePage(getWebDriver());
        LoginPage loginPage = homePage.getHeader().navToLoginPage().navToForgotPasswordPage().forgotPassword(user);
        String message = loginPage.forgotPasswordMessage();
        Assert.assertTrue(message.contains("We've sent you an email with a link to update your password."));
    }
}
