package utilities;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * this class represents the main functions of all pages
 *
 * @author Shlomi
 */


public abstract class BasePageFunctions {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // constructor
    public BasePageFunctions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // get webDriver
    public WebDriver getDriver() {
        return this.driver;
    }

    // navigate to URL
    public Boolean navigateToURL(String URL) {
        try {
            getDriver().navigate().to(URL);
            return true;
        } catch (Exception e) {
            System.out.println("Site was not loaded");
            return false;
        }
    }

    // get back webElement
    public WebElement getWebElement(By elem) {
        return getDriver().findElement(elem);
    }

    public static WebElement getElementByText(WebDriver driver, By locator, String expectedText, int... timeout) {
        int localTimeout = (timeout.length > 0) ? timeout[0] : 30;
        try {
            if (!waitForNumberOfElementsToBeMoreThan(driver, locator, 0, localTimeout)) {
                System.out.println("Failed to find element matching passed locator");
                return null;
            }
            Uninterruptibles.sleepUninterruptibly(800, TimeUnit.MILLISECONDS);
            List<WebElement> elements = waitForPresenceOfAllElementsLocatedBy(driver, locator, localTimeout);
            for (WebElement element : elements) {
                String text = element.getText();
                if (text.toLowerCase().contains(expectedText.toLowerCase())) {
                    return element;
                }
            }

        } catch (Exception e) {
            System.out.println("Could not find element containing " + expectedText + " text" + e.getMessage());
        }
        return null;
    }

    public boolean clickElementByText(By locator, String expectedText) {
        try {
            WebElement element = getElementByText(getDriver(), locator, expectedText);
            return waitForElementToBeClickableAndClickIt(element);
        } catch (Exception e) {
            String error = "Could not find element containing " + expectedText + " text and click it" + e.getMessage();
            return false;
        }
    }

    public static boolean waitForNumberOfElementsToBeMoreThan(WebDriver driver, By locator, int expectedAmount, int timeout, By... parentLocator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            if (parentLocator.length == 0) {
                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, expectedAmount));
                return true;
            } else {
                WebElement parentElement = driver.findElement(parentLocator[0]);
                for (int i = 0; i < timeout * 2; i++) {
                    try {
                        if (parentElement.findElements(locator).size() > expectedAmount) {
                            return true;
                        } else {
                            Uninterruptibles.sleepUninterruptibly(500, TimeUnit.MILLISECONDS);
                        }
                    } catch (Exception e) {
                        Uninterruptibles.sleepUninterruptibly(500, TimeUnit.MILLISECONDS);
                    }
                }
                return false;
            }
        } catch (Exception e) {
            System.out.println("Could not find the expected element more than " + expectedAmount + " times on the page " + e.getMessage());
            return false;
        }
    }

    public static List<WebElement> waitForPresenceOfAllElementsLocatedBy(WebDriver driver, By locator, int timeout, By... parentLocator) {
        try {
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            if (parentLocator.length == 0) {
                return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            } else {
                return wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(parentLocator[0], locator));
            }
        } catch (Exception e) {
            System.out.println("Failed to find presence of all nested elements" + e.getMessage());
            return null;
        }
    }

    // click on element
    public Boolean clickOnElement(By elem) {
        try {
            getWebElement(elem).click();
            return true;
        } catch (Exception e) {
            System.out.println("Element " + elem + " was not clicked");
            return false;
        }
    }

    // element to be clickable and click it
    public Boolean waitForElementToBeClickableAndClickIt(WebElement elem) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(elem)).click();
            return true;
        } catch (Exception e) {
            System.out.println("Wait for element to be clickable was not worked correct");
            return false;
        }
    }

    // wait for element to be visible
    public Boolean waitForElementToBeVisible(By elem) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(elem));
            return true;
        } catch (Exception e) {
            System.out.println("Wait for element to be visible was not worked correct");
            return false;
        }
    }

    // get text from element
    public String getTextFromElement(By elem) {
        return getWebElement(elem).getText();
    }

    // get attribute from element
    public String getElementAttribute(WebElement elem, String attribute) {
        return elem.getAttribute(attribute);
    }

    // get text of elements
    public List<String> getTextFromElements(By elem) {
        List<WebElement> elementList = getDriver().findElements(elem);
        List<String> stringList = new ArrayList<>();
        for (WebElement element : elementList) {
            stringList.add(element.getText());
        }
        return stringList;
    }

    // get list of elements
    public List<WebElement> getElementsList(By elem) {
        return getDriver().findElements(elem);
    }

}
