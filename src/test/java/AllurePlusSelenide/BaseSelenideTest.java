package AllurePlusSelenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class BaseSelenideTest {

    @BeforeEach
    void setUp() {

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";

        // 🔴 ВАЖНО
        Configuration.screenshots = true;
        Configuration.savePageSource = false;

        SelenideLogger.removeListener("allure");
        SelenideLogger.addListener(
                "allure",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(false)
        );
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}