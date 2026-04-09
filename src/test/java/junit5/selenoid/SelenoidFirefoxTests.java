package junit5.selenoid;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SelenoidFirefoxTests {

    @BeforeAll
    public static void init(){
        com.codeborne.selenide.Configuration.remote = "http://localhost:4444/wd/hub"; //Configuration — это класс с настройками Selenide, .remote используется для указания удаленного Selenium Grid/Selenoid сервера, куда нужно отправлять команды на запуск браузера.
        Configuration.browser = "firefox"; //Проинициализировали браузер для использования //Конфигурация браузера задаётся единожды
    }

    @Test
    public void firefoxTest(){


    }


}
