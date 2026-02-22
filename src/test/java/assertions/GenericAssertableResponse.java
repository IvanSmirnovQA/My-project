package assertions;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GenericAssertableResponse <T>{ // <T> здесь указывает на заранее созданный класс, в который надо будет извлекать ответ

    private final ValidatableResponse response; //Объявили "response" — это объект типа ValidatableResponse из Rest Assured.(Он представляет HTTP-ответ, над которым можно выполнять проверки и извлекать данные.)

   private final TypeRef<T> clazz;

    public GenericAssertableResponse<T> should(Condition condition){

        condition.check(response);
        return this; //данная командой мы экземпляр текущего класса
    }


    public T asObjectName() {
        return response.extract().as(clazz);
    }

    public T asObjectName(String jsonPath) { //Создали "Перегрузку Метода" - когда одинаковые названия, но разные параметры
        return response.extract().jsonPath().getObject(jsonPath, clazz);
    }


    public <T> T randomName(Class<T> tClass) { //Здесь созданы дженерики. В параметрах данного метода указали класс дженериков (т.е класс, который может принимать разные входные данные и указали название класса)
//        //"<T>" здесь указан, чтобы обозначить, что метод принимает на вход "какой-то" параметр //GPT объяснение "<T> — это объявление параметра типа метода (type parameter). Метод является обобщённым (generic method)"
//        //"T" означает, что метод будет возвращать этот же "какой-то" параметр //GPT объяснение "T перед именем метода — это тип возвращаемого значения, который соответствует объявленному параметру типа"
//        //"(Class<T>" в параметрах метода означает, что метод принимает объект класса того же типа, который описывает тип "T"
//        //"tClass" - название параметра(переменной)"
//        //Данный метод обеспечивает строгую типизацию без необходимости явного приведения типов

        return response.extract().as(tClass);
        //С помощью ".extract()" мы извлекаем данные из "response" (т.е HTTP-ответа)
        //Метод "as(Class<T>)" выполняет десериализацию тела ответа в объект указанного класса (т.е. Объект "Class<T>" - данного класса

        //В совокупности: выполняется извлечение тела HTTP-ответа и его десериализация в объект типа T, определённого через параметр Class<T>, после чего результат возвращается вызывающему коду.
    }
    public <T> T randomName(String jsonPath, Class<T> tClass) { //"String jsonPath" означает, что мы передаём какой-то путь в параметрах (будем использовать и подставлять в других классах)

        return response.extract().jsonPath().getObject(jsonPath, tClass); // в данном случае мы извлекаем объект "jsonPath" в tClass
        //".jsonPath()" Создаёт и возвращает объект JsonPath, который содержит тело ответа в виде структуры, с которой можно работать. (Т.е. Создаёт объект для работы с JSON.)
        //"."getObject(jsonPath, tClass)" - берёт указанный объект, т.е. "jsonPath" и преобразовывает его в объект класса tClass
    }

    public Response asResponseName(){ //Создали объект класса Response (из библиотеки RestAssured) Response — это полноценный объект HTTP-ответа
        return response.extract().response();
        //Данный метод просто возвращает весь HTTP-ответ как есть без преобразования тела в конкретный тип
    }

    public  List<T> asListName() {
        return response.extract().jsonPath().getList("", clazz.getTypeAsClass());
        //Данный метод извлекает тело ответа и десериализует его в список объектов класса "tClass"
        //".jsonPath()" - Создаёт и возвращает объект JsonPath, содержащий ответ чтобы достать нужные нам данные
        //Преобразует найденный фрагмент в List<T>
        //"" - Пустой путь говорит "Возьми весь корневой JSON и преврати каждый элемент в объект класса tClass"
    }

    public  List<T> asListNameWithJasonPath(String jsonPath) { //"String jsonPath" обозначили создание строковой перменной
        return response.extract().jsonPath().getList(jsonPath, clazz.getTypeAsClass());
        //Данный метод извлекает из JSON-ответа список элементов по указанному пути "jsonPath) и десериализует их в List<T>.


    }


}
