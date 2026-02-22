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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static TestUtils.RandomTestData.getAdminUser;
import static TestUtils.RandomTestData.getRandomUser;
import static assertions.Conditions.hasMessage;
import static assertions.Conditions.hasStatusCode;
import static io.restassured.RestAssured.given;


public class UserNewTest { //Данный класс был создан, чтобы показать как можно сократить код из класса UserTests

//    private static Random random; //создали объект из класса Random

    private static UserService userService; //создали объект из класса UserService

//    int randomNumber = Math.abs(random.nextInt()); //Math.abs() — это метод, который возвращает не отрицательное число (а в параметре метода указано получение рандомного числа)


    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "http://85.192.34.140:8080/";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        //RequestLoggingFilter() — Это объект-фильтр, который подключается в Rest Assured и перед отправкой выводит детали запроса (HTTP-метод, headers и т.д.) в консоль - логирует запрос
        //ResponseLoggingFilter() — это фильтр, который получает ответ (status code, headers, response body-тело ответа) - логирует ответ. С его помощью, мы детально увидим что произошло не так.
        userService = new UserService(); //проинициализировали userService, чтобы каждый раз его не указывать в тестах

    }

//    private FullUser getRandomUser() {
//        //строчкой ниже мы создаём экземпляр класса FullUser, чтобы с помощью builder указать необходимые для рег-ии данные (так как в док-ии указано, что заполнять необязательно все поля, мы можем указать только некоторые)
//       return FullUser.builder()
//                .login("RandomUser" + randomNumber)
//                .pass("ObiVanKenobi")
//                .build();
//        //собрали экземпляр объекта FullUser указав лишь обязательные данные для рег-ии, а данные необязательные к заполнению так как мы их не указали должны быть null
//
//
//
//    }
//
//
//    private FullUser getAdminUser() {
//        return FullUser.builder()
//                .login("admin")
//                .pass("admin")
//                .build();
//    }

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
//        int randomNumber = Math.abs(random.nextInt()); //Math.abs() — это метод, который возвращает не отрицательное число (а в параметре метода указано получение рандомного числа)
        FullUser user = getRandomUser(); //собрали экземпляр объекта FullUser с вызовом метода getRandomUser (там вся реализация)
        userService.register(user) //вызвали метод register для создания пользователя
                .should(hasStatusCode(201))//Вызываем метод hasStatusCode, чтобы сверить статус код
                .should(hasMessage("User created")); //Вызываем метод hasMessage, чтобы сверить сообщение при создании пользователя


//        FullUser userFirst = getRandomUser(); //Создали экземпляр класса FullUser, придав ему значение в виде метода getRandomUser
//
//        Info info = given().contentType(ContentType.JSON) //создали экземпляр класс Info, чтобы считать из ответа объект с названием "info" (там будет "status" и "message"
//                .body(user)
//                .post("/api/signup")
//                .then().statusCode(201)
//                .extract().jsonPath().getObject("info", Info.class);//с помощью данной строки достаём определённый объект "info" и считываем его, как класс "Info"
//        Assertions.assertEquals("User created", info.getMessage()); //В параметрах мы указали, что ожидаем сообщение "User created" и сравнили его с getMessage при отправке запроса
    }


    @Test
    public void negativeRegisterLoginExistTest () {
        FullUser user = getRandomUser(); //собрали экземпляр объекта FullUser с вызовом метода getRandomUser (там вся реализация)
        userService.register(user); //вызвали метод register для создания пользователя
        userService.register(user) //спец-но вызвали повторную регистрация, что проверить сценарий - что произойдёт при попытке создания существующего пользователя
                .should(hasStatusCode(400)) //Проверка на ожидаемый статус код - 400, потому что польз-ль уже был создан (строчка выше)
                .should(hasMessage("Login already exist")); //Проверка на ожидаемое сообщения о том что польз-ль уже создан

    }

    @Test
    public void negativeRegisterNoPasswordTest() {
        FullUser user = getRandomUser();
        user.setPass(null); //Установили пароль юзеру "null". Если укажем валидные данные, то польз-ль будет создан
        userService.register(user)
                .should(hasStatusCode(400))
                .should(hasMessage("Missing login or password"));
    }


    @Test
    public void PositiveAuthAdminTest () {
        FullUser user = getAdminUser(); //Получили данные админа (данный метод инициализировали в данном классе )



        String token =  userService.auth(user) //Производим авторизацию с данными юзера
                .should(hasStatusCode(200))
                .asJwt(); //Данный метод вернёт строку "token" - данный метод реализован в созданном нами классе AssertableResponse

        Assertions.assertNotNull(token);
    }

    @Test
    public void positiveAuthNewUserTest() {

        FullUser user = getRandomUser(); //Вызвав метод getRandomUser создаём рандомного пользователя
        userService.register(user); //регистрируем пользователя
        String token =  userService.auth(user) //Авторизуемся
                .should(hasStatusCode(200)).asJwt(); //Получаем статус код 200 и извлекаем токен

        Assertions.assertNotNull(token); //проверяем, что полученный токен не является пустотой
    }

    @Test
    public void negativeAuthTest (){ //Данный метод создан, чтобы проверить авторизацию пользователя с рандомными данными (авторизация не должна пройти)

        FullUser user = getRandomUser();

        userService.auth(user)
                .should(hasStatusCode(401));


    }

    @Test
    public void positiveGetUserInfoTest() { //Метод позволяет получить информацию о пользователе

        FullUser user = getAdminUser();
        String token =  userService.auth(user).asJwt();

        userService.getUserInfo(token) //Получаем информацию о пользователе и вставляем токен в параметры, чтобы сервер позволил нам получить информаицю о польз-ле
                .should(hasStatusCode(200));

    }

    @Test
    public void negativeGetUserInfoInvalidJWTTest() { //Метод проверяет, будет ли ошибка, если вводить невалидный токен

        userService.getUserInfo("Invalid jwt") //Реализовали метод получения информации с фейковым токеном
                .should(hasStatusCode(400));
    }

    @Test
    public void negativeGetUserInfoWithoutJWTTest() {//Метод проверяет ответ от сервера на запрос получения инф-ии без токена

        userService.getUserInfo(null)
                        .should(hasStatusCode(401));
    }

    @Test
    public void positiveChangeUserPassTest() {

        FullUser user = getRandomUser();
        userService.register(user);

        String token = userService.auth(user).asJwt();

        String updatePassValue = "newPassword";

        userService.updatePass(updatePassValue, token)
                .should(hasStatusCode(200))
                .should(hasMessage("User password successfully changed"));


//        FullUser updatedUser = (FullUser) userService.getUserInfo(token).asListName(FullUser.class);

//        Assertions.assertNotEquals(user.getPass(), updatedUser.getPass()); //Сверяем, что старый и новый пароль не схожи
    }

    @Test
    public void negativeChangeAdminPasswordTest() { //Создали данный метод, для изменения пароля админа (должна быть ошибка так как это системный пользователь. Сервер ответит в "info" - "Cant update base users"

        FullUser user = getAdminUser();

        String updatedPasswordValue = "new password"; //Создали переменную со значением нового пароля

        String token = userService.auth(user).asJwt(); //Авторизовались

        userService.updatePass(updatedPasswordValue, token) //вызвали метод updatePass меняющий пароль (в параметрах у него нужно указывать значение нового пароля и токен)
                .should(hasStatusCode(400))
                .should(hasMessage( "Cant update base users"));
    }

@Test
    public void negativeAdminDeleteTest() {

        FullUser user = getAdminUser();
        String token = userService.auth(user).asJwt();

        userService.deleteUser(token) //Указав метод deleteUser в параметрах мы должны указать токен
                .should(hasStatusCode(400))
                .should(hasMessage("Cant delete base users"));
    }

    @Test
    public void positiveDeleteNewUserTest() {

        FullUser user = getRandomUser();


        userService.register(user);

        String token = userService.auth(user).asJwt();

        userService.deleteUser(token)
                .should(hasStatusCode(200))
                .should(hasMessage("User successfully deleted"));
    }

    @Test
    public void positiveGetAllUsersTest() { //создали данный метод, чтобы проверить список всех пользователей

        List <String> users = userService.getAllUsers().asListName(String.class);

        Assertions.assertTrue(users.size()>=3);//Ассерт показывает что число польз-ей больше, или равно 3
    }


}
