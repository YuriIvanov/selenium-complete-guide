package ru.yurivan.selenium.stories.adminpanel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yurivan.selenium.litecart.managers.LiteCartSettingsManager;
import ru.yurivan.selenium.litecart.test.BaseWebUITest;
import ru.yurivan.selenium.litecart.applogic.CommonAppLogic;
import ru.yurivan.selenium.litecart.webdriver.Browser;

import java.util.List;

public class AdminPanelSections extends BaseWebUITest {
    private static final By HEADER_LOCATOR = By.cssSelector("td#content h1");
    private static final By topSectionsLocator = By.cssSelector("ul#box-apps-menu li#app-");
    private static final By subsectionsLocator = By.cssSelector("ul.docs li");

    private LiteCartSettingsManager settingsManager;
    private Browser browser;

    @BeforeClass(dependsOnMethods = "setupBaseWebUITest")
    public void setup() {
        settingsManager = LiteCartSettingsManager.getInstance();
        browser = new Browser();
        browser.start(null);
    }

    @AfterClass(dependsOnMethods = "shutdownBaseWebUITest")
    public void shutdown() {
        browser.quit();
    }

    @Test(description = "Walk through all admin panel sections and subsections.")
    public void walkThroughAdminPanelSections() {
        CommonAppLogic.doLogin(
                browser, settingsManager.getAdminPanelLogin(), settingsManager.getAdminPanelPassword());

        // Walk through sections.
        List<WebElement> sections = browser.driver().findElements(topSectionsLocator);
        for (int i = 0; i < sections.size(); ++i) {
            WebElement section = sections.get(i);

            WebElement header = null;
            if (i != 0) {
                header = browser.driver().findElement(HEADER_LOCATOR);
            }

            section.click();
            checkHeaderPresence(header);

            // Refresh current section.
            section = browser.driver().findElements(topSectionsLocator).get(i);

            // Check if menu has subsections.
            List<WebElement> subsections = section.findElements(subsectionsLocator);
            if (!subsections.isEmpty()) {
                // Walk though subsections.
                for (int j = 0; j < subsections.size(); ++j) {
                    WebElement subsection = subsections.get(j);

                    header = browser.driver().findElement(HEADER_LOCATOR);
                    subsection.click();
                    checkHeaderPresence(header);

                    // Refresh current subsection.
                    section = browser.driver().findElements(topSectionsLocator).get(i);
                    subsections = section.findElements(subsectionsLocator);
                }
            }

            // Refresh web elements to prevent StaleElementReferenceException.
            sections = browser.driver().findElements(topSectionsLocator);
        }
    }

    private void checkHeaderPresence(WebElement headerBeforePageReload) {
        if (null != headerBeforePageReload) {
            browser.defaultWait().until(ExpectedConditions.stalenessOf(headerBeforePageReload));
        }
        browser.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(HEADER_LOCATOR));
    }
}
