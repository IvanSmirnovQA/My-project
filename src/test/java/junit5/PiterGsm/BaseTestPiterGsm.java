package junit5.PiterGsm;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class BaseTestPiterGsm { //Создали данный класс согласно паттерну PageObject

    protected WebDriver driver;

    @BeforeAll
    public static void downloadDriver() {
        WebDriverManager.chromedriver().setup(); //Проинициализировали драйвера
    }


    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080)); //Указали размеры для открытия браузера
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30)); //Инициализировали загрузку страницы (до 20 сек.)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30)); //Инициализировали загрузку ВСЕХ элементов для взаимодействия с ними( до 20 сек)
        driver.get("https://pitergsm.ru");
    }

    @AfterEach
    public void tearDown() {
        //driver.close();//Закроет и браузер и процесс работы
        //driver.quit()//Только закроет браузер
    }

}
