package ru.yurivan.selenium.litecart.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface WebDriverFactory {
    WebDriver createDriver(DesiredCapabilities desiredCapabilities);
}
