package TestUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public class JsonHelper {
    private static final ObjectMapper mapper = new ObjectMapper();


    @SneakyThrows
    public static <T> T fromJs(String jsjnPath, Class<T> out) {
        String jsonPath = "";
        return mapper.readValue(new File(jsonPath), out);
    }


    @SneakyThrows //Обходит правило Java — пишешь код без throws или try-catch, а исключения всё равно летят наверх
    public static <T> T fromJson (String jsonPath, Class <T> out )  {
    return mapper.readValue(new File(jsonPath), out);
    }


    @SneakyThrows
    public static String toJson(Object object) throws JsonProcessingException {
            return mapper.writeValueAsString(object);
    }







}
