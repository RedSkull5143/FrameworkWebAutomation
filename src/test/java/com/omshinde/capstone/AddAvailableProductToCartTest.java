package com.omshinde.capstone;

import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.modals.CartModal;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.ProductDetailsPage;
import com.omshinde.capstone.pages.SearchResultPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddAvailableProductToCartTest extends BaseTest{

    private static final Logger logger = LogManager.getLogger(AddAvailableProductToCartTest.class);
    private static final Logger searchLogger=LogManager.getLogger("SearchLogger");
    private static final Logger cartLogger=LogManager.getLogger("CartLogger");

    @Test(testName = "Verify that user adds product to cart when product in stock", description = "Verifies that a user is able to successfully add a product to the cart when it is available for purchase.")
    public void verifyUserIsAbleToAddProductToCart(){
        logger.info("Executing verifyUserIsAbleToAddProductToCart: Verifying that a user can add a product to the cart when it is in stock");
        SearchContent searchContent = SearchContent.builder().build().bellDress();
        searchLogger.info("Search Content : {}",searchContent.getInput());
        HomePage homePage=new HomePage(getWebDriver());
        homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        SearchResultPage searchResultPage=new SearchResultPage(getWebDriver());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        cartLogger.debug("Navigated to Product Details Page");

        CartModal cartModal=new CartModal(getWebDriver());

        if(!productDetailsPage.isProductSoldOut()){
            productDetailsPage.clickAddToCart();
            Assert.assertTrue(cartModal.getSuccessMessage().contains("Item added to your cart"));
            cartLogger.info("Product Added to the Cart");
        }else{
            Assert.fail("Product Out of Stock");
            cartLogger.error("Product is Out of Stock");
        }
    }

    @Test(testName = "verifyCartCountIncreasedOnProductAddition",description = "Verifies that the cart count is increased by one after adding a product to the cart.")
    public void verifyThatCartCountIsIncreasedAfterAddingProductToCart(){
        logger.info("Executing verifyThatCartCountIsIncreasedAfterAddingProductToCart: Verifying that the cart count is increased after adding a product to the cart");
        SearchContent searchContent = SearchContent.builder().build().bellDress();
        searchLogger.info("Search content: {}", searchContent.getInput());

        HomePage homePage=new HomePage(getWebDriver());
        homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        SearchResultPage searchResultPage=new SearchResultPage(getWebDriver());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        cartLogger.debug("Navigated to product details page.");

        CartModal cartModal=new CartModal(getWebDriver());

        if(!productDetailsPage.isProductSoldOut()){
            int initialCount = homePage.getHeader().getCartCount();
            productDetailsPage.clickAddToCart();
            Assert.assertTrue(cartModal.getSuccessMessage().contains("Item added to your cart"));
            int newCount = homePage.getHeader().getCartCount();
            Assert.assertEquals(newCount,initialCount+1);
            cartLogger.info("Cart count increased successfully.");

        }else{
            Assert.fail("Product Out of Stock");
            cartLogger.error("Product is out of stock.");

        }
    }
}
