package com.omshinde.capstone;

import com.omshinde.capstone.actions.SearchContent;
import com.omshinde.capstone.modals.CartModal;
import com.omshinde.capstone.models.User;
import com.omshinde.capstone.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckOutPageTest extends BaseTest{

    @Test(testName = "Use is able to buy Product",description = "verifies that a user can successfully purchase a product and receive a confirmation message.")
    public void userIsAbleToBuyProduct(){
        SearchContent searchContent= SearchContent.builder().build().init();
        CartPage cartPage=new CartPage(getWebDriver());
        User user= User.builder().build().userWithValidCredentials();
        HomePage homePage=new HomePage(getWebDriver());
        homePage.getHeader().navToLoginPage().login(user);
        SearchResultPage searchResultPage = homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

        ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
        CartModal cartModal=new CartModal(getWebDriver());

        if(!productDetailsPage.isProductSoldOut()){
            cartPage = productDetailsPage.clickAddToCart();
            Assert.assertTrue(cartModal.getSuccessMessage().contains("Item added to your cart"));
        }else{
            Assert.fail("Product Out of Stock");
        }

        cartModal.clickAddToCart();
        BillingPage billingPage = cartPage.clickCheckOutBtn();
        billingPage.selectPayment();
        billingPage.completeOrder();
        String confirmationMessage = billingPage.getConfirmationMessage();
        Assert.assertTrue(confirmationMessage.contains("Your order is confirmed"));
    }
}
