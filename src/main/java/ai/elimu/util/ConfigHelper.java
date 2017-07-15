package ai.elimu.util;

import java.util.Properties;
import ai.elimu.web.context.EnvironmentContextLoaderListener;

public class ConfigHelper {

    public static String getProperty(String key) {
        String value = null;

        Properties properties = EnvironmentContextLoaderListener.PROPERTIES;
        value = properties.getProperty(key);

        return value;
    }

    public static boolean getBooleanProperty(String key) {
        return Boolean.valueOf(getProperty(key));
    }
}
