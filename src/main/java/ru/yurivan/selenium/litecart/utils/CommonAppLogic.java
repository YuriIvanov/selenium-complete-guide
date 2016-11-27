package ru.yurivan.selenium.litecart.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yurivan.selenium.litecart.managers.LiteCartSettingsManager;
import ru.yurivan.selenium.litecart.webdriver.Browser;

public class CommonAppLogic {
    public static void doLogin(Browser browser, String login, String password) {
        browser.driver().get(LiteCartSettingsManager.getInstance().getAdminPanelAddress());
        browser.driver().manage().deleteAllCookies();
        browser.driver().navigate().refresh();
        browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(By.id("loader")));

        browser.driver().findElement(By.name("username")).sendKeys(login);
        browser.driver().findElement(By.name("password")).sendKeys(password);
        browser.driver().findElement(By.name("login")).click();

        browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(By.id("sidebar")));
    }
}
