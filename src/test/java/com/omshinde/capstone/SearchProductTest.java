package com.omshinde.capstone;

import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.ProductDetailsPage;
import com.omshinde.capstone.pages.SearchResultPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

// Annotations for Epic, Feature, and Story
@Epic("Search and Navigation")
@Feature("Product Search")
public class SearchProductTest extends BaseTest{

    // Test verifies that the user can navigate to the search result page
    @Test(testName = "testUserCanNavigateToSearchResultPage", description = "Verifies that the user can navigate to the search result page.")
    @Story("User navigates to search result page")
    public void testUserCanNavigateToSearchResultPage(){
        // Initialize test data and pages
        SearchContent searchContent = SearchContent.builder().build().init();
        HomePage homePage = new HomePage(getWebDriver());

        // Search for a product and navigate to the search result page
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        // Get the search result heading
        String searchResultHeading = searchResultPage.getSearchResultHeading();

        // Assert that the search result heading contains "Search results"
        Assert.assertTrue(searchResultHeading.contains("Search results"));
    }

    // Test verifies that the user can search for a specific product by name
    @Test(testName = "testUserCanSearchSpecificProductByName", description = "Verifies that the user can search for a specific product by name.")
    @Story("User searches for a specific product by name")
    public void testUserCanSearchSpecificProductByName(){
        // Initialize test data and pages
        SearchContent searchContent = SearchContent.builder().build().bellDress();
        HomePage homePage = new HomePage(getWebDriver());

        // Search for a specific product by name
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        // Assert that the product name matches the searched input
        Assert.assertTrue(searchContent.getInput().contains(searchResultPage.getProductName()));

        // Click to view the product details page
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();

        // Assert that the product name on the details page matches the searched input
        Assert.assertTrue((searchContent.getInput().contains(productDetailsPage.getProductName())));
    }

    // Test verifies that the user can search for a specific product by index
    @Test(testName = "testUserCanSearchSpecificProductByIndex", description = "Verifies that the user can search for a specific product by index.")
    @Story("User searches for a specific product by index")
    public void testUserCanSearchSpecificProductByIndex(){
        // Initialize test data and pages
        SearchContent searchContent = SearchContent.builder().build().bellDress();
        HomePage homePage = new HomePage(getWebDriver());

        // Search for a specific product by index
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        // Click to view the product details page by index
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByIndex(0);

        // Assert that the product name on the details page matches the searched input
        Assert.assertTrue((searchContent.getInput().contains(productDetailsPage.getProductName())));
    }

}
