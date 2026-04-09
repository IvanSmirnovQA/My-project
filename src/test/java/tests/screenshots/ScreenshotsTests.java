package tests.screenshots;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideConfig;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ScreenshotsTests {

    private String testName;
    private static File outPutDir; //Создали данный объект, чтобы он служил как директорий для файлов

    @BeforeEach
    public void initializationTestName(TestInfo info){//TestInfo — это объект из JUnit 5, который содержит информацию о текущем тесте.
        testName = info.getTestMethod().get().getName();//Получаем название метода
    }

    @AfterEach
    public void tearDown(){
        Selenide.closeWindow();
        Configuration.browserCapabilities = new SelenideConfig().browserCapabilities(); //С помощью данного кода сбрасываем настройки
    }

    @BeforeAll
    public static void initFolder(){
        outPutDir = new File("build/screenshots"); //Проинициализировали место для хранения скриншотов
        if (!outPutDir.exists()){ //!outPutDir.exists Подразумевает "если папка отсутствует"
            outPutDir.mkdirs(); //В данном цикле "mkdirs" означает, что мы создадим указанную папку, если она отсутствует
        }

    }



    @Test
    public void web1080pTest(){
        Configuration.browserSize = "1920x1080";
        Selenide.open("https://threadqa.ru");
        assertFullScreen();//Данный метод проинициализирован ниже
    }

    @Test
    public void mobileIphoneXrTest(){ //Данный метод запустит сайт с разрешением как у IphoneXR
        Configuration.browserSize = "414x896";
        Selenide.open("https://threadqa.ru");
        assertFullScreen();//Данный метод проинициализирован ниже
    }
    @Test
    public void mobileIphoneXrEmulationTest(){ //Данный метод запустит сайт с эмуляцией устройства IphoneXR
        System.setProperty("chromeoptions.mobileEmulation", "deviceName=iPhone XR");
        Selenide.open("https://threadqa.ru");
        assertFullScreen();//Данный метод проинициализирован ниже
    }

    @Test
    public void mobileIphoneXrSecondVariantEmulationTest() {//В данном методе указали настройки, благодаря которым будет производиться эмуляция устройства
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "IphoneXR"); //Метод .put добавляет пару "ключ → значение" в хэш карту
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("mobileEmulation", mobileEmulation);
        Configuration.browserCapabilities = options; //browserCapabilities это набор параметров, с которыми запускается браузер (т.е. options)

        Selenide.open("https://threadqa.ru");
        assertFullScreen();

    }

    @SneakyThrows
    private void assertFullScreen()  {
        Screenshot screenshot = new AShot() //Создали объект класса AShot для реализации скриншотов
                .shootingStrategy(ShootingStrategies.viewportRetina(3000,0,0, 2))//Методом shootingStrategy мы указываем "как надо делать скриншот" - и указываем что надо делать скриншот всей страницы через прокрутку (1ый пар.-таймаут до прокрутки, 2ой пар.-сколько пикселей срезаем в шапке, 3ий пар.- сколько пикселей срезаем снизу, 4ый пар.-указываем плотность пикселей)
                .takeScreenshot(WebDriverRunner.getWebDriver()); //Методом takeScreenshot делаем скриншот//Указывая webDriver в параметрах мы указываем в каком браузере надо делать скриншот

        File actualScreen = new File(outPutDir.getAbsolutePath() + "/" + testName + ".png");//Таким образом создаем файл с типом данных png (в параметрах указываем директорию нового файла)
        ImageIO.write(screenshot.getImage(), "png", actualScreen); //Данным методом сохраняем изображение в файл//Первым аргументом получаем изображение, вторым указываем тип данных для сох-ия, третьим указываем в какую переменную мы записываем

        File expectedScreen = new File(String.format("src/test/resources/references/%s.png", testName));
        if (!expectedScreen.exists()){ //"Если expectedScreen не существует"
            throw new RuntimeException("No Reference Image, download it from build/screenshoots");//То выкидываем исключение
        }
        assertImages(actualScreen, expectedScreen); //Сравниваем два изображения
    }

    @SneakyThrows
    private void assertImages(File actual, File expected){ //Данный метод сравнивает два изображения
        ImageDiff differ = new ImageDiffer() //Объект данного класса нужен, чтобы сравнивать изображения
                .makeDiff(ImageIO.read(actual), ImageIO.read(expected)) //ImageIO-данный класс нужен для чтения и записи изображений//Сравнивает два изображения и создаёт объект ImageDiff, в котором хранится информация о различиях
                .withDiffSizeTrigger(10); //Данный метод задаёт порог, начиная с которого различие считается значимым //Данный метод исп-ют как порог, чтобы тест не падал из-за мелких отличий
        if (differ.hasDiff()){ //hasDiff проверяет отличия изображений у переменной differ
            BufferedImage diffImage = differ.getMarkedImage(); //Данным методом мы получаем картинку на которой отмечены отличия сравниваемых изображений (BufferedImage это класс, в котором изображение представляется, как массив байтов)
            ByteArrayOutputStream bos = new ByteArrayOutputStream(); //Создали объект класса ByteArrayOutputStream, который записывает данные в массив байтов
            ImageIO.write(diffImage, "png", bos); //Данной строкой сохраняем картинку diffImage сохраняется в формате "png" не в файл, а в поток байтов, проинициализированный в переменной bos
            byte[] image = bos.toByteArray();//.toByteArray достает массив байтов из потока bos (т.е. изображение будет получено в формате массива байтов)
            Allure.getLifecycle().addAttachment("diff", "image/png", "png", image); //Данный код добавляет изображения в Allure отчет
            //"diff"-имя файла, "image/png"-формат, "png"-расширение файла, image-само изображение(массив байтов)
        }

        Assertions.assertTrue(differ.hasDiff());//Проверка на то что differ (сравнитель) имеет различия
    }
}
