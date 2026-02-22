package listener;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.util.HashSet;
import java.util.Set;

public class RetryListener implements TestExecutionExceptionHandler, AfterTestExecutionCallback { //специально создали данный класс "смотритель"


    private static final int MAX_RETRIES = 3;
    private static final Set<String> failedTestNames = new HashSet<>();


    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {



    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {

    }
}
