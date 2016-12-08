package ru.yurivan.selenium.stories.shop;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yurivan.selenium.litecart.applogic.CommonAppLogic;
import ru.yurivan.selenium.litecart.locator.Locators;
import ru.yurivan.selenium.litecart.test.BaseWebUITest;
import ru.yurivan.selenium.litecart.utils.FindUtils;
import ru.yurivan.selenium.litecart.webdriver.Browser;

public class Cart extends BaseWebUITest {
    private Browser browser;

    @BeforeClass(dependsOnMethods = "setupBaseWebUITest")
    public void setup() {
        browser = new Browser();
        browser.start(null);
    }

    @AfterClass(dependsOnMethods = "shutdownBaseWebUITest")
    public void shutdown() {
        browser.quit();
    }

    @Test(description = "Add and remove products from cart")
    public void addRemoveProducts() {
        final int numberOfProductsToAdd = 3;
        int currentNumberOfProductsInCart = 0;

        CommonAppLogic.openShopMainPage(browser);
        for (int i = 0; i < numberOfProductsToAdd; ++i) {
            browser.driver()
                    .findElements(Locators.SHOP_MAIN_PAGE_PRODUCTS_LOCATOR_MOST_POPULAR)
                    .get(i)
                    .click();
            browser.defaultWait().until(
                    ExpectedConditions.visibilityOfElementLocated(Locators.PRODUCT_PAGE_WAIT_LOCATOR));

            if (FindUtils.isElementPresent(browser, Locators.PRODUCT_PAGE_PRODUCT_SIZE_CHOOSER)) {
                WebElement productSizeChooserElement =
                        browser.driver().findElement(Locators.PRODUCT_PAGE_PRODUCT_SIZE_CHOOSER);
                Select productSizeChooser = new Select(productSizeChooserElement);
                productSizeChooser.selectByValue("Small");
            }

            browser.driver()
                    .findElement(Locators.PRODUCT_PAGE_ADD_TO_CART_BUTTON_LOCATOR)
                    .click();

            browser.defaultWait().until(
                    ExpectedConditions.textToBe(
                            Locators.CART_NUMBER_OF_ITEMS_LOCATOR,
                            String.valueOf(currentNumberOfProductsInCart + 1)));
            ++currentNumberOfProductsInCart;

            CommonAppLogic.openShopMainPage(browser);
        }

        browser.driver()
                .findElement(Locators.CART_CHECKOUT_LINK_LOCATOR)
                .click();
        browser.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(Locators.CART_PAGE_WAIT_LOCATOR));

        for (int i = 0; i < numberOfProductsToAdd; ++i) {
            ((JavascriptExecutor) browser.driver())
                    .executeScript(
                            "document.querySelector(arguments[0]).click()",
                            "#box-checkout-cart [name=cart_form] [name=remove_cart_item]");

            if (FindUtils.isElementPresent(browser, Locators.CART_PAGE_ORDER_SUMMARY_TABLE_LOCATOR)) {
                browser.defaultWait()
                        .until(ExpectedConditions.numberOfElementsToBe(
                                Locators.CART_PAGE_ORDER_SUMMARY_TABLE_ITEMS_LOCATOR,
                                currentNumberOfProductsInCart - 1));
                --currentNumberOfProductsInCart;
            }
        }
    }
}
