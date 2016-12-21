package ru.yurivan.selenium.litecart.model.pageblock;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yurivan.selenium.litecart.model.pageobject.ShopMainPage;
import ru.yurivan.selenium.litecart.webdriver.Browser;

public class ShopHeader {
    private Browser browser;

    public ShopHeader(Browser browser) {
        this.browser = browser;
        this.browser.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("header")));
    }

    public CartWidget getCart() {
        return new CartWidget(browser);
    }

    public ShopMainPage openMainPage() {
        browser.driver().findElement(By.id("logotype-wrapper")).click();
        return new ShopMainPage(browser);
    }
}
