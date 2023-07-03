package tests;

import org.testng.Assert;
import org.testng.annotations.*;
import java.io.InputStream;
import java.util.Properties;

/**
 * this class represents the test runner
 *
 * @author Shlomi
 */

public class MainRunnerTest extends BaseTest {

    private String siteURL, secondSiteURL;

    @BeforeMethod
    public void beforeMethod() {
        try {
            // load properties
            Properties props = new Properties();

            String propFileName = "config.properties";
            // get the config properties file
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);
            siteURL = props.getProperty("siteURL");
            secondSiteURL = props.getProperty("secondSite");
        } catch (Exception e) {
            System.out.println("There was problem load the properties file");
        }
    }

    @Test(priority = 1, description = "language test")
    public void printLanguageTest() {
        Assert.assertTrue(mainPage.getWebSite(siteURL), "site was not loaded");
        Assert.assertTrue(mainPage.clickOnLanguageOption(), "open language option was not performed");
        System.out.println("The Languages are: ");
        mainPage.getLanguages().forEach(System.out::println);
        Assert.assertTrue(mainPage.clickOnLanguageOption(), "close language option was not performed");
    }

    @Test(priority = 2, description = "language select test")
    public void languageSelectTest() {
        Assert.assertTrue(mainPage.getWebSite(siteURL), "site was not loaded");
        mainPage.clickOnLanguages();
    }

}
