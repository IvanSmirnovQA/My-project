package api;

import Models.fakeapiusers.Address;
import Models.fakeapiusers.Geolocation;
import Models.fakeapiusers.Name;
import Models.fakeapiusers.UserRobot;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SimpleApiPostTest {


    @Test
    public void addNewuserTest () {
        Name name = new Name("Thomas", "Anderson"); //создали объект из класса моделей и заполнили "first name" и "lastName"

        Geolocation geolocation = new Geolocation("3224.3224", "27.27.27"); //создали объект из класса моделей и заполнили "lat" и "long" из объекта geolocation

        Address address = Address.builder() //создали объект класса "Address", чтобы с помощью builder придать значения ключам
                .city("Москва")//придаем значение обязательным ключам
                .street("Арбат Роялти")//придаем значение обязательным ключам
                .number(3)//придаем значение обязательным ключам
                .zipcode("12345-6789")//придаем значение обязательным ключам
                .geolocation(geolocation).build(); //в "geolocation" мы указали раннее созданный объект "geolocation" (там уже заданы значение - "3224.3224" и "27.27.27" )

        UserRobot bodyRequest = UserRobot.builder() //Создали объект типа UserRobot, чтобы в дальнейшем отправлять POST запрос, указав объет для отправки (т.е. bodyRequest)
                .name(name)
                .phone("89508960611")
                .email("3224@gmail.com")
                .username("thomasAnderson")
                .password("3224")
                .address(address)
                .build(); //с помощью builder указали значения в ключах


        given().body(bodyRequest) //создали запрос, в котором в "body" указали, что будет отправка запроса с данными из объекта bodyRequest
                .post("https://fakestoreapi.com/users") //указали что будет POST запрос и указали адрес куда будет направлен запрос
                .then().log().all() //указали что нужно логирование
                .statusCode(200) //указали проверку на код 200
                .body("id", notNullValue()); //указали, что ответ сервера будет содержать id и не будет равен нулю

    }

    private UserRobot getTestUser (){ //создали данный приватный метод, который возвращает данные о пользователе, чтобы попробовать PUT запрос
        Name name = new Name("Thomas", "Anderson");

        Geolocation geolocation = new Geolocation("3224.3224", "27.27.27");

        Address address = Address.builder()
                .city("Москва")
                .street("Арбат Роялти")
                .number(3)
                .zipcode("12345-6789")
                .geolocation(geolocation).build();

        return UserRobot.builder()
                .name(name)
                .phone("89508960611")
                .email("3224@gmail.com")
                .username("thomasAnderson")
                .password("3224")
                .address(address)
                .build();
    }


    @Test
    public void updateUserTest(){
        UserRobot user = getTestUser(); //создали переменную с типом данных UserRobot у которой значение = раннее созданный метод getTestUser который возвращает данные (name, phone, email и т.д.)
        String oldPassword = user.getPassword(); //создали переменную, у которой значение = получение пароля у пользователя

        user.setPassword("newPass"); //указав переменную user указали метод (сеттер), с помощью которого установили новый пароль

        given().body(user) //начало отправки запроса с телом, в параметрах которого мы ранее созданный объект "user" (у которого уже указаны значения)
                .put("https://fakestoreapi.com/users/7") //вызвали метод put, чтобы обновить данные у конкретного пользователя
                .then().log().all()
                .body("password", not(equalTo(oldPassword))); //указали что в body (в ключе "password" не должно быть значение, как у oldPasswor - старого пароля, ранее который мы инициализировали)
    }


    @Test
    public void deleteUseTest() {

        given().delete("https://fakestoreapi.com/users/7") //начало отправки запроса с методом delete (в параметрах указали кого хотим удалить)
                .then().log().all()
                .statusCode(200);
    }


    @Test
    public void autUserTest(){
        Map<String, String> userAuth = new HashMap<>(); //создали Хэш карту, чтобы передать "ключ-значение", а именно userName-"jimmie_k" и password-"klein*#%*", чтобы авторизоваться под раннее созданным пользователем (изначально был создан на fakeApiStore)
        userAuth.put("username", "jimmie_k"); //указываем данные раннее созданного пользователя для авторизации
        userAuth.put("password", "klein*#%*"); //указываем данные раннее созданного пользователя для авторизации
        given().contentType(ContentType.JSON) //указали, что при отправке запроса тип отправляемого запроса должен быть JSON формат (иначе сервер укажет ошибку из-за неправильно отправляемого формата в запросе)
                .body(userAuth) //указали, что при отправке запроса в body данные от userAuth
                .post("https://fakestoreapi.com/auth/login") //создали запрос с методом post для авторизации
                .then().log().all()
                .statusCode(201)
                .body("token", notNullValue()); //проверяем что ключ "token" не равен нулю - т.е., нам выдался валидный токен на наш запрос

        //после того как мы отправили данный запрос на авторизацию нам будет выдан токен авторизации, который мы сможем использовать в дальнейшем

    }



}
