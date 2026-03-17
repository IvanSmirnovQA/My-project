package tests;

import TestUtils.XlsReader;
import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.$x;

public class SelenideFileTests {


    @Test
    public void readPdfTest() throws IOException {

        File pdfFile = new File("src/test/resources/Сертификат YR.pdf");
        PDF pdfReader = new PDF(pdfFile);
        String textFromPdfFile = pdfReader.text;//Данным методом мы получаем текст из pdf файла
        Assertions.assertTrue(textFromPdfFile.contains("аллею")); //
    }

    @Test
    public void pdfFromMoonDownload() throws IOException {
        Configuration.pageLoadTimeout = 60000;
        Selenide.open("https://www.moon.ru/pages/order-accept");
        File pdfFile = $x("//a[text()='Постановление Правительства РФ от 31 декабря 2020 г. №2463']").download();
        PDF pdfReader = new PDF(pdfFile);
        Assertions.assertEquals("Adobe PDF Library 11.0", pdfReader.author);//Сверяем ожидаемого и фактического автора 
    }

    @Test
    public void numberOfPageTest() throws IOException {
        File polis = new File("src/test/resources/OMODA C5 хэтчбек 2023 года.pdf");
        PDF reader = new PDF(polis);
        int numberOfPage = 6;
        Assertions.assertEquals(numberOfPage, reader.numberOfPages);
    }

    @Test
    public void readXlsxTest() throws Exception {
        File xlsx = new File("src/test/resources/ТаблицаExcel.xlsx");
        XlsReader xlsxReader = new XlsReader(xlsx); //Проинициализировали "читальщика"
        String [][] data =  xlsxReader.getSheetData(); //Вызывая метод getSheetData мы получаем двумерный массив

        Assertions.assertTrue(xlsxReader.isSheetContainsStringStream("France"));
    }
}
