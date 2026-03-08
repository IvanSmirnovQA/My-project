package junit5.PiterGsm;

import junit5.PiterGsm.pages.ItemPage;
import junit5.PiterGsm.pages.MainPage;
import junit5.PiterGsm.pages.SearchResultPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PiterGsmFilterTests extends BaseTestPiterGsm{

    @Test
    public void searchResultTest(){

        //Проинициализировали тестовые данные
        String expectedItem = "Iphone 16 PRO";
        Integer expectedPriceMin = 60000;
        Integer expectedPriceMax = 120000;

        MainPage mainPage = new MainPage(driver);
        mainPage.searchItem( expectedItem);

        //Проинициализировали взаимодействия на странице
        SearchResultPage resultPage = new SearchResultPage(driver);//Проинициализировали объект класса SearchResultPage у которого есть методы для поиска
        resultPage.setMinPrice(expectedPriceMin); //Указали в фильтрах мин-ую цену
        resultPage.setMaxPrice(expectedPriceMax); //Указали в фильтрах макс-ую цену
        resultPage.openItem();//Данная строка откроет элемент с индексом 0

        ItemPage itemPage = new ItemPage(driver);
        String actualName = itemPage.getItemName();
        Integer actualPrice = itemPage.getItemPrice();

        Assertions.assertTrue(actualName.toLowerCase().contains(expectedItem.toLowerCase())); //Сравниваем результаты в формате нижнего регистра
        Assertions.assertTrue(actualPrice>=expectedPriceMin && actualPrice<=expectedPriceMax); //Сравниваем что актуальная цена >= мин-ой и, что акт-ая цена <= макс-ой цене
    }

}
