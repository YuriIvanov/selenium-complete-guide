package ru.yurivan.selenium.litecart.applogic;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yurivan.selenium.litecart.managers.LiteCartSettingsManager;
import ru.yurivan.selenium.litecart.webdriver.Browser;

public class CommonAppLogic {
    private static final LiteCartSettingsManager settingsManager = LiteCartSettingsManager.getInstance();

    private static final String ADMIN_PANEL_COUNTRIES_SECTION_URL =
            settingsManager.getAdminPanelAddress() + "/?app=countries&doc=countries";
    private static final String ADMIN_PANEL_CATALOG_SECTION_URL =
            settingsManager.getAdminPanelAddress() + "?app=catalog&doc=catalog";
    private static final String ADMIN_PANEL_GEO_ZONES_SECTION_URL =
            settingsManager.getAdminPanelAddress() + "?app=geo_zones&doc=geo_zones";

    private static final By SHOP_MAIN_PAGE_WAIT_LOCATOR = By.id("box-logotypes");

    private static final By ADMIN_PANEL_COUNTRIES_SECTION_WAIT_LOCATOR = By.cssSelector("[name=countries_form]");
    private static final By ADMIN_PANEL_CATALOG_SECTION_WAIT_LOCATOR = By.cssSelector("[name=catalog_form]");
    private static final By ADMIN_PANEL_GEO_ZONES_SECTION_WAIT_LOCATOR = By.cssSelector("[name=geo_zones_form]");

    public static void loginToAdminPanel(Browser browser, String login, String password) {
        browser.driver().get(settingsManager.getAdminPanelAddress());
        browser.driver().manage().deleteAllCookies();
        browser.driver().navigate().refresh();
        browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(By.id("loader")));

        browser.driver().findElement(By.name("username")).sendKeys(login);
        browser.driver().findElement(By.name("password")).sendKeys(password);
        browser.driver().findElement(By.name("login")).click();

        browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(By.id("sidebar")));
    }

    public static void openShopMainPage(Browser browser) {
        browser.driver().get(settingsManager.getBaseUrl());
        browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(SHOP_MAIN_PAGE_WAIT_LOCATOR));
    }

    public static void openAdminPanelCountriesSection(Browser browser) {
        browser.driver().get(ADMIN_PANEL_COUNTRIES_SECTION_URL);
        browser.defaultWait().until(
                ExpectedConditions.presenceOfElementLocated(ADMIN_PANEL_COUNTRIES_SECTION_WAIT_LOCATOR));
    }

    public static void openAdminPanelCatalogSection(Browser browser) {
        browser.driver().get(ADMIN_PANEL_CATALOG_SECTION_URL);
        browser.defaultWait().until(
                ExpectedConditions.presenceOfElementLocated(ADMIN_PANEL_CATALOG_SECTION_WAIT_LOCATOR));
    }

    public static void openAdminPanelGeoZonesSection(Browser browser) {
        browser.driver().get(ADMIN_PANEL_GEO_ZONES_SECTION_URL);
        browser.defaultWait().until(
                ExpectedConditions.presenceOfElementLocated(ADMIN_PANEL_GEO_ZONES_SECTION_WAIT_LOCATOR));
    }
}
