package tests;

import Models.People;
import TestUtils.JsonHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestWithCat {
    @Test
    public void testWithCat () throws IOException {
        People people = JsonHelper.fromJson("src/test/resources/stas.json", People.class);


    }


}
