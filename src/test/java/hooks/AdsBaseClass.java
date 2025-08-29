package hooks;

import drivers.DriverManager;
import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import utils.Adsconfiguration;

import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class AdsBaseClass {

    public static String url;
    public static String username;
    public static String password;

    private static String readBrowserFromProps() throws Exception {
        Properties p = new Properties();
        p.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/global.properties"));
        return p.getProperty("Browser");
    }

    private static WebDriver newDriver(String br) {
        switch (br.toLowerCase()) {
            case "firefox": return new FirefoxDriver();
            case "edge":    return new EdgeDriver();
            default:        return new ChromeDriver();
        }
    }

    @BeforeAll
    public static void beforeAll() throws Exception {
        Adsconfiguration cfg = new Adsconfiguration();
        url = cfg.geturl();
        username = cfg.getusername();
        password = cfg.getpassword();

        WebDriver driver = newDriver(readBrowserFromProps());
        DriverManager.setDriver(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(url);
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        if (scenario.isFailed() && driver != null) {
            try {
                File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                byte[] bytes = FileUtils.readFileToByteArray(src);
                scenario.attach(bytes, "image/png", "Failed Step Screenshot");
            } catch (IOException ignored) {}
        }
    }

    @AfterAll
    public static void afterAll() {
        DriverManager.quitDriver();
    }
}
