package ru.yurivan.selenium.stories.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yurivan.selenium.litecart.applogic.CommonAppLogic;
import ru.yurivan.selenium.litecart.test.BaseWebUITest;
import ru.yurivan.selenium.litecart.utils.SoftAssert;
import ru.yurivan.selenium.litecart.webdriver.Browser;

import java.util.List;

public class MainPage extends BaseWebUITest {
    private static final By PRODUCTS_LOCATOR_MOST_POPULAR = By.cssSelector("#box-most-popular ul[class*=products] li");
    private static final By PRODUCTS_LOCATOR_CAMPAIGNS = By.cssSelector("#box-campaigns ul[class*=products] li");
    private static final By PRODUCTS_LOCATOR_LATEST = By.cssSelector("#box-latest-products ul[class*=products] li");

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

        CommonAppLogic.openShopMainPage(browser);

        checkProductsSticker(PRODUCTS_LOCATOR_MOST_POPULAR, sa);
        checkProductsSticker(PRODUCTS_LOCATOR_CAMPAIGNS, sa);
        checkProductsSticker(PRODUCTS_LOCATOR_LATEST, sa);

        sa.assertAll();
    }

    @Test(description = "Check product page.")
    public void checkProductPage() {
        CommonAppLogic.openShopMainPage(browser);

        List<WebElement> campaignsProducts = browser.driver().findElements(PRODUCTS_LOCATOR_CAMPAIGNS);
        WebElement firstCampaignsProduct = campaignsProducts.get(0);

        final String productNameOnMainPage = firstCampaignsProduct.findElement(By.cssSelector("div.name")).getText();

        WebElement productRegularPriceOnMainPageElement =
                firstCampaignsProduct.findElement(By.cssSelector(".price-wrapper .regular-price"));
        final String productRegularPriceOnMainPage = productRegularPriceOnMainPageElement.getText().substring(1);
        final String productRegularPriceClassesOnMainPage = productRegularPriceOnMainPageElement.getAttribute("class");

        WebElement productCampaignPriceOnMainPageElement =
                firstCampaignsProduct.findElement(By.cssSelector(".price-wrapper .campaign-price"));
        final String productCampaignPriceOnMainPage = productCampaignPriceOnMainPageElement.getText().substring(1);
        final String productCampaignPriceClassesOnMainPage =
                productCampaignPriceOnMainPageElement.getAttribute("class");

        firstCampaignsProduct.click();
        browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(By.id("box-product")));

        WebElement productBox = browser.driver().findElement(By.id("box-product"));
        final String productNameOnProductPage = productBox.findElement(By.cssSelector("div h1.title")).getText();

        WebElement productRegularPriceOnProductPageElement =
                productBox.findElement(By.cssSelector(".information .price-wrapper .regular-price"));
        final String productRegularPriceOnProductPage = productRegularPriceOnProductPageElement.getText().substring(1);
        final String productRegularPriceClassesOnProductPage =
                productRegularPriceOnProductPageElement.getAttribute("class");

        WebElement productCampaignPriceOnProductPageElement =
                productBox.findElement(By.cssSelector(".information .price-wrapper .campaign-price"));
        final String productCampaignPriceOnProductPage =
                productCampaignPriceOnProductPageElement.getText().substring(1);
        final String productCampaignPriceClassesOnProductPage =
                productCampaignPriceOnProductPageElement.getAttribute("class");

        Assert.assertEquals(
                productNameOnProductPage,
                productNameOnMainPage,
                "Product name on product page is not the same as product name on shop main page." +
                        "\nActual: " + productNameOnProductPage +
                        "\nExpected: " + productNameOnMainPage);
        Assert.assertEquals(
                productRegularPriceOnProductPage,
                productRegularPriceOnMainPage,
                "Product regular price on product page is not the same as product regular price on shop main page." +
                        "\nActual: " + productRegularPriceOnProductPage +
                        "\nExpected: " + productRegularPriceOnMainPage);
        Assert.assertEquals(
                productCampaignPriceOnProductPage,
                productCampaignPriceOnMainPage,
                "Product campaign price on product page is not the same as campaign price on shop main page." +
                        "\nActual: " + productCampaignPriceOnProductPage +
                        "\nExpected: " + productCampaignPriceOnMainPage);

        // Вообще в этой проверке стилей нет необходимости, поскольку css-локаторы, с помощью которых мы берем одни
        // и те жи цены на разных страницах, итак указывают, что у одинаковых цен должен быть один и только один
        // определенный класс.
        Assert.assertEquals(
                productRegularPriceClassesOnProductPage,
                productRegularPriceClassesOnMainPage,
                "Product regular price classes on product page is not the same as" +
                " product regular price classes on shop main page." +
                "\nActual: " + productRegularPriceClassesOnProductPage +
                "\nExpected: " + productRegularPriceClassesOnMainPage);
        Assert.assertEquals(
                productCampaignPriceClassesOnProductPage,
                productCampaignPriceClassesOnMainPage,
                "Product campaign price classes on product page is not the same as" +
                " product campaign price classes on shop main page." +
                "\nActual: " + productCampaignPriceClassesOnProductPage +
                "\nExpected: " + productCampaignPriceClassesOnMainPage);
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
