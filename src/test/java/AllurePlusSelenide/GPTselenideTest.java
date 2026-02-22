package AllurePlusSelenide;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class GPTselenideTest extends BaseSelenideTest {

    @Test
    public void checkFilm() {

        Selenide.open("https://www.kinopoisk.ru/");

        $(By.xpath("//*[@id=\"__next\"]/div[1]/div[1]/header/div/div[2]/div[2]/div/form/div/input"))
                .sendKeys("Сумерки");
    }
}