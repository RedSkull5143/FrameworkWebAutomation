package com.omshinde.capstone.testProdcuts;

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
    }

    // Test verifies that the user can search for a specific product by name
    @Test(testName = "testUserCanSearchSpecificProductByName", description = "Verifies that the user can search for a specific product by name.")
    @Story("User searches for a specific product by name")
    public void testUserCanSearchSpecificProductByName(){
        // Arrange
        logger.info("Initializing test data and pages");
        SearchContent searchContent = SearchContent.builder().build().bellDress();
        HomePage homePage = new HomePage(getWebDriver());

        // Act
        logger.info("Searching for a specific product by name");
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        // Assert
        logger.info("Asserting that the product name matches the searched input");
        Assert.assertTrue(searchContent.getInput().contains(searchResultPage.getProductName()));

        logger.info("Clicking to view the product details page");
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();

        // Assert
        logger.info("Asserting that the product name on the details page matches the searched input");
        Assert.assertTrue((searchContent.getInput().contains(productDetailsPage.getProductName())));
    }

    // Test verifies that the user can search for a specific product by index
    @Test(testName = "testUserCanSearchSpecificProductByIndex", description = "Verifies that the user can search for a specific product by index.")
    @Story("User searches for a specific product by index")
    public void testUserCanSearchSpecificProductByIndex(){
        // Arrange
        logger.info("Initializing test data and pages");
        SearchContent searchContent = SearchContent.builder().build().bellDress();
        HomePage homePage = new HomePage(getWebDriver());

        // Act
        logger.info("Searching for a specific product by index");
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        // Assert
        logger.info("Clicking to view the product details page by index");
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByIndex(0);

        // Assert
        logger.info("Asserting that the product name on the details page matches the searched input");
        Assert.assertTrue((searchContent.getInput().contains(productDetailsPage.getProductName())));
    }

    // Test verifies user can search with special characters
    @Test(testName = "testUserCanSearchWithSpecialCharacters", description = "User is able to search with special characters")
    @Story("User searches for a product using special characters")
    public void testUserCanSearchWithSpecialCharacters() {
        // Arrange
        logger.info("Initializing test data and pages");
        SearchContent searchContent = SearchContent.builder().build().specialCharacters();
        HomePage homePage = new HomePage(getWebDriver());

        // Act
        logger.info("Performing search and verifying search result");
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());
        boolean isProductAvailable = !searchResultPage.getProductName().isEmpty();

        // Assert
        logger.info("Asserting search results");
        Assert.assertTrue(searchResultPage.getResultsCount().isDisplayed(), "Search results page should load without errors.");
        Assert.assertTrue(isProductAvailable, "Search results should be relevant to the query or a proper message should be displayed.");
    }

    // Test verifies product opened is same as product clicked
    @Test(testName = "testProductOpenedIsSameAsProductClicked", description = "Product opened should be the same as the product clicked")
    @Story("User verifies product opened is same as product clicked")
    public void testProductOpenedIsSameAsProductClicked() {
        // Arrange
        logger.info("Initializing test data and pages");
        SearchContent searchContent = SearchContent.builder().build().bellDress();
        HomePage homePage = new HomePage(getWebDriver());

        // Act
        logger.info("Performing search and clicking on the first product");
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByIndex(0);

        // Assert
        logger.info("Verifying product opened is the same as the product clicked");
        Assert.assertEquals(productDetailsPage.getProductName(), searchContent.getInput(), "Product opened should be the same as the product clicked");
    }

}
