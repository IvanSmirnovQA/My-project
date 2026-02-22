package tests.swaggerTests;

import Models.swagger.FullUser;
import Models.swagger.Info;
import Models.swagger.JwtAuthData;
import assertions.AssertableResponse;
import assertions.Conditions;
import assertions.GenericAssertableResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import lombok.Builder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static assertions.Conditions.hasMessage;
import static assertions.Conditions.hasStatusCode;
import static io.restassured.RestAssured.given;


public class UserTests {
    private static Random random; //инициализировала Random, чтобы в дальнейшем использовать его по всему классу

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "http://85.192.34.140:8080/";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        //RequestLoggingFilter() — Это объект-фильтр, который подключается в Rest Assured и перед отправкой выводит детали запроса (HTTP-метод, headers и т.д.) в консоль - логирует запрос
        //ResponseLoggingFilter() — это фильтр, который получает ответ (status code, headers, response body-тело ответа) - логирует ответ. С его помощью, мы детально увидим что произошло не так.
        random = new Random(); //прописали random, чтобы каждый раз его не указывать в тестах
    }


    @Test
    public void positiveRegisterTest() { //создали данный метод, чтобы протестировать регистрацию

        //строчкой ниже мы создаём экземпляр класса FullUser, чтобы с помощью builder указать необходимые для рег-ии данные (так как в док-ии указано, что заполнять необязательно)
        FullUser user = FullUser.builder()
                .login("Ivan Smirnov")
                .pass("ObiVanKenobi")
                .build();
        //собрали экземпляр объекта FullUser указав лишь обязательные данные для рег-ии, а данные необязательные к заполнению так как мы их не указали быть null


        given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup");
    }

    @Test
    public void positiveRegisterTestWithRandom() { //создали данный метод, чтобы протестировать регистрацию

        int randomNumber = Math.abs(random.nextInt()); //Math.abs() — это метод, который возвращает не отрицательное число (а в параметре метода указано получение рандомного числа)
        FullUser user = FullUser.builder()
                .login("Ivan Smirnov " + randomNumber)
                .pass("ObiVanKenobi")
                .build();
        //собрали экземпляр объекта FullUser с рандомными параметрами, чтобы при каждой отправке не было ошибок и создавалась новая сущность

        Info info = given().contentType(ContentType.JSON) //создали экземпляр класс Info, чтобы считать из ответа объект с названием "info" (там будет "status" b "message"
                .body(user)
                .post("/api/signup")
                .then().statusCode(201)
                .extract().jsonPath().getObject("info", Info.class);//с помощью данной строки достаём определённый объект "info" и считываем его, как класс "Info"
        Assertions.assertEquals("User created", info.getMessage()); //В параметрах мы указали, что ожидаем сообщение "User created" и сравнили его с getMessage при отправке запроса
    }


    @Test
    public void negativeRegisterLoginExistTest () {
        int randomNumber = Math.abs(random.nextInt()); //Math.abs() — это метод, который возвращает не отрицательное число (а в параметре метода указано получение рандомного числа)
        FullUser user = FullUser.builder()
                .login("Ivan Smirnov " + randomNumber)
                .pass("ObiVanKenobi")
                .build();
        //собрали экземпляр объекта FullUser с рандомными параметрами, чтобы при каждой отправке не было ошибок и создавалась новая сущность

        Info info = given().contentType(ContentType.JSON) //создали экземпляр класс Info, чтобы считать из ответа объект с названием "info" (там будет "status" b "message", contentType(ContentType.JSON) означает, что мы отправляем в запросе JSON
                .body(user)
                .post("/api/signup")
                .then().statusCode(201)
                .extract().jsonPath().getObject("info", Info.class);//с помощью данной строки достаём определённый объект "info" и считываем его, как класс "Info"
        Assertions.assertEquals("User created", info.getMessage()); //В параметрах мы указали, что ожидаем сообщение "User created" и сравнили его с getMessage

        Info errorInfo = given().contentType(ContentType.JSON) //contentType(ContentType.JSON) означает, что мы отправляем в запросе JSON
                .body(user)
                .post("/api/signup")
                .then()
                .statusCode(400)//ожидаем ошибку
                .extract().jsonPath().getObject("info", Info.class);
        Assertions.assertEquals("Login already exist", errorInfo.getMessage()); //В параметрах мы указали, что ожидаем сообщение "Login already exist" и сравнили его с getMessage


        new AssertableResponse(given().contentType(ContentType.JSON) //contentType(ContentType.JSON) означает, что мы отправляем в запросе JSON
                .body(user)
                .post("/api/signup")
                .then());
    }

    @Test
    public void negativeRegisterNopasswordTest() {
        int randomNumber = Math.abs(random.nextInt()); //Math.abs() — это метод, который возвращает не отрицательное число (а в параметре метода указано получение рандомного числа)
        FullUser user = FullUser.builder()
                .login("Ivan Smirnov " + randomNumber)
                .build();
        //собрали экземпляр объекта FullUser с рандомными параметрами, чтобы при каждой отправке не было ошибок и создавалась новая сущность

        Info info = given().contentType(ContentType.JSON) //создали экземпляр класс Info, чтобы считать из ответа объект с названием "info" (там будет "status" b "message"
                .body(user)
                .post("/api/signup")
                .then().statusCode(201)
               .extract().jsonPath().getObject("info", Info.class);//с помощью данной строки достаём определённый объект "info" и считываем его, как класс "Info"

        new AssertableResponse(given().contentType(ContentType.JSON) //создали экземпляр класса AssertableResponse, чтобы с его помощью вып-ть API запрос с использованием созданными нами методов
                .body(user)
                .post("/api/signup")
                .then())
                .should(hasMessage("Missing login or password"))//Данный запрос ожидает сообщение "Missing login or password"
                .should(hasStatusCode(400)); //Данный запрос ожидает 400

        new GenericAssertableResponse<Info>(given().contentType(ContentType.JSON) //создали экземпляр класса AssertableResponse, чтобы с его помощью вып-ть API запрос с использованием созданными нами методов
                .body(user)
                .post("/api/signup")
                .then(), new TypeRef<Info>() { } )
                .should(hasMessage("Missing login or password"))//Данный запрос ожидает сообщение "Missing login or password"
                .should(hasStatusCode(400)) //Данный запрос ожидает 400
                .asObjectName();

        Assertions.assertEquals("Missing login or password", info.getMessage());//В параметрах мы указали, что ожидаем сообщение "Missing login or password" и сравнили его с getMessage Тест будет падать, так как в builder запроса мы не указали password
    }


    @Test
    public void PositiveAuthAdminTest () {

        JwtAuthData authData = new JwtAuthData("admin", "admin"); //создали объект POJO класса со значениями username-"admin" и password-"admin"
        String token = given().contentType(ContentType.JSON) // contentType(ContentType.JSON) означает, что мы отправляем в запросе JSON
                .body(authData) //в теле запроса указываем authData (т.е. "admin", "admin")
                .post("/api/login") //указываем что это post запрос с эндпоинтом "/api/login"
                .then().statusCode(200)
                .extract().jsonPath().getString("token"); //с помощью данной строки достаём строку "token"

        Assertions.assertNotNull(token); //проверяем, что полученный токен не является пустотой
    }

    @Test
    public void positiveAuthNewUserTest() {

        int randomNumber = Math.abs(random.nextInt()); //Math.abs() — это метод, который возвращает не отрицательное число (а в параметре метода указано получение рандомного числа)
        FullUser user = FullUser.builder() //Создали объект класса FullUser и указали его данные для будущего запроса с помощью builder
                .login("Ivan Smirnov " + randomNumber)
                .pass("ObiVanKenobi")
                .build();
        //собрали экземпляр объекта FullUser с рандомными параметрами, чтобы при каждой отправке не было ошибок и создавалась новая сущность

        Info info = given().contentType(ContentType.JSON) //создали экземпляр класс Info, чтобы считать из ответа объект с названием "info" (там будет "status" b "message"
                .body(user)
                .post("/api/signup")
                .then().statusCode(201)
                .extract().jsonPath().getObject("info", Info.class);//с помощью данной строки достаём определённый объект "info" и считываем его, как класс "Info"

        Assertions.assertEquals("User created", info.getMessage()); //В параметрах мы указали, что ожидаем сообщение "User created" и сравнили его с getMessage при отправке запроса

        JwtAuthData authData = new JwtAuthData(user.getLogin(), user.getPass());
        String token = given().contentType(ContentType.JSON) // contentType(ContentType.JSON) означает, что мы отправляем в запросе JSON
                .body(authData) //в теле запроса указываем authData (т.е. "admin", "admin")
                .post("/api/login") //указываем что это post запрос с эндпоинтом "/api/login"
                .then().statusCode(200)
                .extract().jsonPath().getString("token"); //с помощью данной строки достаём строку "token"

        Assertions.assertNotNull(token); //проверяем, что полученный токен не является пустотой
    }

    @Test
    public void negativeAuthTest (){
        JwtAuthData authData = new JwtAuthData("randomUserName", "randomPassword");
        given().contentType(ContentType.JSON) // contentType(ContentType.JSON) означает, что мы отправляем в запросе JSON
                .body(authData) //в теле запроса указываем authData (т.е. "admin", "admin")
                .post("/api/login") //указываем что это post запрос с эндпоинтом "/api/login"
                .then().statusCode(401);


        //Данный метод создан, чтобы проверить авторизацию пользователя с рандомными данными (авторизация не должна пройти)
    }

    @Test
    public void positiveGetUserInfoTest() {

        JwtAuthData authData = new JwtAuthData("admin", "admin"); //создали объект POJO класса со значениями username-"admin" и password-"admin"
        String token = given().contentType(ContentType.JSON) // contentType(ContentType.JSON) означает, что мы отправляем в запросе JSON
                .body(authData) //в теле запроса указываем authData (т.е. "admin", "admin")
                .post("/api/login") //указываем что это post запрос с эндпоинтом "/api/login"
                .then().statusCode(200)
                .extract().jsonPath().getString("token"); // ./jsonPath() - позволяет обращаться к JSONу с помощью ключа (в данному случае обращаемся по ключу "token", а getString Берёт поле token и возвращает его как String

        Assertions.assertNotNull(token); //проверяем, что полученный токен не является пустотой

        given()
                .auth().oauth2(token) //.auth().oauth2(token) — это удобный способ добавить Authorization: Bearer <token> без ручного написания заголовка. OAuth 2.0 — это стандарт авторизации
                .get("/api/user")
                .then().statusCode(200);

    }

    @Test
    public void negativeGetUserInfoInvalidJWTTest() {


        given()
                .auth().oauth2("random values") //.auth().oauth2(token) — это удобный способ добавить Authorization: Bearer <token> без ручного написания заголовка. OAuth 2.0 — это стандарт авторизации
                .get("/api/user")
                .then().statusCode(200);

        //Создали данный метод, чтобы показать что нельзя авторизовать с рандомными значениями
    }

    @Test
    public void negativeGetUserInfoWitjoutJWTTest() {


        given()
                .get("/api/user")
                .then().statusCode(200);

        //Создали данный метод, чтобы показать что нельзя авторизовать без токена авторизации
    }





    //Создали данный метод, чтобы сначала создать польз-ля с рандомными данными, потом получить инф-ию о нём, авторизоваться, а потом изменить пароль
@Test
public void positiveChangeUserPassTest() {
    //создали данный метод, чтобы протестировать регистрацию нового польз-ля

        int randomNumber = Math.abs(random.nextInt()); //Math.abs() — это метод, который возвращает не отрицательное число (а в параметре метода указано получение рандомного числа)
        FullUser user = FullUser.builder()
                .login("Ivan Smirnov " + randomNumber)
                .pass("ObiVanKenobi")
                .build();
        //собрали экземпляр объекта FullUser с рандомными параметрами, чтобы при каждой отправке не было ошибок и создавалась новая сущность

        Info info = given().contentType(ContentType.JSON) //создали экземпляр класс Info, чтобы считать из ответа объект с названием "info" (там будет "status" b "message"
                .body(user)
                .post("/api/signup")
                .then().statusCode(201)
                .extract().jsonPath().getObject("info", Info.class);//с помощью данной строки достаём определённый объект "info" и считываем его, как класс "Info"
        Assertions.assertEquals("User created", info.getMessage()); //В параметрах мы указали, что ожидаем сообщение "User created" и сравнили его с getMessage при отправке запроса

    JwtAuthData authData = new JwtAuthData(user.getLogin(), user.getPass()); //создали объект POJO класса и получили значения с помощью геттеров и сеттеров
    String token = given().contentType(ContentType.JSON) // contentType(ContentType.JSON) означает, что мы отправляем в запросе JSON
            .body(authData) //в теле запроса указываем authData (т.е. "admin", "admin")
            .post("/api/login") //указываем что это post запрос с эндпоинтом "/api/login"
            .then().statusCode(200)
            .extract().jsonPath().getString("token"); // ./jsonPath() - позволяет обращаться к JSONу с помощью ключа (в данному случае обращаемся по ключу "token", а getString Берёт поле token и возвращает его как String

    Assertions.assertNotNull(token); //проверяем, что полученный токен не является пустотой


    Map<String, String> password = new HashMap<>();
    String updatePassValue = "newPassUpdated";
    password.put("password", updatePassValue); //в параметрах указали ключ в запросе "password" и его значение updatePassValue, то сеть "newPassUpdated"

    Info updatePassInfo = given() //создали данный запрос, чтобы получить "info" от запроса и убедиться, что там указано "User password successfully changed"
            .contentType(ContentType.JSON)
            .auth().oauth2(token) //указываем, что будет авторизация при помощи токена
            .body(password)
            .put("/api/user")
            .then().extract().jsonPath().getObject("info", Info.class);

    Assertions.assertEquals("User password successfully changed", updatePassInfo.getMessage()); //Данным ассертом проверяем вывод сообщения от сервера и "getMessage" на запрос "updatePassInfo"

    authData.setPassword(updatePassValue);
    token = given().contentType(ContentType.JSON) // contentType(ContentType.JSON) означает, что мы отправляем в запросе JSON
            .body(authData) //в теле запроса указываем authData (т.е. "admin", "admin")
            .post("/api/login") //указываем что это post запрос с эндпоинтом "/api/login"
            .then().statusCode(200)
            .extract().jsonPath().getString("token"); // ./jsonPath() - позволяет обращаться к JSONу с помощью ключа (в данному случае обращаемся по ключу "token", а getString Берёт поле token и возвращает его как String

    FullUser updatedUser =  given()
            .auth().oauth2(token) //.auth().oauth2(token) — это удобный способ добавить Authorization: Bearer <token> без ручного написания заголовка. OAuth 2.0 — это стандарт авторизации
            .get("/api/user")
            .then().statusCode(200)
            .extract().as(FullUser.class);

    Assertions.assertNotEquals(user.getPass(), updatedUser.getPass()); //Сверяем, что старый и новый пароль не схожи
    }


    @Test
    public void negativeChangeAdminPasswordTest() {
        //Создали данный метод, чтобы изменения пароля админа (должна быть ошибка так как это системный пользователь. Сервер ответит в "info" - "Cant update base users"

        JwtAuthData authData = new JwtAuthData("admin","admin"); //указали даyyst fdnhbpfwbb
        String token = given().contentType(ContentType.JSON) // contentType(ContentType.JSON) означает, что мы отправляем в запросе JSON
                .body(authData) //в теле запроса указываем authData (т.е. "admin", "admin")
                .post("/api/login") //указываем что это post запрос с эндпоинтом "/api/login"
                .then().statusCode(200)
                .extract().jsonPath().getString("token"); // ./jsonPath() - позволяет обращаться к JSONу с помощью ключа (в данному случае обращаемся по ключу "token", а getString Берёт поле token и возвращает его как String

        Assertions.assertNotNull(token); //проверяем, что полученный токен не является пустотой


        Map<String, String> password = new HashMap<>();
        String updatePassValue = "newPassUpdated";
        password.put("password", updatePassValue); //в параметрах указали ключ в запросе "password" и его значение updatePassValue, то сеть "newPassUpdated"

        Info updatePassInfo = given() //создали данный запрос, чтобы получить "info" от запроса и убедиться, что там указано "User password successfully changed"
                .contentType(ContentType.JSON)
                .auth().oauth2(token) //указываем, что будет авторизация при помощи токена
                .body(password)
                .put("/api/user")
                .then()
                .statusCode(400) //ожидаем 400 так как невозможно изменить пароль админа
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("Cant update base users", updatePassInfo.getMessage()); //Данным ассертом проверяем вывод сообщения от сервера и "getMessage" на запрос "updatePassInfo"
    }


    @Test
    public void negativeAdminDeleteTest() {
        JwtAuthData authData = new JwtAuthData("admin","admin"); //указали даyyst fdnhbpfwbb
        String token = given().contentType(ContentType.JSON) // contentType(ContentType.JSON) означает, что мы отправляем в запросе JSON
                .body(authData) //в теле запроса указываем authData (т.е. "admin", "admin")
                .post("/api/login") //указываем что это post запрос с эндпоинтом "/api/login"
                .then().statusCode(200)
                .extract().jsonPath().getString("token"); // ./jsonPath() - позволяет обращаться к JSONу с помощью ключа (в данному случае обращаемся по ключу "token", а getString Берёт поле token и возвращает его как String


        Info infoAboutDelete =  given()
                .auth().oauth2(token)
                .delete("/api/user").then()
                .statusCode(400) //ожидаем 400 так как невозможно изменить пароль админа
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("Cant delete base users", infoAboutDelete.getMessage() ); //Данным ассертом сравниваем вывод сообщения от сервера "Cant delete base users" и getMessage у infoAboutDelete
    }

    @Test
    public void positiveDeleteNewUserTest() {
        int randomNumber = Math.abs(random.nextInt()); //Math.abs() — это метод, который возвращает не отрицательное число (а в параметре метода указано получение рандомного числа)
        FullUser user = FullUser.builder()
                .login("Ivan Smirnov " + randomNumber)
                .pass("ObiVanKenobi")
                .build();
        //собрали экземпляр объекта FullUser с рандомными параметрами, чтобы при каждой отправке не было ошибок и создавалась новая сущность

        Info info = given().contentType(ContentType.JSON) //создали экземпляр класс Info, чтобы считать из ответа объект с названием "info" (там будет "status" b "message"
                .body(user)
                .post("/api/signup")
                .then().statusCode(201)
                .extract().jsonPath().getObject("info", Info.class);//с помощью данной строки достаём определённый объект "info" и считываем его, как класс "Info"
        Assertions.assertEquals("User created", info.getMessage()); //В параметрах мы указали, что ожидаем сообщение "User created" и сравнили его с getMessage при отправке запроса

        JwtAuthData authData = new JwtAuthData(user.getLogin(), user.getPass());

        String token = given().contentType(ContentType.JSON) // contentType(ContentType.JSON) означает, что мы отправляем в запросе JSON
                .body(authData) //в теле запроса указываем authData (т.е. "admin", "admin")
                .post("/api/login") //указываем что это post запрос с эндпоинтом "/api/login"
                .then().statusCode(200)
                .extract().jsonPath().getString("token"); // ./jsonPath() - позволяет обращаться к JSONу с помощью ключа (в данному случае обращаемся по ключу "token", а getString Берёт поле token и возвращает его как String

        Info infoAboutDelete =  given()
                .auth().oauth2(token)
                .delete("/api/user").then()
                .statusCode(200) //ожидаем 400 так как невозможно изменить пароль админа
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("User successfully deleted", infoAboutDelete.getMessage());

        //Данный метод проверяет возможность удаления рядового пользователя (не админа)
    }

    @Test
    public void positiveGetAllUsersTest() { //создали данный метод, чтобы проверить список всех пользователей

        List<String> allUsers =  given().get("/api/users")
                .then()
                .statusCode(200)
                .extract().as(new TypeRef<List<String>>() {
                });

        Assertions.assertTrue(allUsers.size()>=3);//Ассерт показывает что число польз-ей больше, или равно 3


    }

}
