package ru.yurivan.selenium.litecart.webdriver.expectedconditions;

import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.Set;

public class CustomExpectedConditions {
    public static ExpectedCondition<String> anyWindowOtherThan(final Set<String> existingWindows) {
        return driver -> {
            Set<String> allWindows = driver.getWindowHandles();
            allWindows.removeAll(existingWindows);
            return allWindows.isEmpty() ? null : allWindows.iterator().next();
        };
    }
}
