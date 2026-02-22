package tests;

import Models.People;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TestWithMethodSource {

    private static Stream<Arguments> People (){
        return Stream.of(
                Arguments.of(new People("Stas", 28, "male")),
                Arguments.of(new People("Sasha", 20, "female")),
                Arguments.of(new People("Ivan", 26, "male"))
        );




    }

    @ParameterizedTest //Аннотация, указывающая, что это параметризированный тест
    @MethodSource("People") // Данные берутся из статического метода класса (в скобках указали метод откуда будет браться информация)
    public void paramsTest(People people)  {
        System.out.println (people.getName() + " " + people.getAge() + " " + people.getSex());
        Assertions.assertTrue(people.getName().contains("a"));
        Assertions.assertTrue(people.getSex().contains("male"));



    }
}
