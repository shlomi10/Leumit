package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.BasePageFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represents the main page
 *
 * @author Shlomi
 */

public class MainPage extends BasePageFunctions {

    // constructor
    public MainPage(WebDriver driver) {
        super(driver);
    }

    By languageBTN = By.cssSelector(".main-nav-wrapper-buttons .current-language");
    By languagesOption = By.cssSelector(".lang-switch-dropdown.header-only-mobile.show .languageLink a");
    By hrefLang = By.cssSelector(".lang-switch-dropdown.header-only-mobile.show .languageLink a[href]");
    By languageDropDown = By.cssSelector(".lang-switch-dropdown.header-only-mobile.show");
    By languageIcon = By.xpath("//div[@class='main-nav-wrapper-buttons']//span[@class='current-language']");
    List<String> languages = new ArrayList<>();
    List<String> fullLanguages = new ArrayList<>();
    List<String> actualLanguages = new ArrayList<>();

    // navigate to the site
    public Boolean getWebSite(String site) {
        return navigateToURL(site);
    }

    // click language change
    public Boolean clickOnLanguageOption() {
        return clickOnElement(languageBTN);
    }

    // get text languages
    public List<String> getLanguages() {
        waitForElementToBeVisible(languagesOption);
        return getTextFromElements(languagesOption);
    }

    // click on each language
    public boolean clickOnLanguages() {
        clickOnLanguageOption();
        List<WebElement> webElements = getElementsList(languagesOption);
        for (WebElement element : webElements) {
            fullLanguages.add(getElementAttribute(element, "text"));
            languages.add(getElementAttribute(element, "href").split("//")[1].split("\\.")[0]);
        }
        clickOnLanguageOption();
        for (String language : fullLanguages) {
            clickOnLanguageOption();
            waitForElementToBeVisible(languageDropDown);
            clickElementByText(hrefLang, language);
            actualLanguages.add(getTextFromElement(languageIcon));
        }

        return languages.containsAll(actualLanguages);
    }
}
