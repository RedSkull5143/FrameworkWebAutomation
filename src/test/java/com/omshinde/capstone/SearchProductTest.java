package com.omshinde.capstone;

import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.ProductDetailsPage;
import com.omshinde.capstone.pages.SearchResultPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchProductTest extends BaseTest{

    @Test(description = "user is able to navigate to the Search Result Page")
    public void userIsAbleToViewResultPage(){
        SearchContent searchContent=SearchContent.builder().build().init();
        HomePage homePage=new HomePage(getWebDriver());
        homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        SearchResultPage searchResultPage=new SearchResultPage(getWebDriver());
        String searchResultHeading = searchResultPage.getSearchResultHeading();

        Assert.assertTrue(searchResultHeading.contains("Search results"));
    }

    @Test(description = "user is able to search for a specific product by Name")
    public void userIsAbleToSearchSpecificProductByName(){
        SearchContent searchContent=SearchContent.builder().build().bellDress();
        HomePage homePage=new HomePage(getWebDriver());
        homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        SearchResultPage searchResultPage=new SearchResultPage(getWebDriver());
        Assert.assertTrue(searchContent.getInput().contains(searchResultPage.getProductName()));

        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        Assert.assertTrue((searchContent.getInput().contains(productDetailsPage.getProductName())));
    }

    @Test(description = "user is able to search for a specific product by index")
    public void userIsAbleToSearchSpecificProductByIndex(){
        SearchContent searchContent=SearchContent.builder().build().bellDress();
        HomePage homePage=new HomePage(getWebDriver());
        homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        SearchResultPage searchResultPage=new SearchResultPage(getWebDriver());

        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByIndex(0);
        Assert.assertTrue((searchContent.getInput().contains(productDetailsPage.getProductName())));
    }
}
