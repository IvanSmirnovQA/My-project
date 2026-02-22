package services;

import Models.swagger.FullUser;
import Models.swagger.JwtAuthData;
import assertions.AssertableResponse;
import io.restassured.http.ContentType;

import tests.swaggerTests.UserTests;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;



public class UserService { //"Сырая версия проверок", чтобы улучшить код в классе UserNewTest

    private static AssertableResponse assertableResponse; //Создали объект данного класса, чтобы потом им пользоваться
    private static FullUser fullUser;

    public AssertableResponse register(FullUser user){
        return new AssertableResponse(given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then());
    }

    public AssertableResponse getUserInfo(String jwt){
        return new AssertableResponse(given().auth().oauth2(jwt)
                .get("/user")
                .then());
    }

    public AssertableResponse getUserInfo(){
        return new AssertableResponse(given()
                .get("/user")
                .then());
    }

    public AssertableResponse updatePass(String newPassword, String jwt){
        Map<String, String> password = new HashMap<>();
        password.put("password", newPassword);

        return new AssertableResponse(given().contentType(ContentType.JSON)
                .auth().oauth2(jwt)
                .body(password)
                .put("/user")
                .then());
    }

    public AssertableResponse deleteUser(String jwt){ //нужно указывать токен для удаления
        return new AssertableResponse(given().auth().oauth2(jwt)
                .delete("/user")
                .then());
    }

    public AssertableResponse auth(FullUser fullUser){
        JwtAuthData data = new JwtAuthData(fullUser.getLogin(), fullUser.getPass());
        return new AssertableResponse(given().contentType(ContentType.JSON)
                .body(data)
                .post("/login")
                .then());
    }

    public AssertableResponse getAllUsers(){
        return new AssertableResponse(given()
                .get("/users")
                .then());
    }
}