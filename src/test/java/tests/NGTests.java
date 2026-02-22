package tests;

import junit5.CalcSteps;
import Models.People;
import io.qameta.allure.testng.AllureTestNg;
import listener.RetryListenerTestNG;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(AllureTestNg.class)
public class NGTests {
    @BeforeSuite //данная аннотация указывает что это код, который выполняется ОДИН РАЗ ПЕРЕД запуском ВСЕХ тестов TestNG в текущем suite
    public void setAnalyzer (ITestContext context){ //в параметрах теста указали содержимое теста - "контекст"
        for (ITestNGMethod testMethod : context.getAllTestMethods()) {
            testMethod.setRetryAnalyzerClass(RetryListenerTestNG.class);
        }
    }

    //@Test (retryAnalyzer = RetryListenerTestNG.class) //указали в аннотации класс "RetryListenerTestNG", который будем использовать для перезапуска теста
    @Test(groups = {"sum1"})
    public void sumTestNGTest () {
        CalcSteps calcSteps = new CalcSteps();
        Assert.assertTrue(calcSteps.isPositive(16)); //создали метод, который возвращает True, если число позитивное и False, если число негативное

    }
    @Test(groups = {"sum2"})
    public void sumTestNGTest2 () {
        CalcSteps calcSteps = new CalcSteps();
        Assert.assertTrue(calcSteps.isPositive(10)); //создали метод, который возвращает True, если число позитивное и False, если число негативное
    }




    @DataProvider (name = "testUsers") //данная аннотация позволяет запускать параметризированные тесты с разными данными
    public Object [] dataWithUsers () { //объявили массив данных класса Object (потому что тип "Object" это может быть хоть класс, хоть число, хоть строчка
        People stas = new People("Stas", 25, "male");
        People katya = new People("Katya", 30, "female");
        People oleg = new People("Sasha", 35, "male");
        return new Object[] {stas, katya, oleg}; //возвращаем массив данных с объектами "stas", "katya", ""oleg

    }



    @Test (dataProvider = "testUsers")
    public void testUsersWithRole(People people) { //создали данную аннотацию, как пример параметризированных тестов в TestNG
        System.out.println(people.getName());
        Assert.assertTrue(people.getAge()>20); //создали тест, которые возвращает True, если возраст всех сущностей класса people больше 20
        Assert.assertTrue(people.getName().contains("a")); //создали тест, которые возвращает True, если "name" всех сущностей класса people содержат "а"


    }

}
