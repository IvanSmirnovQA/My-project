package junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalcTest extends CalcSteps {
    @Test
    public void sumTest() {
        CalcTest calcTest = new CalcTest();
        int result = calcTest.sum(1,4);
        boolean isOk = calcTest.isPositive(result);
        Assertions.assertTrue(isOk);
    }
}
