package jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    private PropertiesUtil() {
    }

    static {
        loadproperties();
    }

    public static String getKey(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadproperties() {
        try (InputStream stream =
                     PropertiesUtil.class
                             .getClassLoader()
                             .getResourceAsStream("application.properties")) {
            PROPERTIES.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
