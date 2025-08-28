package stepDefinitions;

import drivers.DriverManager;
import hooks.AdsBaseClass;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pageobjects.AdsLoginPage;

public class LoginSteps {

    private AdsLoginPage loginPage;

    @Given("I am on the Ads login pages")
    public void i_am_on_the_ads_login_pages() {
        loginPage = new AdsLoginPage(DriverManager.getDriver());
    }

    @When("I am login with valid credentials")
    public void i_am_login_with_valid_credentials() {
        loginPage.login(AdsBaseClass.username, AdsBaseClass.password);
    }

    @Then("verify the page title of Appselection")
    public void verify_the_page_title_of_Appselection() {
        String expectedTitle = "App Selection || ads Application";
        loginPage.verifytitle(expectedTitle);
    }
}
