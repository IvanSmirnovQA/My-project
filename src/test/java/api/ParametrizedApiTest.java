package api;

import Models.fakeapiusers.UserRobot;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ParametrizedApiTest {
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

    }
}

