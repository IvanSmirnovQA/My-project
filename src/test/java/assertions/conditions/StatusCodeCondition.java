package assertions.conditions;

import assertions.Condition;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;

@RequiredArgsConstructor
public class StatusCodeCondition implements Condition {

    private final Integer statusCode;


    @Override
    public void check(ValidatableResponse response) {
        response.assertThat().statusCode(statusCode); //Здесь не обязателен. Он нужен улучшения читаемости кода и логического разделения этапа проверки.
        //Сверху и снизу представлены альтернативные варианты проверки статус кода
        int actualStatusCode = response.extract().statusCode();
        Assertions.assertEquals(statusCode, actualStatusCode);

    }
}
