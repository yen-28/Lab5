package ru.itmo.zhmeh.storage;

import ru.itmo.zhmeh.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private final DatabaseConfig config;

    public ConnectionManager(DatabaseConfig config) {
        this.config = config;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                config.getDbUrl(),
                config.getDbUsername(),
                config.getDbPassword()
        );
    }

    // Тест подключения
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn.isValid(5);
        } catch (SQLException e) {
            return false;
        }
    }
}