package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.annotations.*;
import pages.MainPage;

/**
 * this class represents the base of all tests
 * this will be before each test in the testNG xml
 *
 * @author Shlomi
 */

public class BaseTest implements ITestListener {

    WebDriver driver;
    MainPage mainPage;

    @Parameters({"browser"})
    @BeforeTest(alwaysRun = true)
    public void setup(String browser, ITestContext context) {

        driver = new ChromeDriver();

        // maximize the browser window
        driver.manage().window().maximize();

        // load main page
        mainPage = new MainPage(driver);
        // load table page

        context.setAttribute("driver", driver);

    }

    @AfterTest(alwaysRun = true)
    public void close() {
        driver.quit();

    }

}