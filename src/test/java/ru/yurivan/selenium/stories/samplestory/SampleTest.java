package ru.yurivan.selenium.stories.samplestory;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yurivan.selenium.litecart.webdriver.Browser;
import ru.yurivan.selenium.litecart.test.BaseWebUITest;

public class SampleTest extends BaseWebUITest {
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

    @Test(description = "Sample test.")
    public void sampleTest() {
        browser.driver().get("http://www.google.com");
        browser.defaultWait().until(ExpectedConditions.titleContains("Google"));
    }
}
