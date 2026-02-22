package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ParametrizedTest {





    @ParameterizedTest //Аннотация, указывающая, что это параметризированный тест
    @CsvSource ({"Stas, 28, male", "Sasha, 20, female", "Ivan, 26, male"}) //указали какие параметры будут передаваться
public void paramsTest(String name, String age, String sex) { //указали какие значения будут в методе
        System.out.println(name + " " + age + " " + sex);
        Assertions.assertTrue(name.contains("a")); //Указали, что переменные "name" должны содержать "а"
        Assertions.assertTrue(age.contains("2")); //Указали, что переменные "age" должны содержать "2"



}




}


