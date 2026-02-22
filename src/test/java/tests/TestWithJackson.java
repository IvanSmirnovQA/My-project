package tests;

import Models.People;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class TestWithJackson {


    //ниже идет пример Сериализации

    @Test
    public void firstTestWithJackson() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper(); //Создали экземпляр основного класса Jackson для работы с JSON. Этот объект отвечает за сериализацию и десериализацию
        File file = new File("src/test/resources/stas.json"); //Создали объект с типом "File" с указанием адреса файла
        People people = objectMapper.readValue(file, People.class); //Вызвали метод десериализации, а в скобках указали источник JSON и указали тип объекта, который создаст Jackson
        System.out.println(people.getName());
        System.out.println(people.getAge());
        System.out.println(people.getSex());




        //ниже идет пример Дисериализации

        People sasha = new People("sasha", 20, "female"); //создали новый объект с параметрами в скобках
        String sashaJson = objectMapper.writeValueAsString(sasha); //создали переменную "sashaJson" у которой значение это произведение десериализации объекта "sasha" созданного на предыдущей строке
        System.out.println(sashaJson);
    }


}
