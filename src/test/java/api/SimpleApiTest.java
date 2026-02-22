package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SimpleApiTest {

    @Test
    public void getAllUsersTest(){
        given().get("https://fakestoreapi.com/users/")
                .then()
                .log().all() // с помощью данного метода мы логируем (выводим в консоль) полностью ответ от сервера (а можем указать конкретные детали, которые нас интересуют и могут логироваться- body, headers и т.д.)
                .statusCode(200); //с помощью данного метода мы проверяем, что на наш запрос возвращается код 200

        //given - это точка старта API-теста в нём описывается всё, что мы передаём в API
        //с помощью "contentType" в его (параметрах) мы указываем формат данных, которые будем отправлять на сервер
    }

    @Test
    public void getSingleTest(){
        int userId = 2; //создали данную переменную, чтобы вводить её в методе ".get" и подставлять имя переменной "{userId}" для получения ответа
        given().pathParam("userId", 2)
                .get("https://fakestoreapi.com/users/{userId}")
                .then().log().all() //логируем всю полученную информацию из запроса
                .statusCode(200)
                .body("id", equalTo(userId))
                .body("address.zipcode",matchesPattern("\\d{5}-\\d{4}")); //"\\d{5}-\\d{4}" - означает, что в "address.zipcode" сначала должно идти 5 цифр, а потом 4
    }

    @Test
    public void getAllUsersWithLimitTest() {
        int limitSize = 5;
        given().queryParam("limit", limitSize)//с помощью queryParam указали как именно получить нужные нам данные. В данном случае указали, что нужны данные с определённым лимитом (5)
                .get("https://fakestoreapi.com/users")//выполняем get запрос
                .then() //с помощью then потом указываем какой ответ мы ожидаем увидеть - код 200
                .log() //указываем, чтобы результаты запроса залогировались
                .all() //указываем, чтобы залогировалось всё
                .statusCode(200) // указываем что ожидаем получить от GET запроса статус 200 (в блоке then)
                .body("", hasSize(limitSize)); //Данная строка проверяет, что в body содержаться 5 элементов (исходя из значения переменной limitSize. Двойные кавычки"" означают проверку JSON целиком
    }

    @Test
    public void getAllUsersSortByDescTest() {
        String sortType = "desc"; // создали переменную sortType со значением "desc" (по убыванию), чтобы в дальнейшем использовать данную переменную для queryParam и сортировки API запроса



        //Создадим объект Response, который хранит ВЕСЬ HTTP-ответ сервера(Не только JSON, а вообще всё, что пришло от сервера.).

        Response sortedResponse =  given().queryParam("sort", sortType)//В queryParam указали (название параметра"sort", и сам тип сортировки. т.е. desc - по убыванию) - на сервер будет уходить запрос "/users?sort=desc" - что означает, что список пользователей должен идти по убыванию
                .get("https://fakestoreapi.com/users")
                .then().log().all() // указываем, чтобы был залогирован полностью ответ от сервера
                .extract().response(); //с помощью extract() — мы извлекаем данные, а с помощью response()получаем Весь HTTP ответ
        //создали отсортированный GET запрос по убыванию

        //Создадим объект Response, который хранит ВЕСЬ HTTP-ответ сервера(Не только JSON, а вообще всё, что пришло от сервера.).
         Response notSortedResponse =  given().get("https://fakestoreapi.com/users")
                .then().log().all()
                 .extract().response(); //с помощью extract() — мы извлекаем данные, а с помощью response()получаем Весь HTTP ответ
         //создали неотсортированный GET запрос

        //создаём "переменные-списки" List<Integer>, которые будут записывать данные из JSON ОТВЕТА
        List<Integer> sortedResponseIds = sortedResponse.jsonPath().getList("id"); // этой командой мы говорим «Возьми все id пользователей из JSON-ответа и сохрани их в отсортированном порядке

        List<Integer> notSortedResponseIds = notSortedResponse.jsonPath().getList("id"); // этой командой мы говорим «Возьми все id пользователей из JSON-ответа и сохрани их в том порядке, в котором их прислал сервер

        List<Integer> sortedByCode = notSortedResponseIds.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        Assertions.assertNotEquals(sortedResponseIds, notSortedResponseIds);
        //С помощью Assertions вычисляем - идентичные ли значения выдают sortResponseIds и notSortedResponseIds
        Assertions.assertEquals(sortedByCode, sortedResponseIds);
    }

}
