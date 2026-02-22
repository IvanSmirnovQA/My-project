package TestUtils;

import Models.swagger.FullUser;
import com.github.javafaker.Faker;

import java.util.Random;

public class RandomTestData { //Создали данный класс для генерации рандомных

    private static Faker faker = new Faker(); //Инициализировали данную переменную для генерации рандомных данных (рандомную книгу, страну, кошку и т.д.)в

    private static Random random = new Random();

    static int randomNumber = Math.abs(random.nextInt()); //Math.abs() — это метод, который возвращает не отрицательное число (а в параметре метода указано получение рандомного числа)





    public static FullUser getRandomUser() {

        //строчкой ниже мы создаём экземпляр класса FullUser, чтобы с помощью builder указать необходимые для рег-ии данные (так как в док-ии указано, что заполнять необязательно все поля, мы можем указать только некоторые)
        return FullUser.builder()
                .login("RandomUser" + randomNumber)
                .pass("ObiVanKenobi")
                .build();
        //собрали экземпляр объекта FullUser указав лишь обязательные данные для рег-ии, а данные необязательные к заполнению так как мы их не указали должны быть null



    }


    public static FullUser getAdminUser() {
        return FullUser.builder()
                .login("admin")
                .pass("admin")
                .build();
    }


}
