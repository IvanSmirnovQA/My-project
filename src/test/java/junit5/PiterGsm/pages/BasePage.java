package junit5.PiterGsm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage { //создали данный класс для инициализации инструментов, которые будут применяться и на других страницах

    protected  WebDriver driver; //Проинициализировали таким образом driver, чтобы классы наследники могли его вызвать, а мы не иниц-ли его по 100 раз

   protected WebDriverWait wait;

   protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        js = (JavascriptExecutor) driver; //Данная строка означает "взять driver и использовать его как JavascriptExecutor."
    }


    public String getTextJs(By element) {
        return (String)  js.executeScript("return arguments[0].textContent;",
                driver.findElement(element)); //Данный код выполняет JavaScript в браузере и получает текстовое содержимое из элемента textContent, преобразует его в String и сохраняет результат в переменную priceText.
    }

    public void jsClick(By element){
        js.executeScript("arguments[0].click;", driver.findElement(element)); //Данный код выполняет JavaScript в браузере и кликает по элементу
    }

    public WebElement waitForTextPresentedInList(By list, String value) {
        return wait.until(driver -> driver.findElements(list).stream()
                .filter(element -> element.getText().contains(value))
                .findFirst()
                .orElse(null));


//    public WebElement waitForTextPresentedInList (By list, String value) { //By list-локатора, по которому будет произведен поиск. String value-текст, который мы будем искать внутри элементов
//        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(list)); //Данная строка подразумевает "Ждать, пока на странице появятся все элементы, соответствующие локатору list"
//        return driver.findElements(list).stream()
//                .filter(x->x.getText().contains(value)) //Данный код подразумевает "оставить только те элементы, у которых текст содержит нужное значение." "x->x.getText()" Подразумевает из Списка WebElement взять текст
//                .findFirst()//С помощью данного метода берем первый подходящий вариант
//                .orElseThrow(()->new NoSuchElementException("Города нет " + value)); //Данный код подразумевает "если элемент не найден будет выброс ошибки"

//Общий смысл метода - ждёт появления списка элементов на странице, затем ищет среди них элемент, который содержит нужный текст, и возвращает этот элемент. Если элемент не найден будет выброшен Exception
    }
}
