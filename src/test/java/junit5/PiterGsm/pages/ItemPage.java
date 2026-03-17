package junit5.PiterGsm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ItemPage extends BasePage {

    private By itemHeaderName = By.xpath("//h1[text()='Смартфон Apple iPhone 16 Pro 256GB, Black Titanium (черный)']");
    private By itemPrice = By.xpath("//div[@class='product__price']");

    public ItemPage(WebDriver driver) {
        super(driver); //super означает что конструктор наследуется от конструктора класса
    }

    public String getItemName(){
        return driver.findElement(itemHeaderName).getText(); //Данным методом находим текст и возвращаем этот текст
    }

    public Integer getItemPrice() {
        WebElement itemPriceElement = driver.findElement(itemPrice);
        String priceText = (String)  js.executeScript("return arguments[0].textContent;", itemPriceElement); //Данный код выполняет JavaScript в браузере и получает текстовое содержимое из элемента textContent, преобразует его в String и сохраняет результат в переменную priceText.
        priceText.replaceAll("[^0-9.]", ""); //Данный код удаляет из строки всё, кроме цифр
        return Integer.parseInt(priceText); //Данный код преобразует результат в число
    }
}
