package junit5.unitickets;

import junit5.PiterGsm.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UniTicketsMainPage extends BasePage {

    private By cityFromField = By.xpath("//input[@placeholder='Откуда']");

    private By listOfCityFrom = By.xpath("//div[@class='origin field active']//div[@class='city']");

    private By cityToField = By.xpath("//input[@placeholder='Куда']");

    private By listOfCityTo = By.xpath("//div[@class='destination field active']//div[@class='city']");

    private By dateForward = By.xpath("//input[@placeholder='Туда']");

    private By dateBack = By.xpath("//input[@placeholder='Обратно']");

    private String dayOfCalendar = "//span[text()='%d']"; //Проинициализировали дату в календаре(Почему %d - изначально там стояла четкая дата, но данный способ мы говорим "тут можем быть любое число")

   private  By searchButton = By.xpath("//div[@class='search_btn']");

    public UniTicketsMainPage(WebDriver driver) {
        super(driver); //super = ссылка на родительский класс. Это нужно, когда дочерний класс наследует поведение, но хочет использовать или изменить то, что уже есть у родителя.
        wait.until(ExpectedConditions.presenceOfElementLocated(cityFromField)); //Данным методом проверяем, появился ли элемент в DOM страницы, чтобы начать с ним работать
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));//Создали ожидания кликабельности кнопки поиска
    }

    public UniTicketsMainPage setCityFrom(String city){
        driver.findElement(cityFromField).clear();
        driver.findElement(cityFromField).sendKeys(city); //Данным методом указываем город откуда летим - Казань
        driver.findElement(cityFromField).click();
        waitForTextPresentedInList(listOfCityFrom, city).click(); //Данным методом кликаем по нужному нам городу "city" из всего выпавшего списка "listOfCityFrom"
        return this;
    }

    public UniTicketsMainPage setCityTo(String city) {
        driver.findElement(cityToField).clear();
        driver.findElement(cityToField).sendKeys(city); //Данным методом указываем город куда летим - Дубай
        driver.findElement(cityToField).click();
        waitForTextPresentedInList(listOfCityTo, city).click(); //Данным методом кликаем по нужному нам городу "city" из всего выпавшего списка "listOfCityTo"
        return this;
    }

    public  UniTicketsMainPage setDayForward(int day){
        driver.findElement(dateForward).click();
        getDay(day).click(); //Данным методом кликаем по числу, которые мы выберем в тесте
        return this;
    }

    public UniTicketsMainPage setDayBack(int day){
        driver.findElement(dateBack).click();
        getDay(day).click();
        return this;
    }


    private WebElement getDay (int day) {
        By dayLocator = By.xpath(String.format(dayOfCalendar, day)); //С помощью данного метода мы подставим в dayOfCalendar("//span[text()='%d']") вместо "%d" число, которое запишем в переменную "day"
        return driver.findElement(dayLocator);
    }

    public void search(){
        driver.findElement(searchButton).click();
    }
}
