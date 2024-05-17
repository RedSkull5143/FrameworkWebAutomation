package com.omshinde.capstone;

import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.SearchResultPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SpecialCharactersSearchTest extends BaseTest{

    @Test(testName = "Search with Special Characters",description = "Verify search functionality using special characters.")
    public void searchWithSpecialCharacters(){
        SearchContent searchContent=SearchContent.builder().build().specialCharacters();
        HomePage homePage=new HomePage(getWebDriver());
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());
        boolean isProductAvailable=!searchResultPage.getProductName().isEmpty();

        Assert.assertTrue(searchResultPage.getResultsCount().isDisplayed(),"Search results page should load without errors.");
        Assert.assertTrue(isProductAvailable,"Search results should be relevant to the query or a proper message should be displayed.");
    }
}
