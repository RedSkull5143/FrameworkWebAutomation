package com.omshinde.capstone;

import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.modals.CartModal;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.ProductDetailsPage;
import com.omshinde.capstone.pages.SearchResultPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddAvailableProductToCart extends BaseTest{

    @Test(testName = "Verify that user adds product to cart when product in stock", description = "Verifies that a user is able to successfully add a product to the cart when it is available for purchase.")
    public void verifyUserIsAbleToAddProductToCart(){
        SearchContent searchContent=SearchContent.builder().build().bellDress();
        HomePage homePage=new HomePage(getWebDriver());
        homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        SearchResultPage searchResultPage=new SearchResultPage(getWebDriver());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        CartModal cartModal=new CartModal(getWebDriver());

        if(!productDetailsPage.isProductSoldOut()){
            productDetailsPage.clickAddToCart();
            Assert.assertTrue(cartModal.getSuccessMessage().contains("Item added to your cart"));
        }else{
            Assert.fail("Product Out of Stock");
        }
    }

    @Test(testName = "verifyCartCountIncreasedOnProductAddition",description = "Verifies that the cart count is increased by one after adding a product to the cart.")
    public void verifyThatCartCountIsIncreasedAfterAddingProductToCart(){
        SearchContent searchContent=SearchContent.builder().build().bellDress();
        HomePage homePage=new HomePage(getWebDriver());
        homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        SearchResultPage searchResultPage=new SearchResultPage(getWebDriver());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        CartModal cartModal=new CartModal(getWebDriver());

        if(!productDetailsPage.isProductSoldOut()){
            int initialCount = homePage.getHeader().getCartCount();
            productDetailsPage.clickAddToCart();
            Assert.assertTrue(cartModal.getSuccessMessage().contains("Item added to your cart"));
            int newCount = homePage.getHeader().getCartCount();
            Assert.assertEquals(newCount,initialCount+1);
        }else{
            Assert.fail("Product Out of Stock");
        }
    }
}
