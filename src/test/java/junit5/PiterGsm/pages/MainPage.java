package junit5.PiterGsm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage {

    private WebDriver driver;

    private final By searchField = By.xpath("//input[@placeholder='Поиск']"); //Проинициализировали кнопку поиска на странице

    private final By favorites = By.xpath("//a[@id='header__favorites-counter']"); //Кнопка "Избранное"

    private final By login = By.xpath("//button[@class='hcontrols__item js_popup_trigger isinit']"); //Кнопка "войти"

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public SearchResultPage searchItem(String item) { //Проинициализировали данный метод, чтобы вызывая только его вводить искомый товар в поиске
        driver.findElement(searchField).click();
        driver.findElement(searchField).sendKeys(item);
        driver.findElement(searchField).sendKeys(Keys.ENTER);
        return new SearchResultPage(driver);
    }
}
