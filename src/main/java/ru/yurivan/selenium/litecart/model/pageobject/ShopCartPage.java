package ru.yurivan.selenium.litecart.model.pageobject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yurivan.selenium.litecart.locator.Locators;
import ru.yurivan.selenium.litecart.utils.FindUtils;
import ru.yurivan.selenium.litecart.webdriver.Browser;

public class ShopCartPage {
    private Browser browser;

    public ShopCartPage(Browser browser) {
        this.browser = browser;
        this.browser.defaultWait().until(
                ExpectedConditions.visibilityOfElementLocated(Locators.CART_PAGE_WAIT_LOCATOR));
    }

    /**
     * Remove currently selected product.
     */
    public void removeCurrentProduct() {
        final int numberOfProductsBefore =
                browser.driver().findElements(Locators.CART_PAGE_ORDER_SUMMARY_TABLE_ITEMS_LOCATOR).size();

        ((JavascriptExecutor) browser.driver())
                .executeScript(
                        "document.querySelector(arguments[0]).click()",
                        "#box-checkout-cart [name=cart_form] [name=remove_cart_item]");

        if (FindUtils.isElementPresent(browser, Locators.CART_PAGE_ORDER_SUMMARY_TABLE_LOCATOR)) {
            browser.defaultWait()
                    .until(ExpectedConditions.numberOfElementsToBe(
                            Locators.CART_PAGE_ORDER_SUMMARY_TABLE_ITEMS_LOCATOR,
                            numberOfProductsBefore - 1));
        }
    }
}
