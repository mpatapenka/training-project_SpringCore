package by.epam.maksim.movietheater.util;

import by.epam.maksim.movietheater.entity.Auditorium;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ConfigUtils {

    private static final Logger log = LoggerFactory.getLogger(ConfigUtils.class);

    private static final char PROPERTY_NAME_SEPARATOR = '.';
    private static final String AUDITORIUM_NAME_PROPERTY = "name";
    private static final String AUDITORIUM_SEATSCOUNT_PROPERTY = "seatscount";
    private static final String AUDITORIUM_VIPSEATS_PROPERTY = "vipseats";

    private ConfigUtils() { }

    public static Properties readAllProperties(File propertiesDirectory) {
        Properties properties = new Properties();
        if (propertiesDirectory != null && propertiesDirectory.exists() && propertiesDirectory.isDirectory()) {
            Optional<File[]> propertyFiles = Optional.ofNullable(propertiesDirectory
                    .listFiles(file -> file.isFile() && file.canRead() && file.getName().endsWith(".properties")));
            Stream.of(propertyFiles.orElse(new File[0])).forEach(file -> {
                try (Reader reader = new BufferedReader(new FileReader(file))) {
                    properties.load(reader);
                } catch (IOException e) {
                    log.error("Property file '" + file.getName() + "' can't be read, so skip it.", e);
                }
            });
        }
        return properties;
    }

    public static Map<String, Auditorium> parseAuditoriums(Properties properties) {
        Set<String> uniqueAuditoriumNames = properties.stringPropertyNames().stream()
                .map(propertyName -> propertyName.substring(0, propertyName.lastIndexOf(PROPERTY_NAME_SEPARATOR)))
                .collect(Collectors.toSet());

        return uniqueAuditoriumNames.stream()
                .map(prefixName -> buildAuditorium(prefixName, properties))
                .collect(Collectors.toMap(Auditorium::getName, Function.identity()));
    }

    private static Auditorium buildAuditorium(String prefixName, Properties properties) {
        return Auditorium.build(properties.getProperty(prefixName + PROPERTY_NAME_SEPARATOR + AUDITORIUM_NAME_PROPERTY),
                Long.valueOf(properties.getProperty(prefixName + PROPERTY_NAME_SEPARATOR + AUDITORIUM_SEATSCOUNT_PROPERTY)),
                properties.getProperty(prefixName + PROPERTY_NAME_SEPARATOR + AUDITORIUM_VIPSEATS_PROPERTY));
    }

}