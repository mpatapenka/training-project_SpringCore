package by.epam.maksim.movietheater.util;

import com.google.gson.Gson;

import java.io.Reader;
import java.lang.reflect.Type;

public final class JsonParser {
    private static Gson GSON = new Gson();

    private JsonParser() { }

    public static <T> T convertJsonStringToObject(Reader reader, Type type) {
        return GSON.fromJson(reader, type);
    }
}