package junit5;

import io.qameta.allure.Step;

public class CalcSteps { //создали данный класс, как прослойку, чтобы с его помощью осуществлять логирование - описывание тестов в отчеты Allure



    @Step ("Складываем числа {a} + {b}")//Данная аннотация позволяет описывать тестовые сценарии в Allure отчете
    public int sum (int a, int b){
    return a+b;

}


@Step ("Проверяем что число {result} больше, чем 0 ")
public boolean isPositive (int result) {
        return result > 0;
    }


}
