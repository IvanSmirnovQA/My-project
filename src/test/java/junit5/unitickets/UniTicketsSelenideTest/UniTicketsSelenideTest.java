package junit5.unitickets.UniTicketsSelenideTest;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

public class UniTicketsSelenideTest {

    @Test
    public void firstSelenideTest(){

        Selenide.open("https://uniticket.ru/"); //Данный метод автоматически инициализирует WebDriver и открывает страницу
        UniTicketsSearchSelenideTest mainPage = new UniTicketsSearchSelenideTest(); //Проинициализировали объект(страницу сайта) чтобы использовать созданные методы для работы на сайте
    }
}
