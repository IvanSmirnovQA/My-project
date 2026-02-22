package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class TestWithCsvFileSource {

    @ParameterizedTest //Аннотация, указывающая, что это параметризированный тест
    @CsvFileSource (resources = "/people.csv", delimiter = ',') //указали в каком файле искать параметры - создали файл с параметрами, и указали "delimiter" - разделитель между параметрами
    public void paramsTest(String name, String age, String sex) { //указали какие значения будут в методе
        System.out.println(name + " " + age + " " + sex);
        Assertions.assertTrue(name.contains("a")); //Указали, что переменные "name" должны содержать "а"
        Assertions.assertTrue(age.contains("2")); //Указали, что переменные "age" должны содержать "2"



    }



}
