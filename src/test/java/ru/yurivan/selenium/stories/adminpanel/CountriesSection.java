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
import ru.yurivan.selenium.litecart.applogic.CommonAppLogic;
import ru.yurivan.selenium.litecart.utils.StringUtils;
import ru.yurivan.selenium.litecart.webdriver.Browser;

import java.util.List;
import java.util.stream.Collectors;

public class CountriesSection extends BaseWebUITest {
    private static final By COUNTRY_CELLS_LOCATOR =
            By.cssSelector("[name=countries_form] .dataTable tr.row td:nth-child(5) a");
    private static final By COUNTRIES_WITH_GEO_ZONES_LOCATOR =
            By.xpath(
                    "//*[@name='countries_form']//*[@class='dataTable']//tr[@class='row']/td[6][text()!=0]/../td[5]/a");
    private static final By COUNTRY_ZONE_NAME_CELLS_LOCATOR =
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
        CommonAppLogic.loginToAdminPanel(browser, settingsManager.getAdminPanelLogin(), settingsManager.getAdminPanelPassword());
        CommonAppLogic.openAdminPanelCountriesSection(browser);

        List<WebElement> countryCells = browser.driver().findElements(COUNTRY_CELLS_LOCATOR);
        List<String> countriesNamesOriginal =
                countryCells
                        .stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList());

        List<String> countriesNamesAlphabeticallySorted =
                StringUtils.makeAlphabeticallySortedStringListCopy(countriesNamesOriginal);

        // Check countries alphabetic order.
        Assert.assertEquals(
                countriesNamesOriginal,
                countriesNamesAlphabeticallySorted,
                "Countries are not alphabetically sorted.");

        List<WebElement> countriesWithZones = browser.driver().findElements(COUNTRIES_WITH_GEO_ZONES_LOCATOR);
        for (int i = 0; i < countriesWithZones.size(); ++i) {
            checkCountryZonesOrder(countriesWithZones.get(i));

            // Refresh references.
            countriesWithZones = browser.driver().findElements(COUNTRIES_WITH_GEO_ZONES_LOCATOR);
        }
    }

    private void checkCountryZonesOrder(WebElement country) {
        final String countryName = country.getText();
        country.click();
        browser.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("table-zones")));

        List<WebElement> countryZoneCells = browser.driver().findElements(COUNTRY_ZONE_NAME_CELLS_LOCATOR);
        List<String> countryZoneNamesOriginal =
                countryZoneCells
                        .stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList());
        List<String> countryZoneNamesAlphabeticallySorted =
                StringUtils.makeAlphabeticallySortedStringListCopy(countryZoneNamesOriginal);

        // Check country zones alphabetic order.
        Assert.assertEquals(
                countryZoneNamesOriginal,
                countryZoneNamesAlphabeticallySorted,
                "Country " + countryName + " zones are not alphabetically sorted.");

        CommonAppLogic.openAdminPanelCountriesSection(browser);
    }
}
