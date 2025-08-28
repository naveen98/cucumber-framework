package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.Webdriverwaitutils;


public class AdsAppselecctionpage {
    WebDriver driver;
    Webdriverwaitutils wait;

    public AdsAppselecctionpage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//strong[normalize-space()='Apollo Digital Signage'])[1]")
    private WebElement appselection;

    @FindBy(xpath = "(//span[contains(text(),'Dashboard')])[1]")
    private WebElement dashboardtext;

    public void appselection() {
        WebElement app = wait.waitForVisibility(appselection);
        wait.waitForClickability(app).click();
    }

    public void isTitleAsExpected(String expectedTitle) {
        WebElement dash = wait.waitForVisibility(dashboardtext);
        if (dash != null && dash.isDisplayed()) {
            String actualTitle = driver.getTitle();
            System.out.println("Actual page title: " + actualTitle);
            Assert.assertEquals(actualTitle, expectedTitle, "Page Title mismatch!");
        } else {
            Assert.fail("Dashboard not displayed after app selection!");
        }
    }
}
