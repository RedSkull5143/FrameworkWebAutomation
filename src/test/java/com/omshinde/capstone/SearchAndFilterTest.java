package com.omshinde.capstone;

import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.pages.HomePage;
import com.omshinde.capstone.pages.SearchResultPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class SearchAndFilterTest extends BaseTest{

    @Test
    public void searchAndFilterProductAccordingToPriceRange() throws InterruptedException {
        SearchContent searchContent= SearchContent.builder().build().init();
        HomePage homePage=new HomePage(getWebDriver());
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());
        Categories categories = new Categories(getWebDriver());
        categories.openFilterModal().searchByPrice();

        Assert.assertTrue(categories.openFilterModal().searchByPrice(), "All products should be within the price range.");

    }
}
