package ru.itmo.zhmeh.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public final class User {
    private final long id;
    private final String login;
    private final String passwordHash; // SHA-256 хеш, НЕ пароль!

    public User(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.passwordHash = hashPassword(password);
    }

    // Хеширование пароля
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    public boolean checkPassword(String password) {
        return passwordHash.equals(hashPassword(password));
    }

    public long getId() { return id; }

    public String getLogin() { return login; }
}