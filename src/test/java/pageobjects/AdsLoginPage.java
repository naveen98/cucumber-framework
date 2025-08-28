package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.Webdriverwaitutils;

public class AdsLoginPage {

    private final WebDriver driver;
    private final Webdriverwaitutils wait;

    @FindBy(id = "appUserName") private WebElement txtUsername;
    @FindBy(id = "appPassword") private WebElement txtPassword;
    @FindBy(id = "loginBtn")    private WebElement loginBtn;

    @FindBy(xpath = "(//strong[normalize-space()='Apollo Digital Signage'])[1]")
    private WebElement appselection;

    public AdsLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        PageFactory.initElements(driver, this);
    }


    public void login(String username, String password) {
        try {
            wait.waitForVisibility(txtUsername).clear();
            txtUsername.sendKeys(username);

            wait.waitForVisibility(txtPassword).clear();
            txtPassword.sendKeys(password);

            System.out.println("Please solve CAPTCHA if present...");
            Thread.sleep(30_000); // 30s wait for CAPTCHA

            WebElement lgbtn = wait.waitForVisibility(loginBtn);
            if (lgbtn != null && lgbtn.isDisplayed()) {
                wait.waitForClickability(loginBtn).click();
            }
        } catch (Exception e) {
            System.out.println("Exception during login: " + e.getMessage());
        }
    }

    public void verifytitle(String expectedTitle) {
        try {
            // wait until appselection is visible
            WebElement app = wait.waitForVisibility(appselection);

            if (app != null && app.isDisplayed()) {
                String actualTitle = driver.getTitle();
                System.out.println("Actual Title: " + actualTitle);
                Assert.assertEquals(actualTitle, expectedTitle, "Page title does not match after login!");
                System.out.println("Expected Title: " + expectedTitle);
            } else {
                Assert.fail("App selection element not displayed; cannot verify title.");
            }

        } catch (Exception e) {
            System.out.println("Title verification failed: " + e.getMessage());
            Assert.fail("Title verification failed: " + e.getMessage());
        }
    }

}
