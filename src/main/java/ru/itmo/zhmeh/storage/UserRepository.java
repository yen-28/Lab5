package ru.itmo.zhmeh.storage;

import ru.itmo.zhmeh.domain.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Optional;

public class UserRepository {
    private final ConnectionManager connectionManager;

    public UserRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    // Хеширование пароля (SHA-256)
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 не доступен", e);
        }
    }

    // Регистрация пользователя
    public long register(String login, String password) throws SQLException {
        String passwordHash = hashPassword(password);

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "INSERT INTO users (login, password_hash) VALUES (?, ?) RETURNING id";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, login);
                stmt.setString(2, passwordHash);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getLong("id");
                }
                throw new SQLException("Не удалось получить ID пользователя");
            }
        }
    }

    // Поиск пользователя по логину
    public Optional<User> findByLogin(String login) throws SQLException {
        try (Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT id, login, password_hash FROM users WHERE login = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, login);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    User user = new User(rs.getLong("id"), rs.getString("login"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    return Optional.of(user);
                }
                return Optional.empty();
            }
        }
    }

    // Проверка пароля
    public boolean verifyPassword(String login, String password) throws SQLException {
        Optional<User> user = findByLogin(login);
        if (user.isEmpty()) return false;
        return user.get().getPasswordHash().equals(hashPassword(password));
    }
}