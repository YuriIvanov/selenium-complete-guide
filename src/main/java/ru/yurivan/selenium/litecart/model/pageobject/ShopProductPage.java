package ru.yurivan.selenium.litecart.model.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import ru.yurivan.selenium.litecart.locator.Locators;
import ru.yurivan.selenium.litecart.model.pageblock.CartWidget;
import ru.yurivan.selenium.litecart.model.pageblock.IShopHeader;
import ru.yurivan.selenium.litecart.model.pageblock.ShopHeader;
import ru.yurivan.selenium.litecart.utils.FindUtils;
import ru.yurivan.selenium.litecart.webdriver.Browser;

public class ShopProductPage implements IShopHeader {
    private Browser browser;

    public ShopProductPage(Browser browser) {
        this.browser = browser;
        this.browser.defaultWait().until(
                ExpectedConditions.visibilityOfElementLocated(Locators.PRODUCT_PAGE_WAIT_LOCATOR));
    }

    public boolean isSizeChooserPresent() {
        return FindUtils.isElementPresent(browser, Locators.PRODUCT_PAGE_PRODUCT_SIZE_CHOOSER);
    }

    public void chooseProductSize(final String size) {
        WebElement productSizeChooserElement =
                browser.driver().findElement(Locators.PRODUCT_PAGE_PRODUCT_SIZE_CHOOSER);
        Select productSizeChooser = new Select(productSizeChooserElement);
        productSizeChooser.selectByValue(size);
    }

    public void addThisProductToCart() {
        CartWidget cartWidget = getShopHeader().getCart();
        final int numberOfItemsBefore = cartWidget.getNumberOfItems();

        browser.driver()
                .findElement(Locators.PRODUCT_PAGE_ADD_TO_CART_BUTTON_LOCATOR)
                .click();

        cartWidget.waitForNumberOfItemsPresent(numberOfItemsBefore + 1);
    }

    @Override
    public ShopHeader getShopHeader() {
        return new ShopHeader(browser);
    }
}
