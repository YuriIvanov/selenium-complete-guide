package ru.yurivan.selenium.stories.adminpanel;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yurivan.selenium.litecart.utils.CommonAppLogic;
import ru.yurivan.selenium.litecart.webdriver.Browser;
import ru.yurivan.selenium.litecart.managers.LiteCartSettingsManager;
import ru.yurivan.selenium.litecart.test.BaseWebUITest;

public class TestLogin extends BaseWebUITest {
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

    @Test(description = "Simple login test.")
    public void doLogin() {
        CommonAppLogic.doLogin(
                browser, settingsManager.getAdminPanelLogin(), settingsManager.getAdminPanelPassword());
    }
}

