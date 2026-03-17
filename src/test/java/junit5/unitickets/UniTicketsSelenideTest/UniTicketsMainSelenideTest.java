package junit5.unitickets.UniTicketsSelenideTest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class UniTicketsMainSelenideTest {

    private SelenideElement cityFromField = $x("//input[@placeholder='Откуда']");

    private ElementsCollection listOfCityFrom = $$x("//div[@class='origin field active']//div[@class='city']");

    private SelenideElement cityToField = $x("//input[@placeholder='Куда']");

    private ElementsCollection listOfCityTo = $$x("//div[@class='destination field active']//div[@class='city']");

    private SelenideElement dateForward = $x("//input[@placeholder='Туда']");

    private SelenideElement dateBack = $x("//input[@placeholder='Обратно']");

    private String dayOfCalendar = "//span[text()='%d']"; //Проинициализировали дату в календаре(Почему %d - изначально там стояла четкая дата, но данный способ мы говорим "тут можем быть любое число")

    private  SelenideElement searchButton = $x("//div[@class='search_btn']");


    public UniTicketsMainSelenideTest setCityFrom(String city){
        cityFromField.clear();
        cityFromField.sendKeys(city); //Данным методом указываем город откуда летим - Казань
        cityFromField.click();
        listOfCityFrom.find(Condition.partialText(city)).click();//Метод перебирает все элементы коллекции и ищет первый элемент, у которого текст содержит указанный фрагмент (в данном случае-текст).
        return this;
    }

    public UniTicketsMainSelenideTest setCityTo(String city) {
        cityToField.clear();
        cityToField.sendKeys(city); //Данным методом указываем город куда летим - Дубай
        cityToField.click();
        listOfCityTo.find(Condition.partialText(city));//Метод перебирает все элементы коллекции и ищет первый элемент, у которого текст содержит указанный фрагмент (в данном случае-текст).
        return this;
    }

    public  UniTicketsMainSelenideTest setDayForward(int day){
        dateForward.click();
        getDay(day).click(); //Данным методом кликаем по числу, которые мы выберем в тесте
        return this;
    }

    public UniTicketsMainSelenideTest setDayBack(int day){
        getDay(day).click();
        return this;
    }


    private SelenideElement getDay (int day) {
        return $x(String.format(dayOfCalendar, day)); //С помощью данного метода мы подставим в dayOfCalendar("//span[text()='%d']") вместо "%d" число, которое запишем в переменную "day"
    }

    public UniTicketsSearchSelenideTest search(){
        searchButton.click();
        return page (UniTicketsSearchSelenideTest.class);
    }



}
