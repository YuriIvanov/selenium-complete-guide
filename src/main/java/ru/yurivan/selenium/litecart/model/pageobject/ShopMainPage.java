package ru.yurivan.selenium.litecart.model.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yurivan.selenium.litecart.locator.Locators;
import ru.yurivan.selenium.litecart.managers.LiteCartSettingsManager;
import ru.yurivan.selenium.litecart.model.pageblock.IShopHeader;
import ru.yurivan.selenium.litecart.model.pageblock.ShopHeader;
import ru.yurivan.selenium.litecart.webdriver.Browser;

public class ShopMainPage implements IShopHeader {
    private static final By SHOP_MAIN_PAGE_WAIT_LOCATOR = By.id("box-logotypes");

    private Browser browser;

    public ShopMainPage(Browser browser) {
        this.browser = browser;
        this.browser.defaultWait().until(
                ExpectedConditions.presenceOfElementLocated(SHOP_MAIN_PAGE_WAIT_LOCATOR));
    }

    /**
     * Mimics situation when user opened shop main page through browser address bar/bookmarks/external site, etc.
     *
     * @param browser browser to open page.
     * @return shop main page.
     */
    public static ShopMainPage open(Browser browser) {
        browser.driver().get(LiteCartSettingsManager.getInstance().getBaseUrl());
        return new ShopMainPage(browser);
    }

    /**
     * Open specified product from most popular list.
     *
     * @param index zero-based index for most popular product list.
     * @return Page of chosen product.
     */
    public ShopProductPage openMostLatestProduct(final int index) {
        browser.driver()
                .findElements(Locators.SHOP_MAIN_PAGE_PRODUCTS_LOCATOR_LATEST)
                .get(index)
                .click();
        return new ShopProductPage(browser);
    }

    @Override
    public ShopHeader getShopHeader() {
        return new ShopHeader(browser);
    }
}
