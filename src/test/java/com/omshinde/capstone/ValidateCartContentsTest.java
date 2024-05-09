package com.omshinde.capstone;

import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.modals.CartModal;
import com.omshinde.capstone.pages.CartPage;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.ProductDetailsPage;
import com.omshinde.capstone.pages.SearchResultPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ValidateCartContentsTest extends BaseTest{
    @Test(testName = "verifyUserRedirectToCartPageOnAddingProduct",description = "Verifies that the user is redirected to the cart page after adding a product to the cart.")
    public void userIsAbleToRedirectToCartPage(){
        SearchContent searchContent=SearchContent.builder().build().bellDress();
        HomePage homePage=new HomePage(getWebDriver());
        homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        SearchResultPage searchResultPage=new SearchResultPage(getWebDriver());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        CartModal cartModal=new CartModal(getWebDriver());

        CartPage cartPage=new CartPage(getWebDriver());
        if(!productDetailsPage.isProductSoldOut()){
            cartPage = productDetailsPage.clickAddToCart();
        }else{
            Assert.fail("Product Out of Stock");
        }

        String cartHeading = cartModal.clickAddToCart().getCartHeading();
        Assert.assertTrue(cartHeading.contains("Your cart"));
    }


    @Test(testName = "verifyProductListedInCartWithCorrectDetails", description = "Checks that the product added is listed in the cart with the correct details such as name, size, and quantity.")
    public void checkProductAddedIsListedInCartWithCorrectDetails() {
        SearchContent searchContent=SearchContent.builder().build().bellDress();
        HomePage homePage=new HomePage(getWebDriver());
        homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        SearchResultPage searchResultPage=new SearchResultPage(getWebDriver());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        CartModal cartModal=new CartModal(getWebDriver());

        CartPage cartPage=new CartPage(getWebDriver());

        if(!productDetailsPage.isProductSoldOut()){
            cartPage = productDetailsPage.clickAddToCart();
        }else{
            Assert.fail("Product Out of Stock");
        }

        cartModal.clickAddToCart();

        Assert.assertTrue(cartPage.getAddedProductName().contains(productDetailsPage.getProductName()));
        Assert.assertTrue((cartPage.getSizeOfAddedProduct().contains(productDetailsPage.getSizeSelected())));
        Assert.assertEquals(cartPage.getQuantityOfAddedProduct(),productDetailsPage.getQuantitySelected());
        Assert.assertEquals(cartPage.getProductAddedPrice(),productDetailsPage.getProductPrice());

    }


}
