package tests.swaggerTests;

import io.qameta.allure.Attachment;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.FileService;
import services.UserService;

import static assertions.Conditions.hasMessage;
import static assertions.Conditions.hasStatusCode;


import java.io.File;
import java.util.Random;

public class FileTests {
    private static FileService fileService; //создали объект из класса UserService

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "http://85.192.34.140:8080/";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        //RequestLoggingFilter() — Это объект-фильтр, который подключается в Rest Assured и перед отправкой выводит детали запроса (HTTP-метод, headers и т.д.) в консоль - логирует запрос
        //ResponseLoggingFilter() — это фильтр, который получает ответ (status code, headers, response body-тело ответа) - логирует ответ. С его помощью, мы детально увидим что произошло не так.
        fileService = new FileService(); //проинициализировали fileService, чтобы каждый раз его не указывать в тестах
        RestAssured.filters(new AllureRestAssured());
    }


    //Данный метод создан, чтобы подкреплялись изображения, которые мы скачиваем, или отправляем в Allure отчет
    @Attachment(value = "downloadedFileName", type = "inage/png") //Аннотация позволяет прикрепить файл к Allure отчету ("имяФайла", "тип файла"
    private byte[] attachFile(byte[] bytes){ //Передаём массив байтов, так как изображение - это набор  байтов
        return bytes;
    }


    @Test
    public void positiveDownloadTest() {
        byte [] file = fileService.downLoadImage().asResponseName().asByteArray(); //Создали сущность из массива байтов и придали ей значение в виде скачивания картинки
        File expectedFile = new File("src/test/resources/threadqa.jpeg"); //Придали значение expectedFile в виде указанного пути к картинке threadqa
        attachFile(file); //Вызвали метод attachFile, чтобы подкрепить в Allure отчет скаченный файл (file)
        Assertions.assertEquals(expectedFile.length(), file.length); //С помощью данной проверки мы проверяем размер скачанного файла идентичен размеру файла который находится в репозитории
    }

    @Test

    public void positiveUploadTest () { //Данный метод создан, чтобы загрузить сущность на сервер
        File expectedFile = new File("src/test/resources/threadqa.jpeg"); //Придали значение expectedFile в виде указанного пути к картинке threadqa
        fileService.uploadFile(expectedFile)
                .should(hasStatusCode(200))
                .should(hasMessage("file uploaded to server")); //Проверяем что получилось успешно загрузить файл на сервер после чего получим опр-ое сообщение

        byte [] actualFile = fileService.downLoadLstFile().asResponseName().asByteArray(); //Создали сущность из массива байтов и придали ей значение в виде скачивания последней загруженной сущности
        Assertions.assertNotNull(actualFile.length); //Проверяем, что размер actualFile (последняя скачанная сущность) не равно нулю
        Assertions.assertEquals(expectedFile.length(), actualFile.length); //С помощью данной проверки мы проверяем размер скачанного файла с размером actualFile (сущности, которая была скачана в последней раз)
    }
}
