package junit5.selenoid;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD}) //Данной аннотацией указываем где мы сможем пользоваться созданной аннотацией BrowserType (в пакетах, или методах, или классах и т.д.)//В параметрах аннотации указали куда будем вставлять нашу аннотацию
@Retention(RetentionPolicy.RUNTIME)//Данная аннотация нужна, чтобы указать, на каком этапе (компиляции или выполнения) аннотация будет доступна.//В пар-ах указали, что аннотация должна жить во время выполнения программы
public @interface BrowserType {

    Browser browser();
    boolean isRemote() default true; //Проинициализировали переменную remote (будет указателем как запускать тест-если true то локально) И установили ей значение true по дефолту

    enum Browser {
        FIREFOX, CHROME, OPERA


    }
}
