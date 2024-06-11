package com.omshinde.capstone.testProdcuts;

import com.omshinde.capstone.util.BaseTest;
import com.omshinde.capstone.Categories;
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
@Epic("Search and Filter")
@Feature("Product Search")
public class SearchAndFilterTest extends BaseTest {

    // Logger for logging in this class
    private static final Logger logger = LogManager.getLogger(SearchAndFilterTest.class);

    // Test verifies that products can be filtered according to the price range
    @Test(testName = "testSearchAndFilterProductAccordingToPriceRange", description = "Verifies that products can be filtered according to the price range.")
    @Story("User filters products by price range")
    public void testSearchAndFilterProductAccordingToPriceRange() throws InterruptedException {
        // Arrange
        logger.info("Initializing test data and pages");
        SearchContent searchContent = SearchContent.builder().build().init();
        HomePage homePage = new HomePage(getWebDriver());

        // Act
        logger.info("Searching for a product and applying price filter");
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());
        Categories categories = new Categories(getWebDriver());
        boolean isPriceFiltered = categories.openFilterModal().searchByPrice();

        // Assert
        logger.info("Asserting that products are within the specified price range after applying the price filter");
        Assert.assertTrue(isPriceFiltered, "Products should be within the specified price range after applying the price filter.");
    }
}
