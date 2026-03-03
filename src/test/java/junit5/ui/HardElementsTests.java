package junit5.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class HardElementsTests {

    private WebDriver driver;


    @BeforeEach
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

    }

    @AfterEach
    public void tearDown() {
        //driver.close();
    }

    @Test
    public void basicAuth() {
        //driver.get("https://the-internet.herokuapp.com/basic_auth");
        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth"); //В данной ссылке сразу указаны данные для авторизации "admin:admin@", чтобы нас перекидывало на страницу сразу с автор-ей
        String h3 = driver.findElement(By.xpath("//h3")).getText(); //Данный метод получает текст (а именно "Basic Auth" указано на стр) из заголовка "//h3"
        Assertions.assertEquals("Basic Auth", h3);
    }

    @Test
    public void allertOk() {
        String expectedText = "I am a JS Alert"; //Указали ожидаемый текст, который будет в Alert
        String expectedTextResult = "You successfully clicked an alert";
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.findElement(By.xpath("//button[@onclick='jsAlert()']"));
        String actualText = driver.switchTo().alert().getText();
        //driver.switchTo().alert().sendKeys("admin"); //Метод .switchTo().alert() переключает на Alert, после чего мы сможем ввести данные
        driver.switchTo().alert().accept();
        String result = driver.findElement(By.id("result")).getText();
        Assertions.assertEquals(expectedText, actualText);
        Assertions.assertEquals(expectedTextResult, result);


    }


}
