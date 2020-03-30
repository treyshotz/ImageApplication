package NTNU.IDATT1002.database;

import org.hibernate.cfg.Environment;

import javax.persistence.*;
import java.util.*;


/**
 * Entity Manager Configuration Singleton for overriding hibernate persistence properties.
 * Provides a single global access point to the applications entity manager.
 */
public class EntityManagerConfig {

    private static EntityManager entityManager;
    private static Map<String, Object> configOverrides = new HashMap<>();
    private static Properties properties = Environment.getProperties();


    private EntityManagerConfig() {
    }

    /**
     * Retrieve the entity manager instance.
     * An entity manager is not created until it is requested.
     *
     * @return an entity manager instance
     */
    public static EntityManager getEntityManager() {
        if (entityManager == null)
            configureEntityManager();

        return entityManager;
    }

    /**
     * Parse properties and create the entity manager
     */
    private static void configureEntityManager() {
        if (shouldOverrideConfig())
            parseConfigProperties();

        createEntityManager();
    }

    private static boolean shouldOverrideConfig() {
        return Boolean.parseBoolean((String) properties.get("OVERRIDE_DEFAULT_DB_CONFIG"));
    }

    /**
     * Parse properties from resources/hibernate.properties and overrides found values.
     */
    private static void parseConfigProperties() {
        Set<Object> envKeys = properties.keySet();

        for (Object key : envKeys)
            parseProperty(key);
    }

    /**
     * Parse desired values from given key in properties and add them to the configuration overrides.
     *
     * @param key the key to parse
     */
    private static void parseProperty(Object key) {
        switch ((String) key) {
            case "DB_DRIVER":
                configOverrides.put("javax.persistence.jdbc.driver", properties.get(key));
                break;
            case "DB_URL":
                configOverrides.put("javax.persistence.jdbc.url", properties.get(key));
                break;
            case "DB_USER":
                configOverrides.put("javax.persistence.jdbc.user", properties.get(key));
                break;
            case "DB_PASSWORD":
                configOverrides.put("javax.persistence.jdbc.password", properties.get(key));
                break;
            case "DB_DIALECT":
                configOverrides.put("hibernate.dialect", properties.get(key));
                break;
            case "DB_HBM2DDL":
                configOverrides.put("hibernate.hbm2ddl.auto", properties.get(key));
                break;
            default:
                break;
        }
    }

    /**
     * Create an entity manager with configurations overrides.
     */
    private static void createEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ImageApplication", configOverrides);
        entityManager = emf.createEntityManager();
    }
}
