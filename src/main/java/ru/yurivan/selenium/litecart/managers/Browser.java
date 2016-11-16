package ru.yurivan.selenium.litecart.managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Browser {
    private static WebDriver driver;
    private static WebDriverWait defaultWait;

    public static void start() {
        if (null == driver()) {
            driver = new ChromeDriver();
            defaultWait = new WebDriverWait(driver, 10);
        }
    }

    public static void quit() {
        if (null != driver()) {
            driver().quit();
            driver = null;
        }
    }

    public static WebDriver driver() {
        return driver;
    }

    public static WebDriverWait defaultWait() {
        return defaultWait;
    }
}

