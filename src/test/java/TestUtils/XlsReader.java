package TestUtils;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class XlsReader {

    private final File xlsFile;

    private XSSFSheet sheet; //С помощью объекта данного класса мы сможем работать с листом excel файла

    private XSSFWorkbook book; //С помощью объекта данного класса мы сможем работать с Excel файлами

    private String sheetName; //Данная переменная создана, чтобы в дальнейшем использовать в дальнейшем для работы со страницами



    //Реализовали перегрузку метода, чтобы работать с листом с индексом 0
    public XlsReader(File xlsFile) throws IOException { //throws IOException — объявление, что метод может выбросить ошибку ввода/вывода.
        this.xlsFile = xlsFile;
        try {
            FileInputStream fs = new FileInputStream(xlsFile); //Создали объект класса FileInputStream для прочтения файла xlsFile
            book = new XSSFWorkbook(fs); //Проинициализировали объект класса XSSFWorkbook(с его помощью можем работать с Excel файлами) и указали с каким файлом будем работать в параметрах
            sheet = book.getSheetAt(0); //Проинициализировали значение переменной sheet - у book получаем страницу с индексом 0
        } catch (IOException exception){ //IOException может возникнуть например если файл не найден или повреждён, или нет доступа к файлу.
            throw  new IOException("Wrong Title");
        }
    }


    //Реализовали перегрузку метода, чтобы можно было работать с конкретной страницей (будем получать ее по названию)
    public XlsReader(File xlsFile, String sheetName) throws IOException { //throws IOException — объявление, что метод может выбросить ошибку ввода/вывода.
        this.xlsFile = xlsFile;
        try {
            FileInputStream fs = new FileInputStream(xlsFile); //Создали объект класса FileInputStream для прочтения файла xlsFile
            book = new XSSFWorkbook(fs); //Проинициализировали объект класса XSSFWorkbook(с его помощью можем работать с Excel файлами) и указали с каким файлом будем работать в параметрах
            sheet = book.getSheet(sheetName); //Проинициализировали значение переменной sheet - у book получаем страницу по определённому названию
        } catch (IOException exception){ //IOException может возникнуть например если файл не найден или повреждён, или нет доступа к файлу.
            throw  new IOException("Wrong Title");
        }
    }

    private String cellToString(XSSFCell cell) throws Exception { //Exception - выбросится исключение, если ячейка имеет неизвестный тип
        Object result = null; //Почему Object? - Потому что значение Excel может быть разных типов, а Object может хранить любой тип данных
        CellType type = cell.getCellType(); //С помощью метода getCellType получаем тип ячейки//Тип переменной CellType - это изначально ENUM
        switch (type) { //"Если тип"
            case NUMERIC: //"Если тип NUMERIC"
                result = cell.getNumericCellValue();
                break;
            case STRING://"Если тип String"
                result = cell.getStringCellValue();
                break;
            case FORMULA://"Если тип Формула - вернёт саму формулу"
                result = cell.getCellFormula();
                break;
            case BLANK://Если значение "пустота"
                result = "";//Запишется пустота
                break;
            default:
                throw new Exception("Ошибка чтения ячейки");
        }
        return result.toString();//Так как тип переменной result - класс Object, у него есть метод toString, который превращает значение в строковое значение
    }

    private int xlsCountColumn(){
        return sheet.getRow(0).getLastCellNum(); //.getRow-Возвращает указанную строку//.getFirstCellNum-получаем индекс последний ячейки, т.е. так мы узнаем количество ячеек в строке
    }

    private int xlsCountRow(){
        return sheet.getLastRowNum() + 1; //Данный методом получаем индекс последней строки и прибавляем 1 - Так мы узнаем количество строк (первую строку не считаем)
    }

    public String[][] getSheetData() throws Exception{

        int numberOfColumn = xlsCountColumn(); //Получаем количество колонок
        int numberOfRows = xlsCountRow();//Получаем количество строк
        String [][] data = new String[numberOfRows - 1][numberOfColumn]; //Создаём двумерный массив со строковыми данными//Почему numberOfRows - 1? - первая строка это заголовок(не учитываем её)
        for (int i = 1; i < numberOfRows; i++){
            XSSFRow row = sheet.getRow(i); //Получаем строку (так как в цикле там перебираются все строки, то получим все)
            XSSFCell cell = row.getCell(i); //Получаем ячейку (в цикле перебираются все ячейки, поэтому получим все)
            if (cell==null){
                break;
            }
            String value = cellToString(cell);//Преобразовываем значение ячеек (в методе cellToString полностью реализована логика преобразования с помощью switch)
            data[i - 1][i] = value; //Сохраняем значения в массив
        }
        //data = deleteNulls(data)
        return data;
    }

    public boolean isSheetContainsStringStream(String expected) throws Exception {
        return Arrays.stream(getSheetData())
                .flatMap(Arrays::stream)
                .anyMatch(x->x.contains(expected)); //С помощью данного метода мы сверяем, что полученные значения имеют строковый тип данных
    }



    private String [] [] deleteNulls(String[][] oldArray){
        return Arrays.stream(oldArray)
                .filter(row -> Arrays.stream(row).anyMatch(Objects::nonNull)) //С помощью метода .filter реализуем фильтрацию (оставляем те строки, где значения не 0); "Objects::nonNull"- проверка на то что проверяемые объекты не равны 0
                .toArray(String[][]::new);
    }




    public String[][] getSheetData(String sheetName) throws Exception{

        int numberOfColumn = xlsCountColumn(); //Получаем количество колонок
        int numberOfRows = xlsCountRow();//Получаем количество строк
        String [][] data = new String[numberOfRows - 1][numberOfColumn]; //Создаём двумерный массив со строковыми данными//Почему "numberOfRows - 1" - первая строка это заголовок(не учитываем её)
        for (int i = 1; i < numberOfRows; i++){
            XSSFRow row = book.getSheet(sheetName).getRow(i);//Получаем строку (так как в цикле там перебираются все строки, то получим все)
            XSSFCell cell = row.getCell(i); //Получаем ячейку (в цикле перебираются все ячейки, поэтому получим все)
            String value = cellToString(cell);//Преобразовываем значение ячеек (в методе cellToString полностью реализована логика преобразования с помощью switch)
            data[i - 1][i] = value; //Сохраняем значение каждой ячейки в массив
        }
        return data;
    }




}
