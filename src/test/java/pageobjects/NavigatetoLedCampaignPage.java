package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.Webdriverwaitutils;


public class NavigatetoLedCampaignPage {
    private final WebDriver driver;
    private final Webdriverwaitutils wait;
    private final JavascriptExecutor js;

    public NavigatetoLedCampaignPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//li[@id='menu-li-led-campaigns']")
    private WebElement menuLedCampaigns;

    @FindBy(xpath = "//formly-group[@class='row dashboard-counts-row zc-fg-no-margin mx-0 ng-star-inserted']")
    private WebElement dashboardRow;

    public void clickonledcampaign() {
        try {
            WebElement led = wait.waitForVisibility(menuLedCampaigns);
            if (led != null && led.isDisplayed()) {
                wait.waitForClickability(led).click();
            }
        } catch (Exception e) {
            // fallback JS click
            js.executeScript("arguments[0].click();", menuLedCampaigns);
        }
    }

    public void verifyTitle(String expectedTitle) {
        try {
            WebElement row = wait.waitForVisibility(dashboardRow);
            if (row != null && row.isDisplayed()) {
                String actual = driver.getTitle();
                System.out.println("Actual title: " + actual);
                Assert.assertEquals(actual, expectedTitle, "Page title does not match!");
                System.out.println("Expected title: " + expectedTitle);
            } else {
                System.out.println("Row not displayed");
                Assert.fail("Row not displayed, cannot verify title.");
            }
        } catch (Exception e) {
            System.out.println("Exception while verifying title: " + e.getMessage());
            Assert.fail("Title verification failed due to exception.");
        }
    }
}
