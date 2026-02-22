package assertions.conditions;

import Models.swagger.Info;
import assertions.Condition;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;

import static org.hamcrest.Matchers.equalTo;


@RequiredArgsConstructor
public class MessageCondition implements Condition {

    private final String expectedMessage; //создали данную константу со смыслом как "сообщение на проверку"


    @Override
    public void check(ValidatableResponse response) {

        Info info = response.extract().jsonPath().getObject("info", Info.class); //Извлекает из JSON-ответа объект, находящийся по пути "info", и преобразует его в Java-объект класса Info.
        Assertions.assertEquals(expectedMessage, info.getMessage());
        //Сверху и снизу представлены две разны реализации проверки
        //response.body("info.message", equalTo(expectedMessage)); //Проверяет, что в JSON-ответе, а именно в теле по пути info.message находится значение, равное expectedMessage

    }
}
