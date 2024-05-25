package com.omshinde.capstone;

import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.SearchResultPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

// Annotations for Epic, Feature, and Story
@Epic("Search Functionality")
@Feature("Special Characters Search")
public class SpecialCharactersSearchTest extends BaseTest{

    // Test verifies the search functionality using special characters
    @Test(testName = "testSearchWithSpecialCharacters", description = "Verify search functionality using special characters.")
    @Story("User searches with special characters")
    public void testSearchWithSpecialCharacters(){
        // Initialize test data and pages
        SearchContent searchContent = SearchContent.builder().build().specialCharacters();
        HomePage homePage = new HomePage(getWebDriver());

        // Perform search with special characters
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        // Check if any product is available in the search results
        boolean isProductAvailable = !searchResultPage.getProductName().isEmpty();

        // Assert that the search results page and relevant products are displayed
        Assert.assertTrue(searchResultPage.getResultsCount().isDisplayed(), "Search results page should load without errors.");
        Assert.assertTrue(isProductAvailable, "Search results should be relevant to the query or a proper message should be displayed.");
    }
}
