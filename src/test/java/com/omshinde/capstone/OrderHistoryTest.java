package com.omshinde.capstone;

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

@Epic("Order Management")
@Feature("Order History and Details")
public class OrderHistoryTest extends BaseTest {

    @Test(testName = "Validate Order History Information", description = "Validates the order history information of a user")
    @Story("As a user, I want to view my order history to see the details of my previous purchases")
    public void validateOrderHistoryInformation() {
        SearchContent searchContent = SearchContent.builder().build().init();
        CartPage cartPage = new CartPage(getWebDriver());
        User user = User.builder().build().userWithValidCredentials();
        HomePage homePage = new HomePage(getWebDriver());

        homePage.getHeader().navToLoginPage().login(user);
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        CartModal cartModal = new CartModal(getWebDriver());

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
        ProfilePage profilePage = homePage1.getHeader().navToProfilePage();

        Assert.assertTrue(profilePage.getPaymentStatus().contains("Pending"));
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String formattedDate = currentDate.format(formatter);

        Assert.assertTrue(profilePage.getDate().contains(formattedDate));
    }

    @Test(testName = "Validate Order Details", description = "Validates the details of a specific order")
    @Story("As a user, I want to view the details of a specific order to verify the product and quantity")
    public void validateOrderDetails() {
        SearchContent searchContent = SearchContent.builder().build().init();
        CartPage cartPage = new CartPage(getWebDriver());
        User user = User.builder().build().userWithValidCredentials();
        HomePage homePage = new HomePage(getWebDriver());

        homePage.getHeader().navToLoginPage().login(user);
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        int quantitySelected = productDetailsPage.getQuantitySelected();
        CartModal cartModal = new CartModal(getWebDriver());

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
        OrderDetailsPage orderDetailsPage = homePage1.getHeader().navToProfilePage().navToOrderDetails();

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String formattedDate = currentDate.format(formatter);

        Assert.assertTrue(orderDetailsPage.getDateAndTime().contains(formattedDate), "Date should be equal but it is not");
        Assert.assertEquals(orderDetailsPage.getQuantity(), quantitySelected, "Quantity should be the same but it is not");
        Assert.assertTrue(orderDetailsPage.getProductName().contains(product), "Product name should be the same but is not");
    }
}
