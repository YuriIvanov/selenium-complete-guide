package ru.yurivan.selenium.stories.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yurivan.selenium.litecart.applogic.CommonAppLogic;
import ru.yurivan.selenium.litecart.test.BaseWebUITest;
import ru.yurivan.selenium.litecart.utils.Generators;
import ru.yurivan.selenium.litecart.webdriver.Browser;

import java.util.Random;

public class Customer extends BaseWebUITest {
    private static final By SHOP_MAIN_PAGE_LOGOUT_LINK_LOCATOR = By.cssSelector("#box-account ul li:nth-child(4) a");
    private static final By SHOP_MAIN_PAGE_LOGIN_LINK_BUTTON =
            By.cssSelector("#box-account-login [name=login_form] table [name=login]");

    private static final Random random = new Random();

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

    @Test(description = "Register new user.")
    public void registerNewUser() {
        CommonAppLogic.openShopMainPage(browser);

        browser.driver()
                .findElement(
                        By.cssSelector("#box-account-login [name=login_form] table tr:nth-child(5) a"))
                .click();
        browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(By.id("create-account")));

        WebElement createAccountTable =
                browser.driver().findElement(
                        By.cssSelector("#create-account [name=customer_form] table tbody"));

        createAccountTable
                .findElement(By.cssSelector("[name=tax_id]"))
                .sendKeys(Generators.randomString("Tax ID ", "", 32));

        createAccountTable
                .findElement(By.cssSelector("[name=company]"))
                .sendKeys(Generators.randomString("Company ", "", 32));

        createAccountTable
                .findElement(By.cssSelector("[name=firstname]"))
                .sendKeys(Generators.randomString("First name ", "", 32));

        createAccountTable
                .findElement(By.cssSelector("[name=lastname]"))
                .sendKeys(Generators.randomString("Last name ", "", 32));

        createAccountTable
                .findElement(By.cssSelector("[name=address1]"))
                .sendKeys(Generators.randomString("Address1 ", "", 32));

        createAccountTable
                .findElement(By.cssSelector("[name=address2]"))
                .sendKeys(Generators.randomString("Address 2 ", "", 32));

        createAccountTable
                .findElement(By.cssSelector("[name=postcode]"))
                .sendKeys(Integer.toString(random.nextInt(1000000) + 111111));

        createAccountTable
                .findElement(By.cssSelector("[name=city]"))
                .sendKeys(Generators.randomString("City ", "", 32));

        WebElement countryElement = browser.driver().findElement(By.cssSelector("[name=country_code]"));
        Select countrySelect = new Select(countryElement);
        countrySelect.selectByVisibleText("Russian Federation");

        final String email = Generators.randomString("", "@mail.com", 64);
        createAccountTable
                .findElement(By.cssSelector("[name=email]"))
                .sendKeys(email);

        createAccountTable
                .findElement(By.cssSelector("[name=phone]"))
                .sendKeys(Integer.toString(random.nextInt(123456) + 123456));

        final String password = Generators.randomString("", "");
        createAccountTable
                .findElement(By.cssSelector("[name=password]"))
                .sendKeys(password);

        createAccountTable
                .findElement(By.cssSelector("[name=confirmed_password]"))
                .sendKeys(password);

        WebElement createAccountButton = createAccountTable.findElement(By.cssSelector("[name=create_account]"));
        createAccountButton.click();
        browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(SHOP_MAIN_PAGE_LOGOUT_LINK_LOCATOR));

        WebElement logoutButton = browser.driver().findElement(SHOP_MAIN_PAGE_LOGOUT_LINK_LOCATOR);
        logoutButton.click();
        browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(SHOP_MAIN_PAGE_LOGIN_LINK_BUTTON));

        WebElement loginForm =
                browser.driver().findElement(By.cssSelector("#box-account-login [name=login_form] table"));
        loginForm.findElement(By.cssSelector("[name=email]")).sendKeys(email);
        loginForm.findElement(By.cssSelector("[name=password]")).sendKeys(password);

        WebElement loginButton = loginForm.findElement(By.cssSelector("[name=login]"));
        loginButton.click();
        browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(SHOP_MAIN_PAGE_LOGOUT_LINK_LOCATOR));

        logoutButton = browser.driver().findElement(SHOP_MAIN_PAGE_LOGOUT_LINK_LOCATOR);
        logoutButton.click();
        browser.defaultWait().until(ExpectedConditions.presenceOfElementLocated(SHOP_MAIN_PAGE_LOGIN_LINK_BUTTON));
    }
}
