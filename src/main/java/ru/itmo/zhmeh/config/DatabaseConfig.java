package ru.itmo.zhmeh.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private final Properties properties;

    public DatabaseConfig() {
        this.properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        // Загружаем файл из classpath (src/main/resources)
        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "Файл config.properties не найден в resources"
                );
            }

            // Читаем свойства
            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Ошибка чтения конфигурации: " + e.getMessage()
            );
        }
    }

    // Геттеры для получения значений
    public String getDbUrl() {
        return properties.getProperty("db.url");
    }

    public String getDbUsername() {
        return properties.getProperty("db.username");
    }

    public String getDbPassword() {
        return properties.getProperty("db.password");
    }

    // Метод с дефолтным значением (если ключ не найден)
    public int getDbPoolSize() {
        return Integer.parseInt(
                properties.getProperty("db.pool.size", "10")
        );
    }
}