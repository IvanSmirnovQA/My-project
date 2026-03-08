package junit5.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

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

    @Test
    public void iframeTest() {
        driver.get("https://mail.ru/");
        driver.findElement(By.xpath("//a[@class='resplash-btn resplash-btn_primary bboqein__1ebh38x']")).click();
    }

    @Test
    public void sliderTest(){
        driver.get("http://85.192.34.140:8081/");
        WebElement elements = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elements.click();
        WebElement widgets = driver.findElement(By.xpath("//div[@class='header-wrapper']//div[text()='Widgets']"));
        widgets.click();
        WebElement sliderClick = driver.findElement(By.xpath("//span[text()='Slider']"));
        sliderClick.click();
        WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));

//        Actions actions = new Actions(driver);//Проинициализировали класс Actions
//        actions.dragAndDropBy(slider,50, 0).build().perform(); //dragAndDropBy позволяет перетащить элемент на опр-ые координаты (1-ым аргументом указываем элемент, 2-ым и 3-им координаты - оси х и у)

//Создали данный цикл, чтобы была прокрутка слайдера до 85
        int expectedValue = 85;
        int currentValue =  Integer.parseInt(slider.getAttribute("value")); //С помощью Integer.parseInt получим значение слайдера (Integer.parseInt написаны чтобы запарсить полученные данные от слайдера их получают в строке)
        int valueToMove = expectedValue - currentValue; //Написали данную строку чтобы она показывала как двигается слайдер

        for (int i = 0; i < valueToMove; i++) {
            slider.sendKeys(Keys.ARROW_RIGHT); //Реализовали цикл fori,чтобы посмотреть как двигается слайдер
            WebElement sliderValue = driver.findElement(By.id("sliderValue"));
            int actualValue = Integer.parseInt(sliderValue.getAttribute("value"));
        }
    }

    @Test
    public void hoverTest() {
        driver.get("http://85.192.34.140:8081/");
        Actions actions = new Actions(driver);

        WebElement widgets = driver.findElement(By.xpath("//h5[text()='Widgets']"));
        widgets.click();
        WebElement menuClick = driver.findElement(By.xpath("//span[text()='Menu']"));
        menuClick.click();

        WebElement mainItem2 = driver.findElement(By.xpath("//a[text()='Main Item 2']"));
        actions.moveToElement(mainItem2).build().perform();//С помощью данного кода мышка переместиться на элемент (без клика)

        WebElement subList = driver.findElement(By.xpath("//a[text()='SUB SUB LIST »']"));
        actions.moveToElement(subList).build().perform();

        List<WebElement> twoElementsItem = driver.findElements(By.xpath("//a[contains(text(),'Sub Sub Item')]")); //Здесь мы получаем список несколько элементов содержащих опр-ый xpath
        Assertions.assertEquals(2, twoElementsItem.size()); //Проверка на то что мы получаем два элемента по опр-ому xpath

    }


}
