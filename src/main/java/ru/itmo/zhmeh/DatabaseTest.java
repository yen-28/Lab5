package ru.itmo.zhmeh;

import ru.itmo.zhmeh.config.DatabaseConfig;
import ru.itmo.zhmeh.storage.ConnectionManager;
import java.sql.Connection;

public class DatabaseTest {
    public static void main(String[] args) {
        try {
            DatabaseConfig config = new DatabaseConfig();
            ConnectionManager connManager = new ConnectionManager(config);

            // Пытаемся получить соединение напрямую, чтобы упало с настоящей ошибкой
            Connection conn = connManager.getConnection();
            conn.close(); // Сразу закрываем, если всё ок

            System.out.println("✅ Подключение к БД успешно!");
        } catch (Exception e) {
            System.out.println("❌ Ошибка подключения: " + e.getMessage());
            System.out.println("--- ПОЛНЫЙ ТЕКСТ ОШИБКИ ---");
            e.printStackTrace(); // Это покажет точную причину
        }
    }
}