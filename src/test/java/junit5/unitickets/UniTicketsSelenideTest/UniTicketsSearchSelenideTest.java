package junit5.unitickets.UniTicketsSelenideTest;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class UniTicketsSearchSelenideTest {

    private SelenideElement titleLoader = $x("//div[@class='countdown-title']");

    private SelenideElement priceSelectedMain = $x("//li[@class='price--current']");

    private SelenideElement selectedDayForward = $x("//li[@class='price--current']//a/span[1]");

    private SelenideElement selectedDayBack = $x("//li[@class='price--current']//a/span[3]");

    private ElementsCollection listOfForwardDays = $$x("//div[@class='ticket-action-airline-container']");

    private ElementsCollection listOfBackDays = $$x("//div[@class='ticket-action-airline-container']");

    public UniTicketsSearchSelenideTest assertAllDaysForwardShouldHaveDay(int expectedForwardDay) {
        String day= String.valueOf(expectedForwardDay); //Здесь число преобразуется в строку c помощью метода valueOf
        listOfForwardDays.should(CollectionCondition.containExactTextsCaseSensitive(day)); //Данный подразумевает среди элементов коллекции должен быть элемент, текст которого точно совпадает с указанной строкой
        return this;
    }

    public UniTicketsSearchSelenideTest assertAllDaysBackShouldHaveDay(int expectedBackDay) {
        String day = String.valueOf(expectedBackDay);//Здесь число преобразуется в строку c помощью метода valueOf
        listOfBackDays.should(CollectionCondition.containExactTextsCaseSensitive(day));//Данный подразумевает среди элементов коллекции должен быть элемент, текст которого точно совпадает с указанной строкой
        return this;
    }

    public UniTicketsSearchSelenideTest assertMainDayForward(int expectedDay){
        selectedDayForward.should(Condition.partialText(String.valueOf(expectedDay)));
        return this;
    }

    public UniTicketsSearchSelenideTest asserMainDayBack(int expectedDay) {
        selectedDayBack.should(Condition.partialText(String.valueOf(expectedDay)));
        return this;
    }

    public UniTicketsSearchSelenideTest waitForPage(){ //Данный метод работает как ожидание в тексте прописанного регулярного выражения
        priceSelectedMain.should(Condition.matchText("//d+"));
        return this;
    }

    public UniTicketsSearchSelenideTest waitForTitleDisapear(){
        titleLoader.should(Condition.disappear, Duration.ofSeconds(30)); //Данный метод ожидает, пока элемент исчезнет (пропадет из DOM, либо станет невидимым)
        return this;
    }

}
