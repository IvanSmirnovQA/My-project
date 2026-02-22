package AllurePlusSelenide;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;

import static com.codeborne.selenide.Selenide.$;

public class SelenideTest extends FirstBaseSelenideTest {

    @Test
    public void CheckFilm () {

        Selenide.open("https://www.kinopoisk.ru/"); //открываем вкладку Кинопоиска
        attachScreenshot("Открытие страницы");
        attachScreenshot("Страница до поиска вамиров");

        $(By.xpath("//*[@id=\"__next\"]/div[1]/div[1]/header/div/div[2]/div[2]/div/form/div/input")).sendKeys("Сумерки");
        attachScreenshot("Результаты поиска");
    }

    @Attachment(value = "{name}", type = "image/png", fileExtension = "png")
    public byte[] attachScreenshot(String name) {
        // делаем скриншот в виде байтов и отдаём Allure
        return Selenide.screenshot(OutputType.BYTES);
    }








    //    @Attachment (value = "{name}", type = "image/png", fileExtension = "png") //@Attachment - нужна для прикрепления файлов, скриншотов или данных к отчету о тестировании. Аннотация позволяет возвращать данные, которые автоматически добавляются в Allure отчет как вложение
//    public byte [] attachScreenshot (String name) { //Почему пишем byte - byte это тип возвращаемого вложения. Allure ожидает именно массив байтов для файлов (скриншотов, PDF)
//        return screenshot(name).getBytes(); //return возвращает массив байтов - эти байты попадут в отчет как файл PNG
//        //Данный метод служит для того, чтобы делать скриншоты
//    }



//    @Test
//    void simpleTest() {
//        System.out.println("TEST RUN");
//    }
}
