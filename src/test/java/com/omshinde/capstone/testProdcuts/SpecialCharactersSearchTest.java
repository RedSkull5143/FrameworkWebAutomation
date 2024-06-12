package com.omshinde.capstone.testProdcuts;

import com.omshinde.capstone.util.BaseTest;
import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.SearchResultPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Annotations for Epic, Feature, and Story
@Epic("Search Functionality")
@Feature("Special Characters Search")
public class SpecialCharactersSearchTest extends BaseTest {

    // Logger for logging in this class
    private static final Logger logger = LogManager.getLogger(SpecialCharactersSearchTest.class);

    // Test verifies the search functionality using special characters
    @Test(testName = "testSearchWithSpecialCharacters", description = "Verify search functionality using special characters.")
    @Story("User searches with special characters")
    public void testSearchWithSpecialCharacters(){
        try {
            // Arrange
            logger.info("Initializing test data and pages");
            SearchContent searchContent = SearchContent.builder().build().specialCharacters();
            HomePage homePage = new HomePage(getWebDriver());

            // Act
            logger.info("Performing search with special characters");
            SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

            // Check if any product is available in the search results
            boolean isProductAvailable = !searchResultPage.getProductName().isEmpty();

            // Assert
            logger.info("Asserting search results");
            Assert.assertTrue(searchResultPage.getResultsCount().isDisplayed(), "Search results page should load without errors.");
            Assert.assertTrue(isProductAvailable, "Search results should be relevant to the query or a proper message should be displayed.");
        } catch (Exception e) {
            logger.error("An exception occurred during the test execution: " + e.getMessage());
            Assert.fail("Test failed due to an unexpected exception: " + e.getMessage());
        }
    }
}
