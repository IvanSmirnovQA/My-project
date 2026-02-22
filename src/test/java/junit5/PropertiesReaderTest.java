package junit5;

import Models.Settings;
import TestUtils.JsonHelper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class PropertiesReaderTest {

    @Test
    @SneakyThrows //данная аннотация позволяет методу выбрасывать Exceptions без необходимости их объявлять в сигнатуре метода через try-catch
    public void simpleReaderTest() throws FileNotFoundException {

        Properties properties = new Properties(); //Создали объект типа "Properties" - класса, который позволяет хранить и управлять конфигурациями в программе
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/project.properties"); //Создали "читателя" и указали путь чего именно он должно почитать
        properties.load(fileInputStream); // код загружает содержимое файла, путь к которому указан в конструкторе (в параметрах)

        String url = properties.getProperty("url"); //Создали переменную в которой указали значение - получение значения из ключа "url" (ключ и значение этого ключа находится в файле project.properties)
        Boolean isProduction = Boolean.parseBoolean(properties.getProperty("is_production")); // метод "parseBoolean" класса Boolean принимает String и возвращает примитив boolean
        int threads = Integer.parseInt(properties.getProperty("threads")); // метод "parseInt" класса Integer принимает String и возвращает примитив int


        System.out.println(url);
        System.out.println(isProduction);
        System.out.println(threads);
        //выводят в консоль данные из файла "project/properties"


    }

    @Test
    @SneakyThrows
    public void jacksonReader () {
        Properties properties = new Properties(); //
        FileInputStream fs = new FileInputStream("src/test/resources/project.properties");
        properties.load(fs);

        String json = JsonHelper.toJson(properties); //преобразовали информацию из файла с помощью JsonHelper в формат Json
        System.out.println(json);


        Settings settings = JsonHelper.fromJson(json, Settings.class);

        System.out.println(settings.getUrls());
        System.out.println(settings.getIsProduction());
        System.out.println(settings.getThreads());

    }

}
