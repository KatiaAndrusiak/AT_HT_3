package com.miamato.test;

import com.miamato.BaseTest;
import com.miamato.PropertyManager;
import com.miamato.pageobject.amazon.BasketPage;
import com.miamato.pageobject.amazon.ChangeCurrencyPage;
import com.miamato.pageobject.amazon.SearchResultsPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.List;


public class AmazonBasicTests extends BaseTest {

    private static final String SEARCH_TERM_CHILDREN_BOOKS = PropertyManager.getProperty("search.term.children.books");
    private static final String SEARCH_TERM_BOOKS_DEPARTMENT = PropertyManager.getProperty("search.books.department");
    private static final String SEARCH_TERM_BOOKS_AUTHOR = PropertyManager.getProperty("search.term.by.author.king");
    private static final String SEARCH_TERM_BOOKS_GIFT = PropertyManager.getProperty("search.term.romantic.book");


    @DataProvider(name = "search-term-set")
    public Object[][] searchTerms() {
        return new Object[][]
                {{SEARCH_TERM_CHILDREN_BOOKS, SEARCH_TERM_BOOKS_DEPARTMENT}
                        ,{SEARCH_TERM_BOOKS_AUTHOR, SEARCH_TERM_BOOKS_DEPARTMENT}
                        ,{SEARCH_TERM_BOOKS_GIFT, SEARCH_TERM_BOOKS_DEPARTMENT}};
    }


    @Test(dataProvider = "search-term-set")
    public void basicAmazonProductSearch(String searchTerm, String expectedDepartmentName){
        homePage.open()
                .searchByProductTitle(searchTerm);
        assertThatItemIsPresentInField(SearchResultsPage.SEARCH_RESULTS_DEPARTMENTS_IN_LEFT_MENU.get(SearchResultsPage.TARGET_DEPARTMENT_INDEX),expectedDepartmentName);
    }


    @Test
    public void addProductToBasket(){
        homePage.open()
                .searchByProductTitle(SEARCH_TERM_CHILDREN_BOOKS);
        basketPage.openSelectedProductItem().addProductToBasket();
        assertThatItemInBasket(BasketPage.BASKET_TOTAL_QUANTITY, BasketPage.TARGET_BASKET_QUANTITY_1);
    }

    @Test
    public void addProductToBasketAndRemove(){
        homePage.open()
                .searchByProductTitle(SEARCH_TERM_CHILDREN_BOOKS);
        basketPage.openSelectedProductItem().addProductToBasket();
        basketPage.openBasket().removeProductFromBasket();
        assertThatItemInBasket(BasketPage.BASKET_TOTAL_QUANTITY, BasketPage.TARGET_BASKET_QUANTITY_0);
    }

    @Test
    public void changeCurrency(){
        homePage.open();
        changeCurrencyPage
                .openChangeCurrencyPage()
                .selectCurrency()
                .saveSelectedCurrency();
        assertThatCurrencyIsChanged(ChangeCurrencyPage.CHECKING_IF_CURRENCY_IS_CHANGED);
    }


    @Step("Check if expected item: {expectedText} is available in field")
    public static void assertThatItemIsPresentInField(WebElement element, String expectedText) {
        assertLogger.info("Checking availability of search item in field");
        Assert.assertEquals(element.getText(), expectedText);
    }

    @Step("Check if item is added to basket")
    public static void assertThatItemInBasket(WebElement element, String expectedText) {
        assertLogger.info("Checking if item is added to basket and quantity equals "+expectedText);
        Assert.assertEquals(element.getText(), expectedText);
    }

    @Step("Check if currency is changed")
    public static void assertThatCurrencyIsChanged(List<WebElement> elements) {
        assertLogger.info("Checking if currency is changed");
        Assert.assertFalse(elements.isEmpty());
    }

}
