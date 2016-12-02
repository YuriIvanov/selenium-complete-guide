package ru.yurivan.selenium.stories.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yurivan.selenium.litecart.managers.LiteCartSettingsManager;
import ru.yurivan.selenium.litecart.test.BaseWebUITest;
import ru.yurivan.selenium.litecart.utils.SoftAssert;
import ru.yurivan.selenium.litecart.webdriver.Browser;

import java.util.List;

public class MainPage extends BaseWebUITest {
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

    @Test(description = "Check products sticker presence.")
    public void productsSticker() {
        SoftAssert sa = new SoftAssert("Check products sticker presence.");

        browser.driver().get(LiteCartSettingsManager.getInstance().getBaseUrl());
        browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(By.id("logotype-wrapper")));

        checkProductsSticker(By.cssSelector("#box-most-popular ul[class*=products] li"), sa);
        checkProductsSticker(By.cssSelector("#box-campaigns ul[class*=products] li"), sa);
        checkProductsSticker(By.cssSelector("#box-latest-products ul[class*=products] li"), sa);

        sa.assertAll();
    }

    @Test(description = "Check product page.")
    public void checkProductPage() {

    }

    private void checkProductsSticker(By productsLocator, SoftAssert sa) {
        List<WebElement> products = browser.driver().findElements(productsLocator);
        for (WebElement product : products) {
            List<WebElement> stickers = product.findElements(By.cssSelector("div[class*=sticker]"));
            sa.assertEquals(
                    stickers.size(),
                    1,
                    "Incorrect number of sticker for product: " + product.getText() + "." +
                    "\nActual: " + stickers.size() + "." +
                    "\nExpected: " + 1 + ".");
        }
    }
}
