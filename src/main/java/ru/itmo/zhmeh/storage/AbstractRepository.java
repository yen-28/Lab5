package ru.itmo.zhmeh.storage;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Базовый класс для всех репозиториев.
 * Отвечает за подключения к БД и управление транзакциями.
 */
public abstract class AbstractRepository {

    protected final ConnectionManager connectionManager;

    protected AbstractRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Для выполнения операций в транзакции.
     *
     * @param action блок кода (SQL запросы), который нужно выполнить
     * @param <T> тип возвращаемого значения
     * @return результат выполнения action
     */
    protected <T> T executeInTransaction(TransactionAction<T> action) throws SQLException {
        // автоматически закроет Connection в конце блока
        try (Connection connection = connectionManager.getConnection()) {

            // Отключаем автокоммит, чтобы управлять транзакцией вручную (для отката в случае ошибки)
            connection.setAutoCommit(false);

            try {

                T result = action.execute(connection);

                // Если успешно, подтверждаем транзакцию
                connection.commit();
                return result;

            } catch (SQLException e) {
                // Если произошла ошибка БД — откатываем изменения
                connection.rollback();
                throw e; // Пробрасываем ошибку выше
            }

        }
    }

    /**
     * Функциональный интерфейс для передачи блока кода в основной метод
     * Позволяет использовать лямбда-выражения.
     */
    @FunctionalInterface
    protected interface TransactionAction<T> {
        T execute(Connection connection) throws SQLException;
    }
}