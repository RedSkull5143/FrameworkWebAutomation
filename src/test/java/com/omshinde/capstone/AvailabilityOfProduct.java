package com.omshinde.capstone;

import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.ProductDetailsPage;
import com.omshinde.capstone.pages.SearchResultPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AvailabilityOfProduct extends BaseTest{
    @Test(description = "verify that product is available")
    public void verifyThatProductIsAvailable(){
        SearchContent searchContent=SearchContent.builder().build().bellDress();
        HomePage homePage=new HomePage(getWebDriver());
        homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        SearchResultPage searchResultPage=new SearchResultPage(getWebDriver());

        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByIndex(0);
        Assert.assertFalse((productDetailsPage.isProductSoldOut()), "Product is Available");
    }

    @Test(description = "verify that product is sold out")
    public void verifyThatProductIsOutOfStock(){
        SearchContent searchContent=SearchContent.builder().build().soldOutProduct();
        HomePage homePage=new HomePage(getWebDriver());
        homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        SearchResultPage searchResultPage=new SearchResultPage(getWebDriver());

        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByIndex(0);
        Assert.assertTrue((productDetailsPage.isProductSoldOut()), "Product is Out of Stock");
    }

}
