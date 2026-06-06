package ru.itmo.zhmeh.storage;

import ru.itmo.zhmeh.domain.Instrument;
import ru.itmo.zhmeh.domain.InstrumentStatus;
import ru.itmo.zhmeh.domain.InstrumentType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InstrumentRepository extends AbstractRepository {

    protected InstrumentRepository(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    /**
     * Получить все приборы.
     */
    public List<Instrument> findAll() throws SQLException {

        return executeInTransaction(connection -> {
            List<Instrument> instruments = new ArrayList<>();
            String sql = "SELECT * FROM instruments";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    instruments.add(mapRowToInstrument(resultSet));
                }
            }
            return instruments;
        });
    }

    /**
     * Добавить новый прибор.
     * @return id
     */
    public long insert(Instrument instrument) throws SQLException {

        return executeInTransaction(connection -> {
            String sql = "INSERT INTO instruments (name, type, inventory_number, location, status, owner_username) " +
                    "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, instrument.getName());
                statement.setString(2, instrument.getType().name()); // enum -> String
                statement.setString(3, instrument.getInventoryNumber());
                statement.setString(4, instrument.getLocation());
                statement.setString(5, instrument.getStatus().name());
                statement.setString(6, instrument.getOwnerUsername());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getLong("id");
                    }
                    throw new SQLException("Не удалось получить ID после вставки");
                }
            }
        });
    }

    public void delete(long id) throws SQLException {
        executeInTransaction(connection -> {
            String sql = "DELETE FROM instruments WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }
            return null; // для воид
        });
    }



    // Вспомогательный метод маппинга
    private Instrument mapRowToInstrument(ResultSet resultSet) throws SQLException {
        return new Instrument(
                resultSet.getLong("id"),
                resultSet.getString("owner_username"),
                resultSet.getString("name"),
                InstrumentType.valueOf(resultSet.getString("type")).toString(),
                resultSet.getString("inventory_number"),
                resultSet.getString("location"),
                InstrumentStatus.valueOf(resultSet.getString("status")).toString()
        );
    }
}
