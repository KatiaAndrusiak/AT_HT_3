package com.miamato.pageobject.amazon;


import com.miamato.PropertyManager;
import com.miamato.pageobject.BasePage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(HomePage.class.getSimpleName());
    private static final String HOME_PAGE_URL = PropertyManager.getProperty("homepage.url");
    private static final String PAGE_TITLE = PropertyManager.getProperty("homepage.title");

    @FindBy(xpath = "//input[@id='twotabsearchtextbox']")
    public static WebElement SEARCH_FIELD;

    @FindBy(xpath = "//input[@id='nav-search-submit-button']")
    public static WebElement SEARCH_BUTTON;

    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @Step("Open application home page")
    public HomePage open(){
        logger.info("Trying to open application home page");
        openWebPage(HOME_PAGE_URL, logger);
        checkPageTitle(PAGE_TITLE, logger);
        acceptCookiesIfPopupPresent(logger);
        return this;
    }

    @Step("Search for a product with name: {gameTitle}")
    public HomePage searchByProductTitle(String gameTitle){
        logger.info("Performing search for product with title: " + gameTitle);
        enterTextIntoField(SEARCH_FIELD, gameTitle, logger);
        SEARCH_BUTTON.click();
        return this;
    }

}
