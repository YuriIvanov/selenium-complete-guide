package ru.yurivan.selenium.stories.login;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yurivan.selenium.litecart.managers.Browser;
import ru.yurivan.selenium.litecart.managers.LiteCartSettingsManager;
import ru.yurivan.selenium.litecart.test.BaseWebUITest;

public class TestLogin extends BaseWebUITest {
    private static final String ADMIN_PANEL_ADDRESS = LiteCartSettingsManager.getInstance().getBaseUrl() + "/admin/";

    private LiteCartSettingsManager settingsManager;

    @BeforeClass(dependsOnMethods = "setupBaseWebUITest")
    public void setup() {
        settingsManager = LiteCartSettingsManager.getInstance();
        Browser.start();
    }

    @AfterClass(dependsOnMethods = "shutdownBaseWebUITest")
    public void shutdown() {
        Browser.quit();
    }

    @Test(description = "Simple login test.")
    public void doLogin() {
        Browser.driver().get(ADMIN_PANEL_ADDRESS);
        Browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(By.id("loader")));

        Browser.driver().findElement(By.name("username")).sendKeys(settingsManager.getAdminPanelLogin());
        Browser.driver().findElement(By.name("password")).sendKeys(settingsManager.getAdminPanelPassword());
        Browser.driver().findElement(By.name("login")).click();

        Browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(By.id("sidebar")));
    }
}

