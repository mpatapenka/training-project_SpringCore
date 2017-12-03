package by.epam.maksim.movietheater.util;

public final class ReflectionUtils {

    private ReflectionUtils() { }

    public static Class<?> loadClassForName(String type) {
        try {
            return Class.forName(type);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Class " + type + " wasn't found!", e);
        }
    }

}