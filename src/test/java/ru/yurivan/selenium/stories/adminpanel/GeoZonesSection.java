package ru.yurivan.selenium.stories.adminpanel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yurivan.selenium.litecart.locator.Locators;
import ru.yurivan.selenium.litecart.managers.LiteCartSettingsManager;
import ru.yurivan.selenium.litecart.test.BaseWebUITest;
import ru.yurivan.selenium.litecart.applogic.CommonAppLogic;
import ru.yurivan.selenium.litecart.utils.StringUtils;
import ru.yurivan.selenium.litecart.webdriver.Browser;

import java.util.List;
import java.util.stream.Collectors;

public class GeoZonesSection extends BaseWebUITest {
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

    @Test(description = "Check geo zones sorting order.")
    public void checkGeoZonesSortingOrder() {
        CommonAppLogic.loginToAdminPanel(browser, settingsManager.getAdminPanelLogin(), settingsManager.getAdminPanelPassword());
        CommonAppLogic.openAdminPanelGeoZonesSection(browser);

        List<WebElement> countries = browser.driver().findElements(Locators.GEO_ZONES_COUNTRY_NAME_CELLS_LOCATORS);
        for (int i = 0; i < countries.size(); ++i) {
            WebElement country = countries.get(i);

            verifyCountryGeoZonesSortingOrder(country);

            // Refresh references.
            countries = browser.driver().findElements(Locators.GEO_ZONES_COUNTRY_NAME_CELLS_LOCATORS);
        }
    }

    private void verifyCountryGeoZonesSortingOrder(WebElement country) {
        final String countryName = country.getText();

        country.click();
        browser.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("table-zones")));

        List<WebElement> zoneSelectElements = browser.driver().findElements(Locators.ZONE_NAME_SELECTS_LOCATOR);
        List<String> zoneNamesOriginal =
                zoneSelectElements
                        .stream()
                        .map(webElement -> new Select(webElement).getFirstSelectedOption().getText())
                        .collect(Collectors.toList());
        List<String> zoneNamesAlphabeticallySorted =
                StringUtils.makeAlphabeticallySortedStringListCopy(zoneNamesOriginal);

        // Check countries alphabetic order.
        Assert.assertEquals(
                zoneNamesOriginal,
                zoneNamesAlphabeticallySorted,
                "Zone names for country " + countryName + " are not alphabetically sorted.");

        CommonAppLogic.openAdminPanelGeoZonesSection(browser);
    }
}
