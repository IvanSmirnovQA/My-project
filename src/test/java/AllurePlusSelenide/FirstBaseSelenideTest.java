package AllurePlusSelenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;

public class FirstBaseSelenideTest {

@BeforeEach
public void setUp() {

    WebDriverManager.chromedriver().setup(); //данная команда автоматически скачивает, устанавливает и настраивает ChromeDriver
    Configuration.browser = "chrome"; //Configuration - это "панель управления браузером", где мы можем задать размер окна, таймауты, браузер и другие настройки до запуска тестов.
    Configuration.browserSize = "1920x1080"; //С помощью Configuration установили размер окна
    SelenideLogger.addListener("AllureSelenide", new AllureSelenide()); //SelenideLogger - "дневник действий браузера", записывающий действия браузера
    //addListener - подключает "слушателя событий" - LogEventListener , который перехватывает все действия Selenide (click(), type(), should()) и отправляет их в нужный отчет (Allure, консоль, файл).
    SelenideLogger.addListener("Allure", new AllureSelenide());

}

}
