package junit5.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SeleniumTests {


    private WebDriver driver;

    private String downLoadFolder = System.getProperty("user.dir") + File.separator + "build" + File.separator + "downloadFiles"; //Данная строка кода задаёт путь к папке для загрузки файлов. В "" указаны названия папок, которые мы создаём, а File.separator добавляет разделитель /\(в зависимости от ОС)

    @BeforeAll
    public static void downloadDriver() {

        WebDriverManager.chromedriver().setup(); //Проинициализировали драйвера
    }


    @BeforeEach
    public void setUp() {

        //Ниже прописали доп. настройки
        ChromeOptions options = new ChromeOptions(); //Данный класс из Selenium, который позволяет настроить поведение Chrome перед запуском.
        Map<String, String> prefs = new HashMap<>(); //Создали хэш карту <у которой ключ будет с типом данных String, а значение также в формате String> //Придали название данной хэш карте "prefs"
        prefs.put("download.default_directory", downLoadFolder); //Указали название ключа, куда будет сохраняться загружаемый файл, а downloadFolder-это значение, которое мы придали ключу (раннее создали downloadFolder и придали ему значение)
        options.setExperimentalOption("prefs", prefs);//Prefs - "Это дополнительные настройки для запуска" Пендрак. Метод setExperimentalOption создаёт экспериментальные опции
        //options.addArguments("--headless"); //Указали чтобы были осуществлены действия в хроме без открытия окна браузера

        driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1920, 1080)); //Указали размеры для открытия браузера
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20)); //Инициализировали загрузку страницы (до 20 сек.)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20)); //Инициализировали загрузку ВСЕХ элементов для взаимодействия с ними( до 20 сек)
        String downloadPath = System.getProperty("user.dir") + "//downloads";

    }


    @AfterEach
    public void tearDown() {
        //driver.close();//Закроет и браузер и процесс работы
        //driver.quit()//Только закроет браузер
    }

    @Test
    public void simpleUiTest() {
        //        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe"); //Инициализировали свойства(конфиги) браузера - указали путь, в котором лежит нужная версия браузера
        //WebDriver driver = new ChromeDriver(options); //Проинициализировали драйвер хрома (В параметрах указали конфигурации, указанные с помощью ChromeOptions)

        driver.get("https://cis.boxraw.com/");

        String actualTitle = driver.getTitle(); //Проинициализировали переменную у которой значение - получение названия страницы
        String expectedTitle = "BOXRAW Россия и СНГ | Официальный интернет-магазин"; //Указали ожидаемое название страницы

        Assertions.assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void simpleFormTest() {
        driver.get("http://85.192.34.140:8081/");

        String expectedName = "Ivan Smirnov";
        String expectedEmail = "ivan@mail.ru";
        String expectedCurrentAddress = "Moscow";
        String expectedPermanentCurrentAddress = "SPB";

        WebElement elementsCard = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elementsCard.click();

        WebElement elementsTextBox = driver.findElement(By.xpath("//span[text()='Text Box']"));
        elementsTextBox.click();

        WebElement username = driver.findElement(By.id("userName"));
        WebElement userEmail = driver.findElement(By.id("userEmail"));
        WebElement userCurrentAddress = driver.findElement(By.id("currentAddress"));
        WebElement userPermanentAddress = driver.findElement(By.id("permanentAddress"));
        WebElement submit = driver.findElement(By.id("submit"));

        username.sendKeys(expectedName);
        userEmail.sendKeys(expectedEmail);
        userCurrentAddress.sendKeys(expectedCurrentAddress);
        userPermanentAddress.sendKeys(expectedPermanentCurrentAddress);
        submit.click();

        WebElement nameNew = driver.findElement(By.id("name"));
        WebElement emailNew = driver.findElement(By.id("email"));
        WebElement currentAddressNew = driver.findElement(By.xpath("//p[@id='currentAddress']"));
        WebElement permanentAddress = driver.findElement(By.xpath("//p[@id='permanentAddress']"));

        String actualName = nameNew.getText();
        String actualEmail = emailNew.getText();
        String actualCurrentAddress = currentAddressNew.getText();
        String actualPermanentAddress = permanentAddress.getText();

        Assertions.assertTrue(actualName.contains(nameNew.getText()));
        Assertions.assertTrue(actualEmail.contains(emailNew.getText()));
        Assertions.assertTrue(actualCurrentAddress.contains(currentAddressNew.getText()));
        Assertions.assertTrue(actualPermanentAddress.contains(permanentAddress.getText()));
    }

    @Test
    public void uploadTest() {
        //Данный метод создан для загрузки файла на страницу

        driver.get("http://85.192.34.140:8081/");

        WebElement elements = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elements.click();

        WebElement uploadAndDownload = driver.findElement(By.xpath("//span[text()='Upload and Download']"));
        uploadAndDownload.click();

        WebElement uploadBtn = driver.findElement(By.xpath("//input[@id='uploadFile']"));
        uploadBtn.sendKeys(System.getProperty("user.div") + "src/test/resources/threadqa.jpeg"); //Данный методом мы указываем путь к файлу, чтобы загрузить файл

        WebElement uploadedFakePath = driver.findElement(By.xpath("//p[@id='uploadedFilePath']"));
        Assertions.assertTrue(uploadedFakePath.getText().contains("threadqa.jpeg")); //Создали ассерт на проверки того что в пути загруженного на платформу файла содержится "threadqa.jpeg"


    }


    @Test
    public void testDownload() {
        driver.get("http://85.192.34.140:8081/");

        WebElement elements = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elements.click();

        WebElement uploadAndDownload = driver.findElement(By.xpath("//span[text()='Upload and Download']"));
        uploadAndDownload.click();

//        WebElement downloadBtn = driver.findElement(By.xpath("//a[@id='downloadButton']"));
        WebElement downloadBtn = driver.findElement(By.id("downloadButton"));
        downloadBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); //Создали "ожидатор"
        wait.until(driver -> Paths.get(downLoadFolder, "sticker.png")); //Данный код ожидает скачивание файла - как только файл загрузится ожидание закончится (Paths.get создаёт объект типа Path, а в параметрах мы указали реализацию метода и название файла)

        File file = new File("build/downloadFiles/sticker.png");//Проинициализировали переменную, придав ей значение в виде пути к скаченному файлу (в дальнейшем сможем исп-ть файл как захотим)

        Assertions.assertTrue(file.length() != 0); //Проверка на то что размер файла не равен 0
        Assertions.assertNotNull(file);//Создали ассерт на проверку существования скаченного файла (что он не равен нулю)
    }


}
