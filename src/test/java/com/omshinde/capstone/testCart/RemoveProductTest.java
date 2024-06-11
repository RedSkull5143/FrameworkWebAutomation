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

/**
 * Epic: Cart Management
 * Feature: Product Removal
 * Story: As a shopper, I want to remove products from my cart so that I can adjust my purchase before checkout.
 * Test class for removing a product from the cart.
 */
@Epic("Cart Management")
@Feature("Product Removal")
public class RemoveProductTest extends BaseTest {

    /**
     * Verifies the removal of a product from the cart.
     */
    @Test(testName = "Verify Product Removal From Cart", description = "Verifies that the product is successfully removed from the cart.")
    @Story("As a shopper, I want to remove products from my cart so that I can adjust my purchase before checkout.")
    public void verifyProductRemovalFromCart(){
        // Arrange
        SearchContent searchContent = SearchContent.builder().build().bellDress();
        HomePage homePage = new HomePage(getWebDriver());

        // Act
        homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());
        SearchResultPage searchResultPage = new SearchResultPage(getWebDriver());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        CartModal cartModal = new CartModal(getWebDriver());
        CartPage cartPage = new CartPage(getWebDriver());

        if(!productDetailsPage.isProductSoldOut()) {
            productDetailsPage.clickAddToCart();
        } else {
            Assert.fail("Product Out of Stock");
        }

        cartModal.clickAddToCart();
        cartPage.removeProductFromCart(cartPage.getAddedProductName());

        // Assert
        Assert.assertTrue(cartPage.isCartEmpty());
    }
}
