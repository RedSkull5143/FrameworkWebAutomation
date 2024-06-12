package com.omshinde.capstone.testOrders;

import com.omshinde.capstone.util.BaseTest;
import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.modals.CartModal;
import com.omshinde.capstone.models.User;
import com.omshinde.capstone.pages.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Epic: Checkout Process
 * Feature: Purchase Products
 * Test class for checkout process.
 */
@Epic("Checkout Process")
@Feature("Purchase Products")
public class CheckOutPageTest extends BaseTest {

    // Logger for logging in this class
    private static final Logger logger = LogManager.getLogger(CheckOutPageTest.class);

    /**
     * Test verifies that a user can successfully purchase a product and receive a confirmation message
     */
    @Test(testName = "testUserCanPurchaseProduct", description = "Verifies that a user can successfully purchase a product and receive a confirmation message.")
    @Story("User completes the checkout process")
    public void testUserCanPurchaseProduct() {
        try {
            // Arrange
            SearchContent searchContent = SearchContent.builder().build().init();
            logger.info("Initializing test data and pages");
            CartPage cartPage = new CartPage(getWebDriver());
            User user = User.builder().build().userWithValidCredentials();
            HomePage homePage = new HomePage(getWebDriver());

            // Act
            logger.info("Logging in with valid user credentials");
            homePage.getHeader().navigateToLoginPage().login(user);

            SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());
            ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
            CartModal cartModal = new CartModal(getWebDriver());

            if (!productDetailsPage.isProductSoldOut()) {
                cartPage = productDetailsPage.clickAddToCart();
                Assert.assertTrue(cartModal.getSuccessMessage().contains("Item added to your cart"));
                logger.info("Product added to cart successfully");
            } else {
                Assert.fail("Product Out of Stock");
            }

            cartModal.clickAddToCart();
            logger.info("Clicked on Add to Cart button in modal");

            BillingPage billingPage = cartPage.clickCheckOutBtn();
            billingPage.selectPayment();
            billingPage.completeOrder();

            // Assert
            String confirmationMessage = billingPage.getConfirmationMessage();
            Assert.assertTrue(confirmationMessage.contains("Your order is confirmed"));
            logger.info("Verified confirmation message for successful purchase");
        } catch (Exception e) {
            logger.error("Error occurred during test execution: testUserCanPurchaseProduct", e);
            throw e; // Rethrow the exception to mark the test as failed
        }
    }

    /**
     * Test verifies that the user cannot proceed to checkout if the cart is empty
     */
    @Test(testName = "testUserCannotCheckoutWithEmptyCart", description = "Verifies that the user cannot proceed to checkout if the cart is empty.")
    @Story("User tries to checkout with an empty cart")
    public void testUserCannotCheckoutWithEmptyCart() {
        try {
            // Arrange
            HomePage homePage = new HomePage(getWebDriver());

            // Act
            logger.info("Navigating to the cart page");
            CartPage cartPage = homePage.getHeader().navigateToCartPage();

            // Assert
            Assert.assertTrue(cartPage.isCartEmpty(), "Cart is empty");
            Assert.assertTrue(cartPage.isCheckoutButtonPresent(), "Checkout button is absent");
            logger.info("Verified that the user cannot proceed to checkout with an empty cart");
        } catch (Exception e) {
            logger.error("Error occurred during test execution: testUserCannotCheckoutWithEmptyCart", e);
            throw e; // Rethrow the exception to mark the test as failed
        }
    }
}
