package com.miamato.pageobject;

import java.util.List;

import com.miamato.LogUtil;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v89.page.Page;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public abstract class BasePage extends Page {

    protected WebDriver driver;

    @FindBy(xpath = "//input[@id='sp-cc-accept']")
    protected static WebElement ACCEPT_COOKIES_BUTTON;

    protected void checkPageTitle(String title, Logger logger){
        logger.info("Verifying if page title is equal to: " + title);
        Assert.assertEquals(driver.getTitle(), title);
        logger.info("Page title is correct: \"" + title + "\"");
    }

    protected void pressKey(Keys key, Logger logger){
        logger.info("Pressing key: " + key.name());
        Actions actions = new Actions(driver);
        actions.sendKeys(key).perform();
    }
    protected void openWebPage(String url, Logger logger){
        logger.info("Navigating to website with url:   " + url);
        driver.navigate().to(url);
    }

    protected void enterTextIntoField(WebElement element, String text, Logger logger){
        logger.info("Entering text: \"" + text + "\" into field: " + element);
        Actions actions = new Actions(driver);
        actions.sendKeys(element, text).perform();
    }

    protected void clickOnElement(WebElement element, Logger logger){
        logger.info("Clicking on element: " + element);
        Actions actions = new Actions(driver);
        actions.click(element).perform();
    }

    protected void clickOnElement(List<WebElement> elements, int position, Logger logger){
        logger.info("Clicking on element: " + elements.get(position-1));
        Actions actions = new Actions(driver);
        actions.click(elements.get(position-1)).perform();
    }

    protected void selectFromDropdownByValue(WebElement element, String value, Logger logger){
        logger.info("Trying to select option: \"" + value + "\" from dropdown: " + element);
        Select dropdown = new Select(element);
        try {
            dropdown.selectByValue(value);
        } catch (Exception e) {
            logger.error("Option cannot be selected from dropdown");
            throw e;
        }
    }


    protected static void acceptCookiesIfPopupPresent(Logger logger){
        try{
            logger.info(BasePage.class.getName() + " Accept cookies");

           ACCEPT_COOKIES_BUTTON.click();
        } catch(Exception e) {
            logger.info(BasePage.class.getName() + " Cookie accept pop-up is not displayed");
            LogUtil.logStackTrace(e, logger);
        }
    }

}
