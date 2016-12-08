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
    private static final By TOP_SECTIONS_LOCATOR = By.cssSelector("ul#box-apps-menu li#app-");
    private static final By SUBSECTIONS_LOCATOR = By.cssSelector("ul.docs li");

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
        CommonAppLogic.loginToAdminPanel(
                browser, settingsManager.getAdminPanelLogin(), settingsManager.getAdminPanelPassword());

        // Walk through sections.
        List<WebElement> sections = browser.driver().findElements(TOP_SECTIONS_LOCATOR);
        for (int i = 0; i < sections.size(); ++i) {
            WebElement section = sections.get(i);

            section.click();
            checkHeaderPresence();

            // Refresh current section.
            section = browser.driver().findElements(TOP_SECTIONS_LOCATOR).get(i);

            // Check if menu has subsections.
            List<WebElement> subsections = section.findElements(SUBSECTIONS_LOCATOR);
            if (!subsections.isEmpty()) {
                // Walk though subsections.
                for (int j = 0; j < subsections.size(); ++j) {
                    WebElement subsection = subsections.get(j);

                    subsection.click();
                    checkHeaderPresence();

                    // Refresh current subsection.
                    section = browser.driver().findElements(TOP_SECTIONS_LOCATOR).get(i);
                    subsections = section.findElements(SUBSECTIONS_LOCATOR);
                }
            }

            // Refresh web elements to prevent StaleElementReferenceException.
            sections = browser.driver().findElements(TOP_SECTIONS_LOCATOR);
        }
    }

    private void checkHeaderPresence() {
        browser.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(HEADER_LOCATOR));
    }
}
