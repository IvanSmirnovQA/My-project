package listener;

import org.testng.*;

public class RetryListenerTestNG implements IRetryAnalyzer, ITestListener { //"Сделали данный класс "Перезапускатор", чтобы реализовывать перезапуск упавших тестов определённое кол-во раз" - Пендрак, ITestListener - "прослушка" для теста

    private final int MAX_RETRIES = 2; //создали переменную константу, чтобы данный код указывает кол-во попыток перезапуска упавших тестов
    private int count = 0; //создали переменную "счетчик" для считывания кол-ва перезапусков

    @Override
    public boolean retry(ITestResult result) {
        if (count < MAX_RETRIES) { //указали условие при котором будет повторяться попытка перезапуска теста (пока счетчик меньше максимального кол-ва попыток
            count++; // указали что счетчик будет увеличиваться на 1 при каждой попытке перезапуска
            return true; //указали, что когда будет возвращаться true (означает что счетчик меньше, чем макс-ое кол-во попыток), то перезапуск будет вновь выполняться
        }
        return false; // указали, что в ином случаем - возвращаем false (означает, что попытки перезапуска прекратятся)
    }

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
    }
}
