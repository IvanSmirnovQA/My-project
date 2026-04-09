package junit5.stepikAuthTest;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.*;

import static io.restassured.RestAssured.given;

public class StepikApiAuthTests {

    private StepikAuth testUsers = new StepikAuth("vanjasmirnov3224@gmail.com", "smirnov3224"); //Проиниц-ли объект класса StepikAuth и в пар-ах указали данные

    @Test
    public void apiAuthTest(){
        Selenide.open("https://stepik.org/catalog ");
        Set<Cookie> cookiesBrowser = WebDriverRunner.getWebDriver().manage().getCookies(); //Проинициализировали список получаемых куки (метод .getCookies возвращает Set-список неповторяемых значений)
        Map<String, String> authHeaders = new HashMap<>();
        authHeaders.put("referer", "https://stepik.org/learn?auth=login"); //С помощью метода put мы добавляем данные в Set "cookiesBrowser" (сначала указываем ключ, потом значение)
        authHeaders.put("origin","https://stepik.org"); //С помощью метода put мы добавляем данные в Set "cookiesBrowser" (сначала указываем ключ, потом значение)
        authHeaders.put("authority","stepik.org"); //С помощью метода put мы добавляем данные в Set "cookiesBrowser" (сначала указываем ключ, потом значение)

        List<io.restassured.http.Cookie> restAssuredCookies = new ArrayList<>(); //Проинициализировали список с типом данные Cookie (из библ-ки RestAssured)
        for (Cookie cookie : cookiesBrowser) { //Проиниц-ли цикл с переменной "cookie" и типом данных Cookie в котором перебираем элементы из списка cookiesBrowser
            io.restassured.http.Cookie temp = new io.restassured.http.Cookie
                    .Builder(cookie.getName(), cookie.getValue())
                    .setDomain(cookie.getDomain())
                    .setPath("/")
                    .build();
            restAssuredCookies.add(temp);
            if (cookie.getName().equals("csrftoken")){ //Если будем получать значение "csrftoken", то будем менять его на "x-csrftoken"
                authHeaders.put("x-csrftoken", cookie.getValue());
            }
        }

        Map<String, String> authCookies = given().contentType(ContentType.JSON)
                .body(testUsers)
                .headers(authHeaders)
                .cookies(new Cookies(restAssuredCookies)) //Почему new - передаваемый нами список имеет тип данных "Cookie", а нам нужны "Cookies"
                .post("https://stepik.org/api/users/login")
                .then().log().all().extract().cookies(); //Данной строкой логируем все значение и достаём cookies

        Cookie csrf = new Cookie("csrftoken", authCookies.get("csrftoken"), "stepik.org", "/", null);
        Cookie sessionId = new Cookie("sessionid", authCookies.get("sessionid"), "stepik.org", "/", null);

        WebDriverRunner.getWebDriver().manage().addCookie(csrf);
        WebDriverRunner.getWebDriver().manage().addCookie(sessionId);
        Selenide.refresh(); //Обновив страницу на которой мы находимся, мы авторизуемся благодаря полученному списку токенов
    }
}
