package tests;

import Models.Cat;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TestWithDebugger {



    @Tag("It is a tag")
    @Test
    public void testCatWithLombok() {



        Cat cat = new Cat("Barsik", "Egypt",5, true); //сразу создали кота с параметрами

        Cat cat1 = new Cat(); //создали нового кота чтобы с помощью вызова методов указать его параметры (код, который представлен снизу)
        cat1.setName("Murzik");
        cat1.setModel("Street cat");
        cat1.setAge(3);
        cat1.setBlack(true);


        Cat cat2 = new Cat("Ivan", "Pers", 20, true);


        int realCatAge = cat2.getAge() + 10;
        System.out.println(realCatAge);

    }
}
