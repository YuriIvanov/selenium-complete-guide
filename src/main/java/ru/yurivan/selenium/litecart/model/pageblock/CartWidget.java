package ru.yurivan.selenium.litecart.model.pageblock;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yurivan.selenium.litecart.locator.Locators;
import ru.yurivan.selenium.litecart.model.pageobject.ShopCartPage;
import ru.yurivan.selenium.litecart.webdriver.Browser;

public class CartWidget {
    private Browser browser;

    public CartWidget(Browser browser) {
        this.browser = browser;
        this.browser.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("cart")));
    }

    public int getNumberOfItems() {
        return Integer.valueOf(browser.driver().findElement(Locators.CART_NUMBER_OF_ITEMS_LOCATOR).getText());
    }

    /**
     * Waits until number of products in cart become as specified.
     *
     * @param expectedNumberOfItems expected number of cart items.
     */
    public void waitForNumberOfItemsPresent(final int expectedNumberOfItems) {
        browser.defaultWait().until(
                ExpectedConditions.textToBe(
                        Locators.CART_NUMBER_OF_ITEMS_LOCATOR,
                        String.valueOf(expectedNumberOfItems)));
    }

    /**
     * Start checkout process.
     *
     * @return shop cart page.
     */
    public ShopCartPage checkout() {
        browser.driver()
                .findElement(Locators.CART_CHECKOUT_LINK_LOCATOR)
                .click();
        return new ShopCartPage(browser);
    }
}
