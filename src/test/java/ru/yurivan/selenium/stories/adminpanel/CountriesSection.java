package ru.yurivan.selenium.stories.adminpanel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
import ru.yurivan.selenium.litecart.webdriver.expectedconditions.CustomExpectedConditions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CountriesSection extends BaseWebUITest {
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
        CommonAppLogic.loginToAdminPanel(
                browser, settingsManager.getAdminPanelLogin(), settingsManager.getAdminPanelPassword());
        CommonAppLogic.openAdminPanelCountriesSection(browser);

        List<WebElement> countryCells = browser.driver().findElements(Locators.COUNTRY_CELLS_LOCATOR);
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

        List<WebElement> countriesWithZones = browser.driver().findElements(Locators.COUNTRIES_WITH_GEO_ZONES_LOCATOR);
        for (int i = 0; i < countriesWithZones.size(); ++i) {
            checkCountryZonesOrder(countriesWithZones.get(i));

            // Refresh references.
            countriesWithZones = browser.driver().findElements(Locators.COUNTRIES_WITH_GEO_ZONES_LOCATOR);
        }
    }

    private void checkCountryZonesOrder(WebElement country) {
        final String countryName = country.getText();
        country.click();
        browser.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(Locators.COUNTRY_PAGE_WAIT_LOCATOR));

        List<WebElement> countryZoneCells = browser.driver().findElements(Locators.COUNTRY_ZONE_NAME_CELLS_LOCATOR);
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

    @Test(description = "Check external link on some country page.")
    public void countryExternalLinks() {
        CommonAppLogic.loginToAdminPanel(
                browser, settingsManager.getAdminPanelLogin(), settingsManager.getAdminPanelPassword());
        CommonAppLogic.openAdminPanelCountriesSection(browser);

        List<WebElement> countryCells = browser.driver().findElements(Locators.COUNTRY_CELLS_LOCATOR);
        countryCells.get(0).click();
        browser.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(Locators.COUNTRY_PAGE_WAIT_LOCATOR));

        List<WebElement> externalLinks =
                browser.driver().findElements(
                        By.cssSelector("#content form table:not(#table-zones) .fa-external-link"));
        for (WebElement externalLink : externalLinks) {
            String originalWindow = browser.driver().getWindowHandle();
            final Set<String> existingWindows = browser.driver().getWindowHandles();

            externalLink.click();
            String newWindow =
                    browser.defaultWait().until(CustomExpectedConditions.anyWindowOtherThan(existingWindows));

            browser.driver().switchTo().window(newWindow);
            browser.driver().close();
            browser.driver().switchTo().window(originalWindow);
        }
    }
}
