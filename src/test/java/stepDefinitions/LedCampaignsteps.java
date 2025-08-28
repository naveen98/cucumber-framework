package stepDefinitions;

import drivers.DriverManager;
import hooks.AdsBaseClass;
import io.cucumber.java.en.*;
import pageobjects.AdsAppselecctionpage;
import pageobjects.AdsLoginPage;
import pageobjects.NavigatetoLedCampaignPage;

public class LedCampaignsteps {

    private AdsLoginPage loginPage;
    private AdsAppselecctionpage appSelectPage;
    private NavigatetoLedCampaignPage ledNavPage;

    // ---------- Background steps ----------
    @Given("I am on the Ads login page")
    public void i_am_on_the_ads_login_page() {
        loginPage = new AdsLoginPage(DriverManager.getDriver());
    }

    @When("I login with valid credentials")
    public void i_login_with_valid_credentials() {
        loginPage.login(AdsBaseClass.username, AdsBaseClass.password);
    }

    @Then("verify the page title")
    public void verify_the_page_title() {
        String expectedTitle = "App Selection || ads Application";
        loginPage.verifytitle(expectedTitle);
    }

    // ---------- App selection ----------
    @When("I select the Apollo Digital Signage app")
    public void i_select_the_apollo_digital_signage_app() {
        appSelectPage = new AdsAppselecctionpage(DriverManager.getDriver());
        appSelectPage.appselection();
    }

    @Then("the dashboard title should be verified")
    public void the_dashboard_title_should_be_verified() {
        String expectedDashboardTitle = "Dashboard || ads Application";
        appSelectPage.isTitleAsExpected(expectedDashboardTitle);
    }

    // ---------- LED Campaign navigation ----------
    @When("I navigate to the LED Campaign page")
    public void i_navigate_to_the_led_campaign_page() {
        ledNavPage = new NavigatetoLedCampaignPage(DriverManager.getDriver());
        ledNavPage.clickonledcampaign();
    }

    @Then("the LED Campaign page title should be verified")
    public void the_led_campaign_page_title_should_be_verified() {
        String expectedLedTitle = "LED Campaigns || ads Application";
        ledNavPage.verifyTitle(expectedLedTitle);
    }
}
