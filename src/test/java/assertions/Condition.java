package assertions;

import io.restassured.response.ValidatableResponse;

public interface Condition { //создали данный интерфейс, чтобы он был как проверка (будем его вызывать в других классах
    //В дальнейшем мы будем обязаны его имплементировать

    void check (ValidatableResponse response); //после имплементации данного интерфейса нужно будет реализовать данный метод

}
