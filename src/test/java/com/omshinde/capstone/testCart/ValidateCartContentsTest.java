package com.omshinde.capstone.testCart;

import com.omshinde.capstone.util.BaseTest;
import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.modals.CartModal;
import com.omshinde.capstone.pages.CartPage;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.ProductDetailsPage;
import com.omshinde.capstone.pages.SearchResultPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Epic: Cart Functionality
 * Feature: Validate Cart Contents
 * Test class for validating cart contents.
 */
@Epic("Cart Functionality")
@Feature("Validate Cart Contents")
public class ValidateCartContentsTest extends BaseTest {

    // Logger for logging in this class
    private static final Logger logger = LogManager.getLogger(ValidateCartContentsTest.class);

    // Test verifies user redirection to cart page after adding a product
    @Test(testName = "testUserRedirectToCartPageOnAddingProduct", description = "Verifies that the user is redirected to the cart page after adding a product to the cart.")
    @Story("User adds a product to the cart and is redirected to the cart page")
    public void testUserRedirectToCartPageOnAddingProduct() {
        // Arrange
        SearchContent searchContent = SearchContent.builder().build().bellDress();
        logger.info("Initializing search content: {}", searchContent);
        HomePage homePage = new HomePage(getWebDriver());
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());
        logger.info("Performed search for product: {}", searchContent.getInput());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        logger.info("Navigated to product details page");

        // Act
        CartModal cartModal = new CartModal(getWebDriver());
        CartPage cartPage = new CartPage(getWebDriver());
        if (!productDetailsPage.isProductSoldOut()) {
            cartPage = productDetailsPage.clickAddToCart();
            logger.info("Added product to cart");
        } else {
            Assert.fail("Product Out of Stock");
        }
        String cartHeading = cartModal.clickAddToCart().getCartHeading();
        logger.info("Clicked on Add to Cart button in modal");
        Assert.assertTrue(cartHeading.contains("Your cart"), "Landed on Cart Page");

        // Assert
        String expectedUrl = "https://web-playground.ultralesson.com/cart";
        String actualUrl = getWebDriver().getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "User should be navigated to the cart page.");
        logger.info("Verified user redirection to cart page");
    }

    // Test verifies product details in the cart match the added product
    @Test(testName = "testProductListedInCartWithCorrectDetails", description = "Checks that the product added is listed in the cart with the correct details such as name, size, and quantity.")
    @Story("User adds a product to the cart and verifies its details in the cart")
    public void testProductListedInCartWithCorrectDetails() {
        // Arrange
        SearchContent searchContent = SearchContent.builder().build().bellDress();
        logger.info("Initializing search content: {}", searchContent);
        HomePage homePage = new HomePage(getWebDriver());
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());
        logger.info("Performed search for product: {}", searchContent.getInput());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        logger.info("Navigated to product details page");
        double productPrice = productDetailsPage.getProductPrice();
        String productName = productDetailsPage.getProductName();
        String productSize = productDetailsPage.getSizeSelected();
        int productQuantity = productDetailsPage.getQuantitySelected();

        // Act
        CartModal cartModal = new CartModal(getWebDriver());
        CartPage cartPage = new CartPage(getWebDriver());
        if (!productDetailsPage.isProductSoldOut()) {
            cartPage = productDetailsPage.clickAddToCart();
            logger.info("Added product to cart");
        } else {
            Assert.fail("Product Out of Stock");
        }
        cartModal.clickAddToCart();
        logger.info("Clicked on Add to Cart button in modal");

        // Assert
        double expectedTotalPrice = productPrice * productQuantity;
        double actualTotalPrice = cartPage.getProductAddedPrice();
        Assert.assertTrue(cartPage.getAddedProductName().contains(productName));
        Assert.assertTrue(cartPage.getSizeOfAddedProduct().contains(productSize));
        Assert.assertEquals(cartPage.getQuantityOfAddedProduct(), productQuantity);
        Assert.assertEquals(actualTotalPrice, expectedTotalPrice);
        logger.info("Verified product details in the cart");
    }
}
