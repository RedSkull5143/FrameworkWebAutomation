package com.omshinde.capstone;

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

// Annotations for Epic, Feature, and Story
@Epic("Cart Functionality")
@Feature("Validate Cart Contents")
public class ValidateCartContentsTest extends BaseTest {

    // Test verifies user redirection to cart page after adding a product
    @Test(testName = "testUserRedirectToCartPageOnAddingProduct", description = "Verifies that the user is redirected to the cart page after adding a product to the cart.")
    @Story("User adds a product to the cart and is redirected to the cart page")
    public void testUserRedirectToCartPageOnAddingProduct() {
        // Initialize test data and pages
        SearchContent searchContent = SearchContent.builder().build().bellDress();
        HomePage homePage = new HomePage(getWebDriver());
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();

        // Add product to cart and assert redirection to cart page
        CartModal cartModal = new CartModal(getWebDriver());
        CartPage cartPage = new CartPage(getWebDriver());
        if (!productDetailsPage.isProductSoldOut()) {
            cartPage = productDetailsPage.clickAddToCart();
        } else {
            Assert.fail("Product Out of Stock");
        }
        String cartHeading = cartModal.clickAddToCart().getCartHeading();
        Assert.assertTrue(cartHeading.contains("Your cart"), "Landed on Cart Page");
        String expectedUrl = "https://web-playground.ultralesson.com/cart";
        String actualUrl = getWebDriver().getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "User should be navigated to the cart page.");
    }

    // Test verifies product details in the cart match the added product
    @Test(testName = "testProductListedInCartWithCorrectDetails", description = "Checks that the product added is listed in the cart with the correct details such as name, size, and quantity.")
    @Story("User adds a product to the cart and verifies its details in the cart")
    public void testProductListedInCartWithCorrectDetails() {
        // Initialize test data and pages
        SearchContent searchContent = SearchContent.builder().build().bellDress();
        HomePage homePage = new HomePage(getWebDriver());
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        double productPrice = productDetailsPage.getProductPrice();
        String productName = productDetailsPage.getProductName();
        String productSize = productDetailsPage.getSizeSelected();
        int productQuantity = productDetailsPage.getQuantitySelected();

        // Add product to cart and assert its details in the cart
        CartModal cartModal = new CartModal(getWebDriver());
        CartPage cartPage = new CartPage(getWebDriver());
        if (!productDetailsPage.isProductSoldOut()) {
            cartPage = productDetailsPage.clickAddToCart();
        } else {
            Assert.fail("Product Out of Stock");
        }
        cartModal.clickAddToCart();
        double expectedTotalPrice = productPrice * productQuantity;
        double actualTotalPrice = cartPage.getProductAddedPrice();
        Assert.assertTrue(cartPage.getAddedProductName().contains(productName));
        Assert.assertTrue(cartPage.getSizeOfAddedProduct().contains(productSize));
        Assert.assertEquals(cartPage.getQuantityOfAddedProduct(), productQuantity);
        Assert.assertEquals(actualTotalPrice, expectedTotalPrice);
    }
}
