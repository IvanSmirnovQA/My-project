package junit5.PiterGsm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultPage {

    private WebDriver driver;
    private By maxPrice = By.xpath("//input[@name='max']"); //Фильтрация по мак-ой цене

    private By minPrice = By.xpath("//input[@name='min']");//Фильтрация по мин-ой цене

    private By items = By.xpath("//div[@class='digi-products-grid digi-products-grid_horde']//div[@class='digi-product']"); //Указали список айфонов, которые выдаются при поиске, выставляя фильтр от 60 000

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openItem(){
        driver.findElements(items)//Получаем список элементов, которые выпадают при указании фильтров по цене
                .get(0);//Получаем элемент с индексом 0
    }

    //Данный метод вводит мин-ую цену в фильтры
    public void setMinPrice(Integer minValue){ //Обозначили Integer так как .sendKeys (метод исп-ван ниже) принимает только строку
        driver.findElement(minPrice).sendKeys(String.valueOf(minValue)); //String.valueOf - данный метод нужен, чтобы превратить число в строку, так как sendKeys принимает только строку
    }

    public void setMaxPrice(Integer maxValue){//Обозначили Integer так как .sendKeys (метод исп-ван ниже) принимает только строку
        driver.findElement(maxPrice).sendKeys(String.valueOf(maxValue));//String.valueOf - данный метод нужен, чтобы превратить число в строку, так как sendKeys принимает только строку
    }
}
