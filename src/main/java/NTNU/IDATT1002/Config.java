package NTNU.IDATT1002;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Application Configuration Singleton for parsing hibernate persistence properties and Google Maps API key.
 * Provides a single global access point to the applications {@link EntityManagerFactory}.
 */
public class Config {

    private static EntityManagerFactory entityManagerFactory;
    private static Map<String, Object> configOverrides = new HashMap<>();
    private static Properties properties = loadProperties();
    private static Logger logger = LoggerFactory.getLogger(Config.class);

    private Config() {
    }

    public static String getGoogleApiKey() {
        return properties.getProperty("GOOGLE_API_KEY");
    }

    /**
     * Retrieve a new {@link EntityManager} instance.
     * An {@link EntityManagerFactory} is not created until it is requested.
     *
     * @return an entity manager instance
     */
    public static EntityManager createEntityManager() {
        if (entityManagerFactory == null)
            configureEntityManagerFactory();

        return entityManagerFactory.createEntityManager();
    }

    /**
     * Parse properties and create the entity manager
     */
    private static void configureEntityManagerFactory() {
        parseConfigProperties();
        createEntityManagerFactory();
    }

    /**
     * Parse properties and override config.
     */
    private static void parseConfigProperties() {
        configOverrides.put("javax.persistence.jdbc.driver", properties.getProperty("DB_DRIVER"));
        configOverrides.put("javax.persistence.jdbc.url", properties.get("DB_URL"));
        configOverrides.put("javax.persistence.jdbc.user", properties.get("DB_USER"));
        configOverrides.put("javax.persistence.jdbc.password", properties.get("DB_PASSWORD"));
        configOverrides.put("hibernate.dialect", properties.get("DB_DIALECT"));
    }

    /**
     * Create an {@link EntityManagerFactory} with configurations overrides.
     */
    private static void createEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplication", configOverrides);
    }

    /**
     * Load properties from configured properties file. Defaults to config.properties.
     *
     * @return the properties
     */
    private static Properties loadProperties() {
        String configFile = System.getProperty("config_properties", "config.properties");

        Properties environmentProperties = new Properties();
        try (InputStream input = new FileInputStream(configFile)) {
            environmentProperties.load(input);
        } catch (IOException ex) {
            logger.error("[x] Failed to load config properties", ex);
        }

        return environmentProperties;
    }

}
