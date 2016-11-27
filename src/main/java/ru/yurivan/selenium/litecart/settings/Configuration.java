package ru.yurivan.selenium.litecart.settings;

import ru.yurivan.selenium.litecart.model.BrowserType;

public class Configuration {
    private static final BrowserType BROWSER_TYPE;

    static {
        String browserType = System.getProperty("ru.yurivan.selenium.browserType", "chrome");
        switch (browserType.toLowerCase()) {
            case "chrome":
                BROWSER_TYPE = BrowserType.BROWSER_TYPE_CHROME;
                break;
            case "firefox":
                BROWSER_TYPE = BrowserType.BROWSER_TYPE_FIREFOX;
                break;
            case "ie":
                BROWSER_TYPE = BrowserType.BROWSER_TYPE_IE;
                break;
            default:
                BROWSER_TYPE = BrowserType.BROWSER_TYPE_CHROME;
        }
    }

    public static BrowserType getBrowserType() {
        return BROWSER_TYPE;
    }
}
