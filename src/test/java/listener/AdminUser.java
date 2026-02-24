package listener;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // данная аннотация говорит "Эту аннотацию можно ставить ТОЛЬКО на параметры метода."
@Retention(RetentionPolicy.RUNTIME) //Данная аннотация указывает "как именно будет запускаться аннотация"
public @interface AdminUser {
    //Данный интерфейс является одним большим параметром, благодаря аннотации ( в их мы указываем что данный интерфейс является (параметром)

}
