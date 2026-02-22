package services;

import assertions.AssertableResponse;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;

import static io.restassured.RestAssured.given;

public class FileService {
    public AssertableResponse downLoadImage() {
        return new AssertableResponse(given().get("/api/files/download").then());// Благодаря "then()"  метод превращает Response в ValidatableResponse, а потом превращает в AssertableResponse
        //Метод отправляет GET запрос, получает ответ и оборачивает его AssertableResponse
 }

    public AssertableResponse downLoadLstFile() {
        return new AssertableResponse(given().get("/api/files/downloadLastUploaded").then()); // Благодаря "then()"  метод превращает Response в ValidatableResponse, а потом превращает в AssertableResponse
        //Метод отправляет GET запрос, получает ответ и оборачивает его AssertableResponse
         }

         @SneakyThrows
    public AssertableResponse uploadFile(File file) {//В параметрах метода указали, что будем передавать file
        return new AssertableResponse(given()
                .contentType(ContentType.MULTIPART) //Указывает на то что запрос будет отправлен в формате MULTIPART (формате HTTP-запроса, который позволяет передавать файлы и разделять данные на части)
                .multiPart("file", "myFile", Files.readAllBytes(file.toPath())) //Строка указывает какой именно файл мы оправляем. Словом "file" мы указываем имя параметра формы(но должно совпадать с тем, что ожидает сервер), "myFile" - Имя файла, которые мы передаём в запросе, Третьим параметром получаем байты для отправки запроса
                .post("/api/files/upload").then()); // Благодаря "then()"  метод превращает Response в ValidatableResponse, а потом превращает в AssertableResponse
        //Метод отправляет POST запрос, получает ответ и оборачивает его AssertableResponse
    }



}
