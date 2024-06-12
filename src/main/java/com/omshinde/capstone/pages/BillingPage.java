package com.omshinde.capstone.pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class BillingPage extends BasePage {

    private WebDriver webDriver;
    private WebDriverWait wait;

    public BillingPage(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div/div/div[1]/div/div[2]/div/aside/div/section/div/section/div[2]/div[1]/div[2]/span")
    private WebElement subTotal;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div/div/div[1]/div/div[2]/div/aside/div/section/div/section/div[2]/div[1]/div[2]/span")
    private WebElement offerDiscount;

    @FindBy(xpath = "//*[@id=\"Form1\"]/div[1]/div/div/section[2]/div[1]/div/div[1]/div[1]/h2")
    private WebElement paymentTextEle;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div/div/div[1]/div/div[2]/div/aside/div/section/div/section/div[2]/div[5]/div[2]/div/div/span")
    private WebElement taxes;

    @FindBy(xpath = "//*[@id=\"tipping_list-tipping_list_options-collapsible\"]/div/div/div/div[1]/div/div")
    private WebElement tipButtonsEle;

    @FindBy(xpath = "//strong[@class='_19gi7yt0 _19gi7ytr _1fragemp6 _19gi7yt1 notranslate']")
    private WebElement totalAmountEle;

    @FindBy(id = "TipsInput")
    private WebElement customTipInput;

    @FindBy(xpath = "//*[@id=\"tipping_list-tipping_list_options-collapsible\"]/div/div/div/div[2]/div/button")
    private WebElement updateTipBtn;

    @FindBy(css = "div[class='_1fragem2i _1fragemnw iZ894'] p[class='_1x52f9s1 _1fragemnw _1x52f9sp _1fragempz']")
    private WebElement productName;

    @FindBy(xpath = "//*[@id=\"basic\"]/div/div[2]/label")
    private WebElement codBtn;

    @FindBy(id = "checkout-pay-button")
    private WebElement completeOrderBtn;

    @FindBy(xpath = "//*[@id=\"checkout-main\"]/div/div/div[2]/div/div[1]/section[1]/div[1]/h2")
    private WebElement confirmationMessage;

    public String getPaymentText() {
        wait.until(ExpectedConditions.visibilityOf(paymentTextEle));
        return webActions.getText(paymentTextEle);
    }

    /**
     * Get the subtotal amount.
     *
     * @return The subtotal amount as a double value.
     */
    public double getSubTotal() {
        try {
            wait.until(ExpectedConditions.visibilityOf(subTotal));
            String value = webActions.getText(subTotal);
            String numericStr = value.replaceAll("[^0-9.]", "");  // Remove all non-numeric characters
            return Double.parseDouble(numericStr);
        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException occurred while parsing subtotal: " + e.getMessage());
            return 0.0; // Return default value or handle accordingly
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while getting subtotal: " + e.getMessage());
            return 0.0; // Return default value or handle accordingly
        }
    }

    /**
     * Calculate the value for tip based on subtotal and discount.
     *
     * @return The calculated tip value as a double.
     */
    public double valueForTip() {
        try {
            double subTotal = getSubTotal();
            wait.until(ExpectedConditions.visibilityOf(offerDiscount));
            String value = webActions.getText(offerDiscount);
            String numericStr = value.replaceAll("[^0-9.]", "");  // Remove all non-numeric characters
            double amount = Double.parseDouble(numericStr);
            return subTotal - amount;
        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException occurred while calculating value for tip: " + e.getMessage());
            return 0.0; // Return default value or handle accordingly
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while calculating value for tip: " + e.getMessage());
            return 0.0; // Return default value or handle accordingly
        }
    }


    @FindBy(xpath = "//*[@id=\"tipping_list-tipping_list_options-collapsible\"]/div/div/div/div[1]/div/div/button[2]")
    private WebElement five;

    @FindBy(xpath = "//*[@id=\"tipping_list-tipping_list_options-collapsible\"]/div/div/div/div[1]/div/div/button[4]/span/div")
    private WebElement none;

    /**
     * Calculate the tip amount based on selected tip percentage.
     *
     * @return The calculated tip amount as a double.
     * @throws InterruptedException if the thread is interrupted while waiting.
     */
    public double tipAmount() throws InterruptedException {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(none)).click();
            wait.until(ExpectedConditions.visibilityOf(taxes));
            WebElement buttonWithAriaPressedTrue = null;
            List<WebElement> allButtons = tipButtonsEle.findElements(By.tagName("button"));
            for (WebElement button : allButtons) {
                if ("true".equals(button.getAttribute("aria-pressed"))) {
                    buttonWithAriaPressedTrue = button;
                    break;
                }
            }
            webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            double tipamount = 0;
            if (buttonWithAriaPressedTrue != null) {
                String buttonText = buttonWithAriaPressedTrue.getText();
                if (!buttonText.contains("None")) {
                    String percentageStr = buttonText.substring(0, buttonText.indexOf("%"));
                    double percentage = Double.parseDouble(percentageStr);
                    tipamount = (percentage / 100) * valueForTip();
                } else {
                    tipamount = 0;
                }
            }
            taxValue();
            return tipamount;
        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException occurred while calculating tip amount: " + e.getMessage());
            return 0.0; // Return default value or handle accordingly
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while calculating tip amount: " + e.getMessage());
            return 0.0; // Return default value or handle accordingly
        }
    }


    /**
     * Get the displayed tip amount from the page.
     *
     * @return The displayed tip amount as a double.
     */
    public double getTipDisplayed() {
        try {
            WebElement tipElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div/div/div[1]/div/div[2]/div/aside/div/section/div/section/div[2]/div[6]/div[2]/span")));
            String value = webActions.getText(tipElement);
            String numericStr = value.replaceAll("[^0-9.]", "");  // Remove all non-numeric characters
            return Double.parseDouble(numericStr);
        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException occurred while getting displayed tip amount: " + e.getMessage());
            return 0.0; // Return default value or handle accordingly
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while getting displayed tip amount: " + e.getMessage());
            return 0.0; // Return default value or handle accordingly
        }
    }

    /**
     * Get the tax value from the page.
     *
     * @return The tax value as a double.
     */
    public double taxValue() {
        try {
            wait.until(ExpectedConditions.visibilityOf(taxes));
            String value = webActions.getText(taxes);
            String numericStr = value.replaceAll("[^0-9.]", "");  // Remove all non-numeric characters
            return Double.parseDouble(numericStr);
        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException occurred while getting tax value: " + e.getMessage());
            return 0.0; // Return default value or handle accordingly
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while getting tax value: " + e.getMessage());
            return 0.0; // Return default value or handle accordingly
        }
    }

    /**
     * Get the offer value from the page.
     *
     * @return The offer value as a double.
     */
    public double offer() {
        try {
            wait.until(ExpectedConditions.visibilityOf(offerDiscount));
            String value = webActions.getText(offerDiscount);
            String numericStr = value.replaceAll("[^0-9.]", "");  // Remove all non-numeric characters
            return Double.parseDouble(numericStr);
        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException occurred while getting offer value: " + e.getMessage());
            return 0.0; // Return default value or handle accordingly
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while getting offer value: " + e.getMessage());
            return 0.0; // Return default value or handle accordingly
        }
    }


    /**
     * Calculate the total amount including subtotal, tip, tax, and offer.
     *
     * @return The total amount as a double.
     * @throws InterruptedException if the thread is interrupted.
     */
    public double totalAmount() throws InterruptedException {
        try {
            webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            return Math.round((getSubTotal() + tipAmount() + taxValue() - offer()) * 100.0) / 100.0;
        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException occurred while calculating total amount: " + e.getMessage());
            return 0.0; // Return default value or handle accordingly
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while calculating total amount: " + e.getMessage());
            return 0.0; // Return default value or handle accordingly
        }
    }

    /**
     * Get the auto-calculated total amount from the page.
     *
     * @return The auto-calculated total amount as a double.
     */
    public double autoTotalAmount() {
        try {
            wait.until(ExpectedConditions.visibilityOf(totalAmountEle));
            String value = webActions.getText(totalAmountEle);
            String numericStr = value.replaceAll("[^0-9.]", "");  // Remove all non-numeric characters
            return Double.parseDouble(numericStr);
        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException occurred while getting auto-calculated total amount: " + e.getMessage());
            return 0.0; // Return default value or handle accordingly
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while getting auto-calculated total amount: " + e.getMessage());
            return 0.0; // Return default value or handle accordingly
        }
    }

    /**
     * Select the payment method.
     */
    public void selectPayment() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(codBtn)).click();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while selecting payment method: " + e.getMessage());
        }
    }


    public HomePage completeOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(completeOrderBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"checkout-main\"]/div/div/div[2]/div/div[2]/div[1]/a"))).click();
        return new HomePage(webDriver);
    }

    public String getConfirmationMessage() {
        wait.until(ExpectedConditions.visibilityOf(confirmationMessage));
        return webActions.getText(confirmationMessage);
    }

    public String getProductName(){
        return webActions.getText(productName);
    }
}
