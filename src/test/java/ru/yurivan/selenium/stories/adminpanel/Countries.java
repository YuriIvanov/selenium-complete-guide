package ru.yurivan.selenium.stories.adminpanel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yurivan.selenium.litecart.managers.LiteCartSettingsManager;
import ru.yurivan.selenium.litecart.test.BaseWebUITest;
import ru.yurivan.selenium.litecart.utils.CommonAppLogic;
import ru.yurivan.selenium.litecart.webdriver.Browser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Countries extends BaseWebUITest {
    private static final String ADMIN_PANEL_COUNTRIES_SECTION_URL =
            LiteCartSettingsManager.getInstance().getAdminPanelAddress() + "/?app=countries&doc=countries";

    private static final By countryCellsLocator =
            By.cssSelector("[name=countries_form] [class=dataTable] tr[class=row] td:nth-child(5) a");
    private static final By countriesWithGeoZonesLocator =
            By.xpath(
                    "//*[@name='countries_form']//*[@class='dataTable']//tr[@class='row']/td[6][text()!=0]/../td[5]/a");
    private static final By countryZoneNameCellsLocator =
            By.xpath("//*[@id='table-zones']//tbody/tr[not(@*)]/td[3][text()!='']");

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

    @Test(description = "Check countries sorting order and their zones sorting order.")
    public void checkCountriesSortingOrder() {
        CommonAppLogic.doLogin(browser, settingsManager.getAdminPanelLogin(), settingsManager.getAdminPanelPassword());

        browser.driver().get(ADMIN_PANEL_COUNTRIES_SECTION_URL);
        browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(By.id("sidebar")));

        List<WebElement> countryCells = browser.driver().findElements(countryCellsLocator);
        List<String> countriesNamesOriginal =
                countryCells.stream().map(WebElement::getText).collect(Collectors.toList());

        List<String> countriesNamesAlphabeticallySorted = new ArrayList<>(countriesNamesOriginal);
        Collections.sort(countriesNamesAlphabeticallySorted);

        // Check countries alphabetic order.
        Assert.assertEquals(
                countriesNamesOriginal,
                countriesNamesAlphabeticallySorted,
                "Countries are not alphabetically sorted.");

        List<WebElement> countriesWithZones = browser.driver().findElements(countriesWithGeoZonesLocator);
        for (int i = 0; i < countriesWithZones.size(); ++i) {
            checkCountryZonesOrder(countriesWithZones.get(i));

            // Refresh references.
            countriesWithZones = browser.driver().findElements(countriesWithGeoZonesLocator);
        }
    }

    private void checkCountryZonesOrder(WebElement country) {
        final String countryName = country.getText();
        country.click();
        browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(By.id("table-zones")));

        List<WebElement> countryZoneCells = browser.driver().findElements(countryZoneNameCellsLocator);
        List<String> countryZoneNamesOriginal =
                countryZoneCells.stream().map(WebElement::getText).collect(Collectors.toList());

        List<String> countryZoneNamesAlphabeticallySorted = new ArrayList<>(countryZoneNamesOriginal);
        Collections.sort(countryZoneNamesAlphabeticallySorted);

        // Check country zones alphabetic order.
        Assert.assertEquals(
                countryZoneNamesOriginal,
                countryZoneNamesAlphabeticallySorted,
                "Country " + countryName + " zones are not alphabetically sorted.");

        browser.driver().get(ADMIN_PANEL_COUNTRIES_SECTION_URL);
        browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(By.id("sidebar")));
    }
}
