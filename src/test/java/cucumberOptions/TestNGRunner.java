package cucumberOptions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {
                "src/test/java/features"  // your feature files location
        },
        glue = {"stepDefinitions", "hooks"},  // package(s) for step definitions and hooks
        plugin = {
                "pretty",                                   // readable console output
                "html:target/cucumber-report.html",        // HTML report
                "json:target/cucumber.json",               // JSON report
                "junit:target/cucumber-reports/Cucumber.xml", // JUnit XML for Jenkins
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" // Extent report
        },
        monochrome = true,  // makes console output readable
        dryRun = false,     // set true to check mapping without executing
        tags = "@smoke"     // run only scenarios with this tag
)
public class TestNGRunner extends AbstractTestNGCucumberTests {

    // Override scenarios() to control parallel execution
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
