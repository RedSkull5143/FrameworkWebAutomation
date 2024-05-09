package com.omshinde.capstone;

import com.omshinde.capstone.actions.SearchContent;
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

        if(!productDetailsPage.isProductSoldOut()){
            productDetailsPage.clickAddToCart();
            System.out.println("Item added to your cart");
        }else{
            Assert.fail("Product Out of Stock");
        }
    }
}
