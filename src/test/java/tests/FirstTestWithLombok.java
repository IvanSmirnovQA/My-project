package tests;

import Models.Cat;
import org.junit.jupiter.api.Test;

public class FirstTestWithLombok {

    @Test
    public void testCatWithLombok() {

        Cat cat = new Cat("Barsik", "Egypt",5, true); //сразу создали кота с параметрами

        Cat cat1 = new Cat(); //создали нового кота чтобы с помощью вызова методов указать его параметры (код, который представлен снизу)
        cat1.setName("Murzik");
        cat1.setModel("Street cat");
        cat1.setAge(3);
        cat1.setBlack(true);


        Cat cat2 = Cat.builder()
                .age(6)
                .model("Persian")
                .isBlack(true)
                .build(); /*создали объект "на ходу"(без сложных конструкций) с помощью аннотации Builder
         на примере выше показано, что не обязательно задавать все параметры в конструкторе
         а, если мы не указали како-то параметр, то значение неуказанного параметра будет "null"*/



        Cat cat3 = new Cat("Kitty", "England", 8, true);
;

        System.out.println(cat);
        System.out.println(cat1);
        System.out.println(cat2);
        System.out.println(cat3);



    }

}
