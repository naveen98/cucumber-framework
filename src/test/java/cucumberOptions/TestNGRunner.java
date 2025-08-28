package cucumberOptions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = { "E:\\projects\\raj-projects\\apollo-Ads\\src\\test\\java\\features"
                //"E:\\projects\\raj-projects\\apollo-Ads\\src\\test\\java\\features\\campaigncreaton.feature",
               },
        glue = {"stepDefinitions","hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true,dryRun = false,tags = "@smoke or @regression"

)
public class TestNGRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
