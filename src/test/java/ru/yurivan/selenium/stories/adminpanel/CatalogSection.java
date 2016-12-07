package ru.yurivan.selenium.stories.adminpanel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yurivan.selenium.litecart.applogic.CommonAppLogic;
import ru.yurivan.selenium.litecart.managers.LiteCartSettingsManager;
import ru.yurivan.selenium.litecart.test.BaseWebUITest;
import ru.yurivan.selenium.litecart.utils.Generators;
import ru.yurivan.selenium.litecart.webdriver.Browser;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.StringJoiner;

public class CatalogSection extends BaseWebUITest {
    private static final By ADD_NEW_PRODUCT_PAGE_TABS_LOCATOR = By.cssSelector("#content .tabs .index");
    private static final By ADD_NEW_PRODUCT_PAGE_INFORMATION_TAB_LOCATOR = By.id("tab-information");
    private static final By ADD_NEW_PRODUCT_PAGE_PRICES_TAB_LOCATOR = By.id("tab-prices");

    private final Random random = new Random();

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

    @Test(description = "Add new product.")
    public void addNewProduct() throws URISyntaxException {
        CommonAppLogic.loginToAdminPanel(
                browser, settingsManager.getAdminPanelLogin(), settingsManager.getAdminPanelPassword());
        CommonAppLogic.openAdminPanelCatalogSection(browser);

        WebElement newProductButton =
                browser.driver().findElement(
                        By.xpath("//*[@id='content']//a[@class='button' and contains(text(), 'New Product')]"));
        newProductButton.click();

        browser.defaultWait().until(
                ExpectedConditions.visibilityOfElementLocated(ADD_NEW_PRODUCT_PAGE_TABS_LOCATOR));

        // Filling general tab.
        WebElement generalTab = browser.driver().findElement(By.id("tab-general"));

        WebElement enableStatusSwitch = generalTab.findElement(By.cssSelector("[name=status]"));
        enableStatusSwitch.click();

        final String productName = Generators.randomString("Name ", "", 32);
        generalTab
                .findElement(By.cssSelector("[name^=name]"))
                .sendKeys(productName);

        generalTab
                .findElement(By.cssSelector("[name=code]"))
                .sendKeys(Generators.randomString("", "", 16));

        WebElement firstProductGroupOption = generalTab.findElement(By.cssSelector("[name='product_groups[]']"));
        firstProductGroupOption.click();

        WebElement quantityField = generalTab.findElement(By.cssSelector("[name=quantity]"));
        quantityField.clear();
        quantityField.sendKeys(Integer.toString(random.nextInt(10) + 1));

        WebElement quantityUnitElement = generalTab.findElement(By.cssSelector("[name=quantity_unit_id]"));
        Select quantityUnitSelect = new Select(quantityUnitElement);
        quantityUnitSelect.selectByValue("1");

        WebElement deliveryStatusElement = generalTab.findElement(By.cssSelector("[name=delivery_status_id]"));
        Select deliveryStatusSelect = new Select(deliveryStatusElement);
        deliveryStatusSelect.selectByValue("1");

        WebElement soldOutStatusElement = generalTab.findElement(By.cssSelector("[name=sold_out_status_id]"));
        Select soldOutStatusSelect = new Select(soldOutStatusElement);
        soldOutStatusSelect.selectByValue("1");

        URI fileUri = getClass().getResource(settingsManager.getTestDataImageFolderPath() + "/1.jpg").toURI();
        final String imageFullPath = new File(fileUri).getAbsolutePath();

        generalTab
                .findElement(By.cssSelector("[name='new_images[]']"))
                .sendKeys(imageFullPath);

        generalTab
                .findElement(By.cssSelector("[name=date_valid_from]"))
                .sendKeys("12.12.1990");

        generalTab
                .findElement(By.cssSelector("[name=date_valid_to]"))
                .sendKeys("12.12.2100");

        // Filling Information tab.
        WebElement tabs = browser.driver().findElement(ADD_NEW_PRODUCT_PAGE_TABS_LOCATOR);
        WebElement informationTabLink = tabs.findElement(By.cssSelector("li:nth-child(2) a"));
        informationTabLink.click();
        browser.defaultWait().until(
                ExpectedConditions.visibilityOfElementLocated(ADD_NEW_PRODUCT_PAGE_INFORMATION_TAB_LOCATOR));

        WebElement informationTab = browser.driver().findElement(ADD_NEW_PRODUCT_PAGE_INFORMATION_TAB_LOCATOR);

        WebElement manufacturerElement = informationTab.findElement(By.cssSelector("[name=manufacturer_id]"));
        Select manufacturerSelect = new Select(manufacturerElement);
        manufacturerSelect.selectByValue("1");

        StringJoiner keywords = new StringJoiner(" ");
        keywords
                .add(Generators.randomString("Keyword", "", 16))
                .add(Generators.randomString("Keyword", "", 16))
                .add(Generators.randomString("Keyword", "", 16));
        browser.driver()
                .findElement(By.cssSelector("[name=keywords]"))
                .sendKeys(keywords.toString());

        browser.driver()
                .findElement(By.cssSelector("[name^=short_description]"))
                .sendKeys(Generators.randomString("Short description ", "", 32));

        browser.driver()
                .findElement(By.className("trumbowyg-editor"))
                .sendKeys(Generators.randomString("", "", 1024));

        browser.driver()
                .findElement(By.cssSelector("[name^=head_title]"))
                .sendKeys(Generators.randomString("Head Title ", "", 32));

        browser.driver()
                .findElement(By.cssSelector("[name^=meta_description]"))
                .sendKeys(Generators.randomString("Meta Description ", "", 32));

        // Filling Prices tab.
        tabs = browser.driver().findElement(ADD_NEW_PRODUCT_PAGE_TABS_LOCATOR);
        WebElement pricesTabLink = tabs.findElement(By.cssSelector("li:nth-child(4) a"));
        pricesTabLink.click();
        browser.defaultWait().until(
                ExpectedConditions.visibilityOfElementLocated(ADD_NEW_PRODUCT_PAGE_PRICES_TAB_LOCATOR));

        WebElement pricesTab = browser.driver().findElement(ADD_NEW_PRODUCT_PAGE_PRICES_TAB_LOCATOR);

        WebElement purchasePrice = pricesTab.findElement(By.cssSelector("[name=purchase_price]"));
        purchasePrice.clear();
        purchasePrice.sendKeys(Integer.toString(random.nextInt(10) + 1));

        WebElement purchasePriceCurrencyElement =
                pricesTab.findElement(By.cssSelector("[name=purchase_price_currency_code]"));
        Select purchasePriceCurrencySelect = new Select(purchasePriceCurrencyElement);
        purchasePriceCurrencySelect.selectByValue("USD");

        pricesTab
                .findElement(By.cssSelector("[name='prices[USD]']"))
                .sendKeys(Integer.toString(random.nextInt(10) + 1));

        pricesTab
                .findElement(By.cssSelector("[name='prices[EUR]']"))
                .sendKeys(Integer.toString(random.nextInt(10) + 1));

        // Save and check new product.
        WebElement saveButton = browser.driver().findElement(By.cssSelector("#content form [name=save]"));
        saveButton.click();
        browser.defaultWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[name=catalog_form]")));
        browser.defaultWait().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@name='catalog_form']//*[@class='row']//a[text()='" + productName + "']")));
    }
}
