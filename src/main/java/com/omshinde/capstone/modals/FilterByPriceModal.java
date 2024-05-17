package com.omshinde.capstone.modals;

import com.omshinde.capstone.pages.BasePage;
import com.omshinde.capstone.pages.SearchResultPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterByPriceModal extends BasePage {

    @FindBy(xpath = "//*[@id=\"shopify-section-template--15328405553373__main\"]/div/div[1]/h1")
    private WebElement head;

    @FindBy(xpath = "//*[@id=\"Filter-Price-GTE\"]")
    private WebElement startPrice;

    @FindBy(xpath = "//*[@id=\"Filter-Price-LTE\"]")
    private WebElement endPrice;

    public void setStartPrice(int input){
        textBox.type(startPrice,input);
    }

    public void setEndPrice(int input){
        textBox.type(endPrice,input);
    }

    public boolean searchByPrice() throws InterruptedException {
        setStartPrice(500);
        setEndPrice(800);
        Actions actions = new Actions(webDriver);
        actions.doubleClick(head).perform();
        Thread.sleep(5000);
        boolean isPriceInRange = isInRange(getStartPriceValue(), getEndPriceValue());
        if (isPriceInRange) {
            System.out.println("Products found within the specified price range.");
        } else {
            System.out.println("No products found within the specified price range.");
        }
        return isPriceInRange;
    }


    public boolean isInRange(int startPrice, int endPrice) {
        WebElement allProducts = webDriver.findElement(By.xpath("//*[@id=\"product-grid\"]/ul"));
        List<WebElement> productsPriceList = allProducts.findElements(By.xpath("//*[@id=\"product-grid\"]/ul/li/div/div[1]/div/div/div/div/span[2]"));
        Pattern pattern = Pattern.compile("\\d+");

        for (WebElement element : productsPriceList) {
            String priceText = element.getText();
            Matcher matcher = pattern.matcher(priceText);
            if (matcher.find()) {
                String matchedDigits = matcher.group();
                int price = Integer.parseInt(matchedDigits);
                if (price >= startPrice && price <= endPrice) {
                    return true; // Price lies between start and end price
                }
            }
        }
        return false; // No price found in the specified range
    }

    public int getStartPriceValue() {
        return Integer.parseInt(startPrice.getAttribute("value"));
    }

    public int getEndPriceValue() {
        return Integer.parseInt(endPrice.getAttribute("value"));
    }

    public FilterByPriceModal(WebDriver webDriver) {
        super(webDriver);
    }
}
