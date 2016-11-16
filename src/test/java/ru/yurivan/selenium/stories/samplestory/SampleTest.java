package ru.yurivan.selenium.stories.samplestory;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yurivan.selenium.litecart.managers.Browser;
import ru.yurivan.selenium.litecart.test.BaseWebUITest;

public class SampleTest extends BaseWebUITest {
    @BeforeClass(dependsOnMethods = "setupBaseWebUITest")
    public void setup() {
        Browser.start();
    }

    @AfterClass(dependsOnMethods = "shutdownBaseWebUITest")
    public void shutdown() {
        Browser.quit();
    }

    @Test(description = "Sample test.")
    public void sampleTest() {
        Browser.driver().get("http://www.google.com");
        Browser.defaultWait().until(ExpectedConditions.titleContains("Google"));
    }
}
