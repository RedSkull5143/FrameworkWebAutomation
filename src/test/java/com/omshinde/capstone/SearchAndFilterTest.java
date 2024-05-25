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
@Epic("Search and Filter")
@Feature("Product Search")
public class SearchAndFilterTest extends BaseTest{

    // Test verifies that products can be filtered according to the price range
    @Test(testName = "testSearchAndFilterProductAccordingToPriceRange", description = "Verifies that products can be filtered according to the price range.")
    @Story("User filters products by price range")
    public void testSearchAndFilterProductAccordingToPriceRange() throws InterruptedException {
        // Initialize test data and pages
        SearchContent searchContent = SearchContent.builder().build().init();
        HomePage homePage = new HomePage(getWebDriver());

        // Search for a product and apply price filter
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());
        Categories categories = new Categories(getWebDriver());
        boolean isPriceFiltered = categories.openFilterModal().searchByPrice();

        // Assert that products are within the specified price range after applying the price filter
        Assert.assertTrue(isPriceFiltered, "Products should be within the specified price range after applying the price filter.");
    }
}
