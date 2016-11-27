package ru.yurivan.selenium.litecart.model;

public enum BrowserType {
    BROWSER_TYPE_CHROME("chrome"),
    BROWSER_TYPE_FIREFOX("firefox"),
    BROWSER_TYPE_IE("ie");

    private final String browserName;

    BrowserType(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserName() {
        return browserName;
    }
}
