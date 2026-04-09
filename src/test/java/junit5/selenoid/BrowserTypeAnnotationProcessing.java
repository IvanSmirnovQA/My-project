package junit5.selenoid;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class BrowserTypeAnnotationProcessing implements BeforeAllCallback, BeforeEachCallback { //implements BeforeAllCallback, BeforeEachCallback - означает что класс обязан реализовать методы данных интерфейсов (а данные интерфейсы служат как "выполнение логики перед всеми тестами" то есть это можно назвать "контекстом" перед тестами
   private static final ExtensionContext.Namespace space = ExtensionContext.Namespace.create(BrowserTypeAnnotationProcessing.class);

    @Override
    public void beforeAll(ExtensionContext context) {
        BrowserType annotation = context.getRequiredTestClass().getAnnotation(BrowserType.class);
        context.getStore(space).put("annotation", annotation);

    }

    @Override
    public void beforeEach(ExtensionContext context)  {
        BrowserType methodAnnotation = context.getRequiredTestMethod().getAnnotation(BrowserType.class);
        BrowserType classAnnotation = context.getStore(space).get("annotation", BrowserType.class);
    }
}
