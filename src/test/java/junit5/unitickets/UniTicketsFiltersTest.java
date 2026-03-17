package junit5.unitickets;

import junit5.wildberries.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UniTicketsFiltersTest extends BaseTest {


    @BeforeEach
    public void openSite(){
        driver.get("https://uniticket.ru/");
    }

    @Test
    public void filterTest(){
        int expectedDayForward = 25;
        int expectedDayBack = 30;
        UniTicketsMainPage mainPage = new UniTicketsMainPage(driver);
        mainPage.setCityFrom("Казань")
                .setCityTo("Дубай")
                .setDayForward(expectedDayForward)
                .setDayBack(expectedDayBack)
                .search();
    }
}
