package ladavilada.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by vbobina on 21.07.2017.
 */
public final class ConfigUtil {
    private final static Logger LOGGER = Logger.getLogger(ConfigUtil.class);
    private static final Map<String, String> configProperties = readProperties();

    private ConfigUtil() {
        throw new IllegalAccessError("Utility class");
    }

    private static Map<String, String> readProperties() {
        Map<String, String> props = new HashMap<>();
        try (InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            properties.forEach((key, value) -> props.put((String) key, (String) value));
        } catch (IOException ignored) {
            LOGGER.info("Exception: ", ignored);
        }
        return Collections.unmodifiableMap(props);
    }

    /**
     * Get property string from config.properties file
     *
     * @param name the name
     * @return The method returns null if the property is not found
     */
    public static String getProperty(String name) {
        return configProperties.get(name);
    }
}
