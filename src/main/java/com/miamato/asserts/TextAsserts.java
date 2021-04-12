package com.miamato.asserts;

import com.miamato.actions.Elements;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class TextAsserts {


    public static void assertThatTextIsPresentInField(WebElement element, String expectedText, WebDriver driver, Logger logger) {
        logger.info("Checking if expected text: \"" + expectedText
            + "\" is equal to text in element:  " + element);
        Assert.assertEquals(Elements.waitForElemnentToBeVisible(element, driver, logger).getText(), expectedText);
        logger.info("Text is equal to expected");
    }
}
