package api;

import Models.fakeapiusers.Name;
import Models.fakeapiusers.UserRobot;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SimpleApiRefactoredTests {


    @BeforeAll //аннотация запускает одну общую логику на все тесты, которые запускаются
    public static void setUp() { //создали метод, что бы были "базовые" настройки для тестов
        RestAssured.baseURI = "https://fakestoreapi.com"; //создали "базовую" страницу
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured());

//        RequestLoggingFilter() — Это объект-фильтр, который подключается в Rest Assured и перед отправкой выводит детали запроса (HTTP-метод, headers и т.д.) в консоль - логирует запрос
//        ResponseLoggingFilter() — это фильтр, который получает ответ (status code, headers, response body-тело ответа) - логирует ответ. С его помощью, мы детально увидим что произошло не так.
//        Так как они добавлены в setUp через RestAssured.filters RequestLoggingFilter и ResponseLoggingFilter будут применяться ко всем запросам во всех тестах, пока тесты выполняются в рамках этого запуска.
//        С помощью данных лого мы получим детальный ответ, если что-то пойдет не так
//        RestAssured — это главный “конфиг-класс” библиотеки Rest Assured. С его помощью мы указываем "глобальные настройки"
//        "new AllureRestAssured()" означает, чтобы собирался отчёт в Allure более детально
    }

    @Test
    public void getAllUsersTest() {
        given().get("/users") //так как в аннотации @Before All указана базовая ссылка мы тут указали лишь эндпоинт
                .then()
                .statusCode(200);
        //Не указываем .then().log().all(), так как в аннотации @Before All мы указали, чтобы производилось лог-ие запроса и лог-ие ответа


    }

    @Test
    public void getSingleTest() {
        int userId = 2;
        UserRobot response = given()
                .pathParam("userId", 2)
                .get("/users/{userId}")//так как в аннотации @Before All указана базовая ссылка мы тут указали лишь эндпоинт
                .then() //логируем всю полученную информацию из запроса
                .statusCode(200)
                .extract().as(UserRobot.class); //указали, чтобы ответ от сервера извлекался, как определённый класс - UserRobot
                //.extract - достаёт ответ HTTP ответ
                // - as(UserRobot.class) - превращает ответ сервера в определённый объект сервера
        Assertions.assertEquals(userId, response.getId()); //используем Assertions, чтобы сравнить ID-шники (который мы указали userID и ID-шником, который получили в ответе от сервера)
        Assertions.assertTrue(response.getAddress().getZipcode().matches("\\d{5}-\\d{4}")); //проверяем на true что zipCode соответствует формату "5 цифр - 4 цифры"

                //.body("id", equalTo(userId))
                //.body("address.zipcode", matchesPattern("\\d{5}-\\d{4}"));

        Name name = given()
                .pathParam("userId", 2)
                .get("/users/{userId}")
                .then()
                .statusCode(200)
                .extract().jsonPath().getObject("name", Name.class); //Эта строка берёт HTTP-ответ, превращает его в JSON и достаёт из него поле name, сразу преобразуя его в Java-объект класса Name.


        Name nameSecond = given()
                .pathParam("userId", userId)
                .get("/users/{userId}")
                .then()
                .extract().jsonPath().getObject("name", Name.class); //вывод из JSON-пути объект с названием "name" и превращает его в объект класса "Name"
    }

    @Test
    public void getAllUsersWithLimitTest() {
        int limitSize = 5;
        List<UserRobot> users =   given().queryParam("limit", limitSize)//с помощью queryParam указали как именно получить нужные нам данные. В данном случае указали, что нужны данные с определённым лимитом (5)
                .get("/users")//выполняем get запрос
                .then() //с помощью then потом указываем какой ответ мы ожидаем увидеть - код 200
                .log() //указываем, чтобы результаты запроса нужно залогировались
                .all() //указываем, чтобы залогировалось всё
                .extract() //метод для извлечения ответа
                .jsonPath().getList("", UserRobot.class); //данная строка возвращает тело ответа в формате JSON и возвращает список элементов и превращает каждый элемент в объект типа UserRobot
        //Специально указали тип List<UserRobot>, чтобы вернуть ответ в виде списка объектов типа UserRobot
        Assertions.assertEquals(5, users.size());
    }

    @Test
    public void getAllUsersWithLimitTestSecond() {
        int limitSize = 5;
        List<UserRobot> users =   given().queryParam("limit", limitSize)//с помощью queryParam указали как именно получить нужные нам данные. В данном случае указали, что нужны данные с определённым лимитом (5)
                .get("/users")//выполняем get запрос
                .then() //с помощью then потом указываем какой ответ мы ожидаем увидеть - код 200
                .log() //указываем, чтобы результаты запроса нужно залогировались
                .all() //указываем, чтобы залогировалось всё
                .extract().as(new TypeRef<List<UserRobot>>(){}); //Данная строка берёт тело HTTP-ответа и сразу превращает его в List<UserRobot>
        Assertions.assertEquals(5, users.size());
    }

    @Test
    public void getAllUsersSortByDescTest() {
        String sortType = "desc"; // создали переменную sortType со значением "desc" (по убыванию), чтобы в дальнейшем использовать данную переменную для queryParam и сортировки API запроса

        //Создадим объект Response, который хранит ВЕСЬ HTTP-ответ сервера(Не только JSON, а вообще всё, что пришло от сервера.).
        List<UserRobot> userSorted =  given().queryParam("sort", sortType)//В queryParam указали (название параметра"sort", и сам тип сортировки. т.е. desc - по убыванию) - на сервер будет уходить запрос "/users?sort=desc" - что означает, что список пользователей должен идти по убыванию
                .get("/users")
                .then() // указываем, чтобы был залогирован полностью ответ от сервера
                .extract().as(new TypeRef<List<UserRobot>>(){});
        //создали отсортированный GET запрос по убыванию

        //Создадим объект Response, который хранит ВЕСЬ HTTP-ответ сервера(Не только JSON, а вообще всё, что пришло от сервера.).
        List<UserRobot> userNotSorted =  given().get("https://fakestoreapi.com/users")
                .then().log().all()
                .extract().as(new TypeRef<List<UserRobot>>(){});


        List<Integer> sortedResponseIds = userSorted.stream()
                .map(UserRobot::getId)//данная строка преобразовывает UserRobot в Integer (значение переменной id)
                .collect(Collectors
                        .toUnmodifiableList());
        //В савокупности данная команда берёт список пользователей (UserRobot), вытаскивает у каждого id и превращает их в неизменяемый список чисел (List<Integer>).




        //List<Integer> notSortedResponseIds = notSortedResponse.jsonPath().getList("id"); // этой командой мы говорим «Возьми все id пользователей из JSON-ответа и сохрани их в том порядке, в котором их прислал сервер

        //List<Integer> sortedByCode = notSortedResponseIds.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

//        Assertions.assertNotEquals(sortedResponseIds, notSortedResponseIds);
//        //С помощью Assertions вычисляем - идентичные ли значения выдают sortResponseIds и notSortedResponseIds
//        Assertions.assertEquals(sortedByCode, sortedResponseIds);

}

    @ParameterizedTest //указали, что это будет параметризированный тест
    @ValueSource(ints = {0, 1, 10, 20}) //указали какое значение будет для парам-ых тестов (каждую сессию значение "limitSize" будет меняться - сначала 0, потом 1, потом 10, потом 20)
    public void getAllUsersWithLimitTestParametrized(int limitSize) { //Добавили в параметры метода переменную limitSize у которой будет меняться значение (0, 1, 10, 20)
        List<UserRobot> users =   given().queryParam("limit", limitSize)//C помощью queryParam указали как именно получить нужные нам данные. В данном случае указали, что нужны данные с определённым лимитом (5)
                .get("/users")//выполняем get запрос
                .then() //с помощью then потом указываем какой ответ мы ожидаем увидеть - код 200
                .statusCode(200)
                .extract().as(new TypeRef<List<UserRobot>>(){}); //Данная строка берёт тело HTTP-ответа и сразу превращает его в List<UserRobot>
        Assertions.assertEquals(limitSize, users.size());
        // limitSize будет иметь разные значения, так как в пам-ом тесте указано четыре разных значения
        //Данный метод в савок-сти выводит в консоль заданное кол-во пользователей (0, 1, 10, 20) и их атрибуты
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 40})
    public void getAllUsersWithLimitTestsParametrizedNegative(int limitSizeNegative) { //создали метод (в параметрах которого limitSize - значение будет подставляться 0 и 40)
        List<UserRobot> users = given().queryParam("limit", limitSizeNegative)
                .get("/users")
                .then()
                .statusCode(200)
                .extract().as(new TypeRef<List<UserRobot>>() {});
        Assertions.assertNotEquals(limitSizeNegative, users); //данная проверка указывает на то, что указанный limitSizeNegative не соответствует реальному кол-ву users)
        //Созданный метод проверяет, что заданные значения в параметрах аннотации limitSizeNegative не равны кол-ву users


    }
}

