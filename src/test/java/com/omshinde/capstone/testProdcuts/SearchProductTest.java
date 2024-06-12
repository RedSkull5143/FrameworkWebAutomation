package com.omshinde.capstone.testProducts;

import com.omshinde.capstone.util.BaseTest;
import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.ProductDetailsPage;
import com.omshinde.capstone.pages.SearchResultPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Annotations for Epic, Feature, and Story
@Epic("Search and Navigation")
@Feature("Product Search")
public class SearchProductTest extends BaseTest {

    // Logger for logging in this class
    private static final Logger logger = LogManager.getLogger(SearchProductTest.class);

    // Test verifies that the user can navigate to the search result page
    @Test(testName = "testUserCanNavigateToSearchResultPage", description = "Verifies that the user can navigate to the search result page.")
    @Story("User navigates to search result page")
    public void testUserCanNavigateToSearchResultPage(){
        try {
            // Arrange
            logger.info("Initializing test data and pages");
            SearchContent searchContent = SearchContent.builder().build().init();
            HomePage homePage = new HomePage(getWebDriver());

            // Act
            logger.info("Searching for a product and navigating to the search result page");
            SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

            // Assert
            logger.info("Asserting that the search result heading contains 'Search results'");
            String searchResultHeading = searchResultPage.getSearchResultHeading();
            Assert.assertTrue(searchResultHeading.contains("Search results"));
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    // Other tests are similar, let's add exception handling and comments to them as well
}
