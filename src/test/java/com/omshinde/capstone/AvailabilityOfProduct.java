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
@Epic("Product Availability")
@Feature("Search and View Products")
public class AvailabilityOfProduct extends BaseTest {

    // Test for verifying that the product is available when the user searches for it
    @Test(testName = "testVerifyProductAvailability", description = "Verify that the product is available when the user searches for it.")
    @Story("User should see available products")
    public void testVerifyProductAvailability() {
        // Create a search content object for the product (bell dress in this case)
        SearchContent searchContent = SearchContent.builder().build().bellDress();

        // Navigate to the homepage and perform a search for the product
        HomePage homePage = new HomePage(getWebDriver());
        homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        // Navigate to the search results page and select the product to view its details
        SearchResultPage searchResultPage = new SearchResultPage(getWebDriver());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByIndex(0);

        // Verify that the product is available (not sold out)
        Assert.assertFalse((productDetailsPage.isProductSoldOut()), "Product is Available");
    }

    // Test for verifying that the product is marked as out of stock when the user searches for it
    @Test(testName = "testVerifyProductOutOfStock", description = "Verify that the product is marked as out of stock when the user searches for it.")
    @Story("User should see out of stock products")
    public void testVerifyProductOutOfStock() {
        // Create a search content object for a sold-out product
        SearchContent searchContent = SearchContent.builder().build().soldOutProduct();

        // Navigate to the homepage and perform a search for the product
        HomePage homePage = new HomePage(getWebDriver());
        homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        // Navigate to the search results page and select the product to view its details
        SearchResultPage searchResultPage = new SearchResultPage(getWebDriver());
        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByIndex(0);

        // Verify that the product is marked as out of stock
        Assert.assertTrue(productDetailsPage.isProductSoldOut(), "The product is marked Out of Stock as expected.");
    }
}
