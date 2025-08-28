package stepDefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pageobjects.AddBasicInformationDetailsPage;
import pageobjects.AddLedStylesPage;
import pageobjects.AdsPaginationsandcount;
import pageobjects.AdsTotalcountspages;
import utils.Excelutils;
import utils.TestDataConfigpaths;

public class CreateCampaignSteps {

    private AddBasicInformationDetailsPage basicPage;
    private AddLedStylesPage ledStylesPage;
    private AdsTotalcountspages countsPage;
    private AdsPaginationsandcount pageCounts;

    private int beforeTotalCampaignCount;
    private int beforePageCount;
    private boolean overallPass;

    private String[][] basicData; // name, startMonth, startYear, startDay, endMonth, endYear, endDay, startTime, endTime, displayText
    private String[][] ledData;   // font, fontSize, animation, stayTime, style, program, dimension

    @Given("I open the Ads app and land on the LED Campaigns page")
    public void i_open_the_ads_app_and_land_on_led_campaigns_page() {
        basicPage     = new AddBasicInformationDetailsPage(DriverManager.getDriver());
        ledStylesPage = new AddLedStylesPage(DriverManager.getDriver());
        countsPage    = new AdsTotalcountspages(DriverManager.getDriver());
        pageCounts    = new AdsPaginationsandcount(DriverManager.getDriver());
        basicPage.clickonledscreen();
        overallPass = true;
    }

    @And("I note current total campaign count and pages")
    public void i_note_current_total_campaign_count_and_pages() {
        beforeTotalCampaignCount = countsPage.getTotalCampaignCount();
        beforePageCount = pageCounts.getTotalPagesFromText();
    }

    @When("I create LED campaigns from basic excel")
    public void i_create_led_campaigns_from_basic_excel() {
        try {
            basicData = Excelutils.getcelldatas(
                    TestDataConfigpaths.BASIC_EXCEL_PATH,
                    TestDataConfigpaths.BASIC_SHEET
            );
        } catch (Exception e) {
            Assert.fail("Failed to read Basic excel: " + e.getMessage());
        }

        for (int j = 0; j < basicData.length; j++) {
            try {
                String name       = basicData[j][0];
                String startMonth = basicData[j][1];
                String startYear  = basicData[j][2];
                String startDay   = basicData[j][3];
                String endMonth   = basicData[j][4];
                String endYear    = basicData[j][5];
                String endDay     = basicData[j][6];
                String startTime  = basicData[j][7];
                String endTime    = basicData[j][8];
                String displayTxt = basicData[j][9];

                basicPage.addcreatebutton();
                basicPage.entertext(name);
                basicPage.startDate(startMonth, startYear, startDay);
                basicPage.endDate(endMonth, endYear, endDay);
                basicPage.setStartTime(startTime);
                basicPage.setEndTime(endTime);
                basicPage.displayText(displayTxt);
                basicPage.clicknext();

            } catch (Exception ex) {
                overallPass = false;
                System.out.println("Basic row " + j + " failed: " + ex.getMessage());
            }
        }
    }

    @And("I apply LED styles from led excel")
    public void i_apply_led_styles_from_led_excel() {
        try {
            ledData = Excelutils.getcelldatas(
                    TestDataConfigpaths.LED_EXCEL_PATH,
                    TestDataConfigpaths.LED_SHEET
            );
        } catch (Exception e) {
            Assert.fail("Failed to read LED excel: " + e.getMessage());
        }

        for (int j = 0; j < basicData.length; j++) {
            for (int i = 0; i < ledData.length; i++) {
                try {
                    String font      = ledData[i][0];
                    String fontSize  = ledData[i][1];
                    String animation = ledData[i][2];
                    String stayTime  = ledData[i][3];
                    String style     = ledData[i][4];
                    String program   = ledData[i][5];
                    String dimension = ledData[i][6];

                    ledStylesPage.fillLedStyles(font, fontSize, animation, stayTime, style, program);
                    ledStylesPage.clickNext();
                    ledStylesPage.preview(dimension);
                    ledStylesPage.clickSubmit();

                    String toast = ledStylesPage.getToastMessage();
                    boolean ok = toast != null && (toast.toLowerCase().contains("successfully")
                            ||  toast.toLowerCase().contains("saved"));
                    if (!ok) {
                        overallPass = false;
                        System.out.println("Save failed (basic row " + j + ", led row " + i + "): " + toast);
                    }
                } catch (Exception ex) {
                    overallPass = false;
                    System.out.println("LED row failed (basic row " + j + ", led row " + i + "): " + ex.getMessage());
                }
            }
        }
    }

    @Then("each campaign run should be successful")
    public void each_campaign_run_should_be_successful() {
        if (!overallPass) {
            Assert.fail("One or more campaign/style submissions failed. See console logs.");
        }
    }

    @Then("the total campaign count should increase")
    public void the_total_campaign_count_should_increase() {
        int afterTotal = countsPage.getTotalCampaignCount();
        int afterPages = pageCounts.getTotalPagesFromText();

        if (!(overallPass && afterTotal > beforeTotalCampaignCount)) {
            Assert.fail("Campaign count did not increase. Before=" + beforeTotalCampaignCount + ", After=" + afterTotal);
        }
    }
}
