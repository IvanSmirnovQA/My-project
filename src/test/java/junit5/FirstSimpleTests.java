package junit5;

import org.junit.jupiter.api.*;

public class FirstSimpleTests {

    @Test //указываем данную аннотация, чтобы потом можно было ее запускать с другими аннотациями "Test"
    
    @BeforeEach //Аннотация нужна, чтобы указывать какую нибудь логику перед каждым тестом - например, придать значение какой-нибудь переменной в рамках конкретно данного теста
    @AfterEach //Аннотация нужна, чтобы указывать какую нибудь логику после выполнения теста после каждого теста
    public void testTwoLessThanThree () {
        int a = 2;
        int b = 3;
        Assertions.assertTrue(b>a, "It's result"); //указали класс "Assertions" с методом "assertTrue" для проверки трушности выражения

    }

    @Test
    @DisplayName("Данная аннотация создана, чтобы давать описание")
    public void randomName() {
        int a = 10;
        int b = 15;
        int sum = a + b;
        Assertions.assertEquals(sum, 25, "It's result of sum two numbers"); //Данный метод "assertEquals" сравнивает ожидаемый и фактический результат


    }
}
