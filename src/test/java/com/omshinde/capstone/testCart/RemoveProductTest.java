package com.omshinde.capstone.testCart;

import com.omshinde.capstone.util.BaseTest;
import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.modals.CartModal;
import com.omshinde.capstone.pages.CartPage;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.ProductDetailsPage;
import com.omshinde.capstone.pages.SearchResultPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Epic: Cart Management
 * Feature: Product Removal
 * Story: As a shopper, I want to remove products from my cart so that I can adjust my purchase before checkout.
 * Test class for removing a product from the cart.
 */
@Epic("Cart Management")
@Feature("Product Removal")
public class RemoveProductTest extends BaseTest {

    // Logger for logging in this class
    private static final Logger logger = LogManager.getLogger(RemoveProductTest.class);

    /**
     * Verifies the removal of a product from the cart.
     */
    @Test(testName = "Verify Product Removal From Cart", description = "Verifies that the product is successfully removed from the cart.")
    @Story("As a shopper, I want to remove products from my cart so that I can adjust my purchase before checkout.")
    public void verifyProductRemovalFromCart(){
        // Arrange
        SearchContent searchContent = SearchContent.builder().build().bellDress();
        logger.info("Initializing search content: {}", searchContent);
        HomePage homePage = new HomePage(getWebDriver());
        logger.info("Navigated to the Home page");

        // Act
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());
        logger.info("Performed search for product: {}", searchContent.getInput());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        logger.info("Navigated to product details page");

        CartModal cartModal = new CartModal(getWebDriver());
        CartPage cartPage = new CartPage(getWebDriver());

        if(!productDetailsPage.isProductSoldOut()) {
            productDetailsPage.clickAddToCart();
            logger.info("Added product to cart");
        } else {
            Assert.fail("Product Out of Stock");
        }

        cartModal.clickAddToCart();
        logger.info("Clicked on Add to Cart button in modal");
        cartPage.removeProductFromCart(cartPage.getAddedProductName());
        logger.info("Removed product from cart");

        // Assert
        Assert.assertTrue(cartPage.isCartEmpty());
        logger.info("Cart is empty");
    }
}
