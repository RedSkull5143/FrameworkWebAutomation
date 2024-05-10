package com.omshinde.capstone;

import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.modals.CartModal;
import com.omshinde.capstone.pages.CartPage;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.ProductDetailsPage;
import com.omshinde.capstone.pages.SearchResultPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RemoveProductTest extends BaseTest{

    @Test
    public void verifyProductRemovalFromCart() throws InterruptedException {
        SearchContent searchContent=SearchContent.builder().build().bellDress();
        HomePage homePage=new HomePage(getWebDriver());
        homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        SearchResultPage searchResultPage=new SearchResultPage(getWebDriver());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        CartModal cartModal=new CartModal(getWebDriver());
        CartPage cartPage=new CartPage(getWebDriver());

        if(!productDetailsPage.isProductSoldOut()){
            productDetailsPage.clickAddToCart();
        }else{
            Assert.fail("Product Out of Stock");
        }

        cartModal.clickAddToCart();
        cartPage.removeProductFromCart(cartPage.getAddedProductName());
        Assert.assertTrue(cartPage.isCartEmpty());

    }
}
