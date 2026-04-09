package junit5.selenoid;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@BrowserType(browser = BrowserType.Browser.CHROME, isRemote = true) //В аннотации указали браузер для использования и значение true в remote (означает что тест будет запущен локально)
public class SelenoidChromeTests {

    @BeforeAll
    public static void init() {
        Configuration.remote = "http://localhost:4444/wd/hub"; //Configuration — это класс с настройками Selenide, .remote используется для указания удаленного Selenium Grid/Selenoid сервера, куда нужно отправлять команды на запуск браузера.
        Configuration.browser = "chrome"; //Проинициализировали браузер для использования //Конфигурация браузера задаётся единожды
    }


    @Test
    @BrowserType(browser = BrowserType.Browser.CHROME, isRemote = true) //В аннотации указали браузер для использования и значение true в remote (означает что тест будет запущен локально)
    public void selenoidTest(){
        Selenide.open("https://vk.com");
    }

    @Test
    public void selenoidFirefoxTest() {
        Configuration.browser = "firefox"; //проинициализировали браузер
        Selenide.open("https://vk.com");
    }
}
