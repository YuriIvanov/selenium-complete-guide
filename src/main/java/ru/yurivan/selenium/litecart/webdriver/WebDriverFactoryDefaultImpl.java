package ru.yurivan.selenium.litecart.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.yurivan.selenium.litecart.settings.Configuration;

public class WebDriverFactoryDefaultImpl implements WebDriverFactory {
    @Override
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {
        if (null == desiredCapabilities) {
            desiredCapabilities = new DesiredCapabilities();
        }

        switch (Configuration.getBrowserType()) {
            case BROWSER_TYPE_CHROME:
                return new ChromeDriver(desiredCapabilities);
            case BROWSER_TYPE_FIREFOX:
                return new FirefoxDriver(desiredCapabilities);
            case BROWSER_TYPE_IE:
                return new InternetExplorerDriver(desiredCapabilities);
            default:
                return new ChromeDriver(desiredCapabilities);
        }
    }
}
