package ru.yurivan.selenium.litecart.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverProvider {
    private static final WebDriverFactory webDriverFactory = new WebDriverFactoryDefaultImpl();

    public static WebDriver createWebDriver(DesiredCapabilities desiredCapabilities) {
        return webDriverFactory.createDriver(desiredCapabilities);
    }
}

