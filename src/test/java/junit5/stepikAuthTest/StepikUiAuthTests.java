package junit5.stepikAuthTest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$x;

public class StepikUiAuthTests {

    private String login = "vanjasmirnov3224@gmail.com";
    private String password = "smirnov3224";


    @Test
    public void uiAuthTest(){
        Selenide.open("https://stepik.org/catalog ");
        $x("//a[contains(@class,'navbar__auth_login')]").click();
        $x("//input[@name='login']").sendKeys(login);
        $x("//input[@name='password']").sendKeys(password);
        $x("//button[@type='submit']").click();
        $x("//img[@alt='User avatar']").should(Condition.visible).click();//Проверяем что мы авторизовались (аватар стал виден для нас)
        $x("//a[@id='ember810']").click();
        $x("//h1[@class='profile__title']").should(Condition.text("Смирнов Иван")); //Указываем что должен содержать элемент

    }
}
