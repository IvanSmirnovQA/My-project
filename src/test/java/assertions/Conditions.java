package assertions;

import assertions.conditions.MessageCondition;
import assertions.conditions.StatusCodeCondition;

public class Conditions { //с помощью данного класса будем создавать статичные экземпляры для дальнейшего использования

    public static MessageCondition hasMessage(String expectedMessage) { //данный метод принимает на вход expectedMessage
        return new MessageCondition(expectedMessage);
    }

    public static StatusCodeCondition hasStatusCondition (Integer expectedStatus){
        return new StatusCodeCondition(expectedStatus);
    }

    public static StatusCodeCondition hasStatusCode(Integer expectedStatus){ //Создали экземпляр класса StatusCodeCondition, чтобы использовать метод check в дальнейшем
        return new StatusCodeCondition(expectedStatus); //Возвращаем экземпляр StatusCodeCondition с внутренним конструктором - expectedStatus



    }



}
