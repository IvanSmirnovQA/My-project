package junit5.PiterGsm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ItemPage {

    private WebDriver driver;

    private By itemHeaderName = By.xpath("//h1[text()='Смартфон Apple iPhone 16 Pro 256GB, Black Titanium (черный)']");
    private By itemPrice = By.xpath("//div[@class='product__price']");

    public ItemPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getItemName(){
        return driver.findElement(itemHeaderName).getText(); //Данным методом находим текст и возвращаем этот текст
    }

    public Integer getItemPrice() {
        String priceText = driver.findElement(itemPrice).getText();
        priceText.replaceAll("[^0-9.]", ""); //Данный код удаляет из строки всё, кроме цифр
        return Integer.parseInt(priceText); //Данный код преобразует результат в число


    }
}
