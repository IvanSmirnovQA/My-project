package AllurePlusSelenide;


import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;

public class AllureAttachments {

    @Attachment(value = "{name}", type = "image/png")
    public static byte[] screenshot(String name) {
        return Selenide.screenshot(OutputType.BYTES);
    }
}