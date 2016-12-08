package ru.yurivan.selenium.litecart.utils;

import org.openqa.selenium.By;
import ru.yurivan.selenium.litecart.webdriver.Browser;

public class FindUtils {
    public static boolean isElementPresent(Browser browser, By locator) {
        return browser.driver().findElements(locator).size() > 0;
    }
}
