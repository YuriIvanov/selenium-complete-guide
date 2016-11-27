package ru.yurivan.selenium.litecart.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Browser {
    private WebDriver driver;
    private WebDriverWait defaultWait;

    public void start(DesiredCapabilities desiredCapabilities) {
        if (null == driver) {
            driver = WebDriverProvider.createWebDriver(desiredCapabilities);
            defaultWait = new WebDriverWait(driver, 10);
        }
    }

    public void quit() {
        if (null != driver) {
            driver.quit();
            driver = null;
            defaultWait = null;
        }
    }

    public WebDriver driver() {
        ensureBrowserRunning();
        return driver;
    }

    public WebDriverWait defaultWait() {
        ensureBrowserRunning();
        return defaultWait;
    }

    private void ensureBrowserRunning() {
        if (null == driver) {
            throw new IllegalStateException("Unable to complete operation. You should start browser first.");
        }
    }
}

