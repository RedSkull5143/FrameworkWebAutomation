package com.omshinde.capstone.testOrders;

import com.omshinde.capstone.util.BaseTest;
import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.modals.CartModal;
import com.omshinde.capstone.models.User;
import com.omshinde.capstone.pages.*;
import com.omshinde.capstone.pages.accounts.OrderDetailsPage;
import com.omshinde.capstone.pages.accounts.ProfilePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Annotations for Epic, Feature, and Story
@Epic("Order Management")
@Feature("Order History and Details")
public class OrderHistoryTest extends BaseTest {

    // Test to validate order history information of a user
    @Test(testName = "testValidateOrderHistoryInformation",
            description = "Validates the order history information of a user")
    @Story("As a user, I want to view my order history to see the details of my previous purchases")
    public void testValidateOrderHistoryInformation() {
        // Initialize pages and user data
        SearchContent searchContent = SearchContent.builder().build().init();
        CartPage cartPage = new CartPage(getWebDriver());
        User user = User.builder().build().userWithValidCredentials();
        HomePage homePage = new HomePage(getWebDriver());

        // Login and navigate to order history
        homePage.getHeader().navigateToLoginPage().login(user);
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        CartModal cartModal = new CartModal(getWebDriver());

        // Add product to cart and complete order
        if (!productDetailsPage.isProductSoldOut()) {
            cartPage = productDetailsPage.clickAddToCart();
            Assert.assertTrue(cartModal.getSuccessMessage().contains("Item added to your cart"));
        } else {
            Assert.fail("Product Out of Stock");
        }

        cartModal.clickAddToCart();
        BillingPage billingPage = cartPage.clickCheckOutBtn();
        billingPage.selectPayment();
        HomePage homePage1 = billingPage.completeOrder();
        ProfilePage profilePage = homePage1.getHeader().navigateToProfilePage();

        // Verify payment status and order date
        Assert.assertTrue(profilePage.getPaymentStatus().contains("Pending"));
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String formattedDate = currentDate.format(formatter);
        Assert.assertTrue(profilePage.getDate().contains(formattedDate), "Order date matches current date");
    }

    // Test to validate order details of a specific order
    @Test(testName = "testValidateOrderDetails",
            description = "Validates the details of a specific order")
    @Story("As a user, I want to view the details of a specific order to verify the product and quantity")
    public void testValidateOrderDetails() {
        // Initialize pages and user data
        SearchContent searchContent = SearchContent.builder().build().init();
        CartPage cartPage = new CartPage(getWebDriver());
        User user = User.builder().build().userWithValidCredentials();
        HomePage homePage = new HomePage(getWebDriver());

        // Login and navigate to order details
        homePage.getHeader().navigateToLoginPage().login(user);
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        int quantitySelected = productDetailsPage.getQuantitySelected();
        CartModal cartModal = new CartModal(getWebDriver());

        // Add product to cart and complete order
        if (!productDetailsPage.isProductSoldOut()) {
            cartPage = productDetailsPage.clickAddToCart();
            Assert.assertTrue(cartModal.getSuccessMessage().contains("Item added to your cart"));
        } else {
            Assert.fail("Product Out of Stock");
        }

        cartModal.clickAddToCart();
        BillingPage billingPage = cartPage.clickCheckOutBtn();
        String product = String.valueOf(billingPage.getProductName());
        billingPage.selectPayment();
        HomePage homePage1 = billingPage.completeOrder();
        OrderDetailsPage orderDetailsPage = homePage1.getHeader().navigateToProfilePage().navToOrderDetails();

        // Verify order details
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String formattedDate = currentDate.format(formatter);
        Assert.assertTrue(orderDetailsPage.getDateAndTime().contains(formattedDate), "Order date matches current date");
        Assert.assertEquals(orderDetailsPage.getQuantity(), quantitySelected, "Quantity matches selected quantity");
        Assert.assertTrue(orderDetailsPage.getProductName().contains(product), "Product name matches selected product");
    }
}
