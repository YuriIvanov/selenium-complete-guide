package ru.yurivan.selenium.stories.samplestory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SampleTest {
    private WebDriver webDriver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        webDriver = new ChromeDriver();
        wait = new WebDriverWait(webDriver, 10);
    }

    @AfterClass
    public void shutdown() {
        if (webDriver != null) {
            webDriver.close();
        }
    }

    @Test(description = "Sample test.")
    public void sampleTest() {
        webDriver.get("http://www.google.com");
        wait.until(ExpectedConditions.titleContains("Google"));
    }
}
