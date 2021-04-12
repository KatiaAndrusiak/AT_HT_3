package com.miamato;

import com.miamato.listeners.TestReporter;
import com.miamato.listeners.TestResultsListener;

import com.miamato.pageobject.amazon.BasketPage;
import com.miamato.pageobject.amazon.ChangeCurrencyPage;
import com.miamato.pageobject.amazon.HomePage;

import com.miamato.pageobject.amazon.SearchResultsPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@Listeners({TestResultsListener.class, TestReporter.class})
public abstract class BaseTest {

    protected WebDriver driver;
    public static final Logger assertLogger = LogManager.getLogger("Assert");

    protected HomePage homePage = null;
    protected SearchResultsPage searchResultsPage = null;
    protected BasketPage basketPage = null;
    protected ChangeCurrencyPage changeCurrencyPage = null;

    protected DriverManager driverManager = null;

    @Parameters("browserName")
    @BeforeClass
    public void setup(@Optional("Chrome") String browserName, ITestContext context){
        driverManager = new DriverManager();
        driver = driverManager.getDriver(browserName);
        context.setAttribute("WebDriver", driver);
        //driver.manage().window().maximize();

        homePage = new HomePage(driver);
        searchResultsPage = new SearchResultsPage(driver);
        basketPage = new BasketPage(driver);
        changeCurrencyPage = new ChangeCurrencyPage(driver);
    }

    @AfterClass
    public void cleanUp(){
        driver.quit();
    }
}
