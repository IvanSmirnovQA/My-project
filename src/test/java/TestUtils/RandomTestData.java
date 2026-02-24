package TestUtils;

import Models.swagger.*;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class RandomTestData { //Создали данный класс для генерации рандомных

    private static Faker faker = new Faker(); //Инициализировали данную переменную для генерации рандомных данных (рандомную книгу, страну, кошку и т.д.)в

    private static Random random = new Random();

    static int randomNumber = Math.abs(random.nextInt()); //Math.abs() — это метод, который возвращает не отрицательное число (а в параметре метода указано получение рандомного числа)

    public static GamesItem getRandomGame() {
        SimilarDlc similarDlc = SimilarDlc.builder()
                .isFree(false)
                .dlcNameFromAnotherGame(faker.funnyName().name())
                .build();

        DlcsItem dlcsItem = DlcsItem.builder()
                .rating(faker.random().nextInt(10))
                .price(faker.random().nextInt(1, 500))
                .description(faker.funnyName().name())
                .dlcName(faker.dragonBall().character())
                .isDlcFree(false)
                .similarDlc(similarDlc).build();


        Requirements requirements = Requirements.builder()
                .ramGb(faker.random().nextInt(4, 16))
                .osName("Windows")
                .hardDrive(faker.random().nextInt(30, 70))
                .videoCard("NVIDEA")
                .build();


        return GamesItem.builder()
                .requirements(requirements)
                .genre(faker.book().genre())
                .price(random.nextInt(400))
                .description(faker.funnyName().name())
                .company(faker.company().name())
                .isFree(false)
                .title(faker.beer().name())
                .rating(faker.random().nextInt(10))
                .publishDate(LocalDateTime.now().toString())
                .requiredAge(random.nextBoolean())
                .tags(Arrays.asList("shooter", "quests"))
                .dlcs(Collections.singletonList(dlcsItem))
                .build();
    }

    public static FullUser getRandomUserWithGames() {
        int randomNumber = Math.abs(random.nextInt());
        GamesItem gamesItem = getRandomGame();
        return FullUser.builder()
                .login(faker.name().username() + randomNumber)
                .pass(faker.internet().password())
                .games(Collections.singletonList(gamesItem))
                .build();
    }

    public static FullUser getRandomUser() {
        int randomNumber = Math.abs(random.nextInt());
        return FullUser.builder()
                .login("threadQATestUser" + randomNumber)
                .pass("passwordCOOL")
                .build();
    }

    public static FullUser getAdminUser() {
        return FullUser.builder()
                .login("admin")
                .pass("admin")
                .build();
    }
}