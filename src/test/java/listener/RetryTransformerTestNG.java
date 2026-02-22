package listener;

import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryTransformerTestNG implements IAnnotationTransformer { //создали данный класс "преобразователь"Б чтобы он предоставлял нужный формат сущностей для файла "testng.xml"

    @Override
    public void transform(
            ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryListenerTestNG.class);

    }

}
