package assertions;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor //Аннотация генерирует конс-ры для final
public class AssertableResponse { //создали данный класс, чтобы он был "обёрткой" для запроса

    private final ValidatableResponse response; //Объявили "response" — это объект типа ValidatableResponse из Rest Assured.(Он представляет HTTP-ответ, над которым можно выполнять проверки и извлекать данные.)

    public AssertableResponse should(Condition condition){

        condition.check(response);
        return this; //данная командой мы экземпляр текущего класса
    }

    public String asJwt() {
        return response.extract().jsonPath().getString("token"); //Данный метод возвращает строчку с ключом "token"
    }



    public <T> T randomName(Class<T> tClass) { //Здесь созданы дженерики. В параметрах данного метода указали класс дженериков (т.е класс, который может принимать разные входные данные и указали название класса)
        //"<T>" здесь указан, чтобы обозначить, что метод принимает на вход "какой-то" параметр //GPT объяснение "<T> — это объявление параметра типа метода (type parameter). Метод является обобщённым (generic method)"
        //"T" означает, что метод будет возвращать этот же "какой-то" параметр //GPT объяснение "T перед именем метода — это тип возвращаемого значения, который соответствует объявленному параметру типа"
        //"(Class<T>" в параметрах метода означает, что метод принимает объект класса того же типа, который описывает тип "T"
        //"tClass" - название параметра(переменной)"
        //Данный метод обеспечивает строгую типизацию без необходимости явного приведения типов

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

    public <T> List<T> asListName(Class<T> tClass) {
        return response.extract().jsonPath().getList("", tClass);
        //Данный метод извлекает тело ответа и десериализует его в список объектов класса "tClass"
        //".jsonPath()" - Создаёт и возвращает объект JsonPath, содержащий ответ чтобы достать нужные нам данные
        //Преобразует найденный фрагмент в List<T>
        //"" - Пустой путь говорит "Возьми весь корневой JSON и преврати каждый элемент в объект класса tClass"
    }

    public <T> List<T> asListNameWithJasonPath(String jsonPath,  Class<T> tClass) { //"String jsonPath" обозначили создание строковой перменной
        return response.extract().jsonPath().getList(jsonPath, tClass);
        //Данный метод извлекает из JSON-ответа список элементов по указанному пути "jsonPath) и десериализует их в List<T>.


    }
}