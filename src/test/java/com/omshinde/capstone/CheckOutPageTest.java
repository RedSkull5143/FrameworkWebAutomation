package com.omshinde.capstone;

import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.modals.CartModal;
import com.omshinde.capstone.models.User;
import com.omshinde.capstone.pages.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

// Annotations for Epic, Feature, and Story
@Epic("Checkout Process")
@Feature("Purchase Products")
public class CheckOutPageTest extends BaseTest{

    // Test verifies that a user can successfully purchase a product and receive a confirmation message
    @Test(testName = "testUserCanPurchaseProduct", description = "Verifies that a user can successfully purchase a product and receive a confirmation message.")
    @Story("User completes the checkout process")
    public void testUserCanPurchaseProduct(){
        // Initialize test data and pages
        SearchContent searchContent = SearchContent.builder().build().init();
        CartPage cartPage = new CartPage(getWebDriver());
        User user = User.builder().build().userWithValidCredentials();
        HomePage homePage = new HomePage(getWebDriver());

        // Log in with valid user credentials
        homePage.getHeader().navToLoginPage().login(user);

        // Search for a product and add it to cart
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        CartModal cartModal = new CartModal(getWebDriver());

        if(!productDetailsPage.isProductSoldOut()){
            cartPage = productDetailsPage.clickAddToCart();
            Assert.assertTrue(cartModal.getSuccessMessage().contains("Item added to your cart"));
        } else {
            Assert.fail("Product Out of Stock");
        }

        cartModal.clickAddToCart();

        // Proceed to checkout and complete the order
        BillingPage billingPage = cartPage.clickCheckOutBtn();
        billingPage.selectPayment();
        billingPage.completeOrder();

        // Verify confirmation message
        String confirmationMessage = billingPage.getConfirmationMessage();
        Assert.assertTrue(confirmationMessage.contains("Your order is confirmed"));
    }

    // Test verifies that the user cannot proceed to checkout if the cart is empty
    @Test(testName = "testUserCannotCheckoutWithEmptyCart", description = "Verifies that the user cannot proceed to checkout if the cart is empty.")
    @Story("User tries to checkout with an empty cart")
    public void testUserCannotCheckoutWithEmptyCart() {
        // Initialize test data and pages
        HomePage homePage = new HomePage(getWebDriver());

        // Navigate to the cart page
        CartPage cartPage = homePage.getHeader().navToCartPage();

        // Verify that the cart is empty and checkout button is absent
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart is empty");
        Assert.assertTrue(cartPage.isCheckoutButtonPresent(), "Checkout button is absent");
    }
}
