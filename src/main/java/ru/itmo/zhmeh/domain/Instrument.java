package ru.itmo.zhmeh.domain;

import ru.itmo.zhmeh.cli.commands.Command;
import ru.itmo.zhmeh.validation.FieldValidator;

import java.time.Instant;
import java.util.Objects;

public final class Instrument {
    // Уникальный номер прибора. Программа назначает сама. -
    private final long id;
    // Название прибора (чтобы человек понял, что это). Нельзя пустое. До 128 символов. +
    private String name;
    // Тип прибора (например PH_METER, BALANCE). Выбирается из списка InstrumentType. -
    private InstrumentType type;
    // Инвентарный номер (например "INV-00077"). Можно пусто. До 32 символов. +
    private String inventoryNumber;
    // Где находится (например "Lab-2 bench"). Нельзя пустое. До 64 символов. +
    private String location;
    // Статус прибора: ACTIVE или OUT_OF_SERVICE. -
    private InstrumentStatus status;
    // Кто добавил запись (логин). На ранних этапах можно "SYSTEM". +-
    private String ownerUsername;
    // Когда создано. Программа ставит автоматически. +
    private final Instant createdAt;
    // Когда обновляли. Программа обновляет автоматически. +
    private Instant updatedAt;

    private Instant lastCalibration;


    private void touch(){
        setUpdatedAt(Instant.now());
        setOwnerUsername("SYSTEM"); // ВРЕМЕННО НА РАННИХ ЭТАПАХ
    }
    public Instrument(long id, String ownerUsername, String name, String type, String inventoryNumber, String location, String status) {
        this.id = id; // айди метод!!
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.setOwnerUsername(ownerUsername);
        this.setName(name);
        this.setType(type);
        this.inventoryNumber = inventoryNumber;
        this.setLocation(location);
        this.setStatus(status);
    }

    public Instrument(long id) {
        this.id = id; // айди метод!!
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
// getters


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public InstrumentType getType() {
        return type;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public String getLocation() {
        return location;
    }

    public InstrumentStatus getStatus() {
        return status;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getLastCalibration() {
        return lastCalibration;
    }

    // setters

    public void setType(String type) {
        this.type = InstrumentType.typeFromString(type);
        touch();
    }

    public void setInventoryNumber(String inventoryNumber) {
        FieldValidator.validateInventoryNumber(inventoryNumber);
        this.inventoryNumber = inventoryNumber;
        touch();
    }

    public void setLocation(String location) {
        FieldValidator.validateLocation(location);
        this.location = location;
        touch();
    }

    public void setStatus(String status) {
        this.status = InstrumentStatus.statusFromString(status); // обработан пустой и некорректный регистр
        touch();
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = FieldValidator.validateOwnerUsername(ownerUsername);

    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setName(String name) {
        FieldValidator.validateName(name);
        this.name = name;
        touch();
    }

    public void setLastCalibration(Instant lastCalibration) { //бездушная машина хочет заставить меня не делать сеттер, каждый раз проверять это условие через менеджер
        if (this.lastCalibration == null || lastCalibration.isAfter(this.lastCalibration)) {
            this.lastCalibration = lastCalibration;
        }
        touch();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Instrument that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Instrument " +
                "id: " + id + '\n' +
                "name: " + name + '\n' +
                "type: " + type + '\n' +
                "inventoryNumber: " + inventoryNumber + '\n' +
                "location: " + location + '\n' +
                "status: " + status + '\n' +
                "lastCalibration: " + Command.dateFormater(lastCalibration) + '\n';
    }
}