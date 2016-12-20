package ru.yurivan.selenium.stories.other;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yurivan.selenium.litecart.applogic.CommonAppLogic;
import ru.yurivan.selenium.litecart.locator.Locators;
import ru.yurivan.selenium.litecart.managers.LiteCartSettingsManager;
import ru.yurivan.selenium.litecart.test.BaseWebUITest;
import ru.yurivan.selenium.litecart.utils.SoftAssert;
import ru.yurivan.selenium.litecart.webdriver.Browser;

import java.util.List;
import java.util.logging.Level;

public class BrowserLogs extends BaseWebUITest {
    private static final String FIRST_CATALOG_CATEGORY_URL =
            "http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1";

    private static final By PRODUCTS_LOCATOR =
            By.xpath("//*[@name='catalog_form']//*[@class='dataTable']//*[@class='row']" +
                     "//*[contains(@href, 'product_id') and not(@title)]");

    private LiteCartSettingsManager settingsManager;
    private Browser browser;

    @BeforeClass(dependsOnMethods = "setupBaseWebUITest")
    public void setup() {
        settingsManager = LiteCartSettingsManager.getInstance();

        browser = new Browser();

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);

        browser.start(desiredCapabilities);
    }

    @AfterClass(dependsOnMethods = "shutdownBaseWebUITest")
    public void shutdown() {
        browser.quit();
    }

    @Test(description = "Check browser log messages.")
    public void checkBrowserLogs() {
        SoftAssert sa = new SoftAssert("Check browser log messages");

        CommonAppLogic.loginToAdminPanel(
                browser, settingsManager.getAdminPanelLogin(), settingsManager.getAdminPanelPassword());

        browser.driver().get(FIRST_CATALOG_CATEGORY_URL);
        browser.defaultWait().until(
                ExpectedConditions.presenceOfElementLocated(Locators.ADMIN_PANEL_CATALOG_SECTION_WAIT_LOCATOR));

        List<WebElement> products = browser.driver().findElements(PRODUCTS_LOCATOR);
        for (int i = 0; i < products.size(); ++i) {
            WebElement product = products.get(i);
            product.click();
            browser.defaultWait().until(
                    ExpectedConditions.visibilityOfElementLocated(
                            Locators.ADD_NEW_OR_EDIT_EXISTING_PRODUCT_PAGE_TABS_LOCATOR));

            List<LogEntry> logs = browser.driver().manage().logs().get("browser").getAll();
            sa.assertTrue(
                    logs.isEmpty(),
                    "Browser log have some messages: " + logs + ".");

            browser.driver().get(FIRST_CATALOG_CATEGORY_URL);
            browser.defaultWait().until(
                    ExpectedConditions.presenceOfElementLocated(Locators.ADMIN_PANEL_CATALOG_SECTION_WAIT_LOCATOR));
            products = browser.driver().findElements(PRODUCTS_LOCATOR);
        }

        sa.assertAll();
    }
}
