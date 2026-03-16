package ru.itmo.zhmeh.things;

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


    private void touch(){
        setUpdatedAt(Instant.now());
        setOwnerUsername("SYSTEM"); // ВРЕМЕННО НА РАННИХ ЭТАПАХ
    }
    public Instrument(long id, String ownerUsername, String name, InstrumentType type, String inventoryNumber, String location, InstrumentStatus status) {
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
// setters

    public void setType(InstrumentType type) {
        this.type = type;
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
        this.ownerUsername = ownerUsername;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setName(String name) {
        FieldValidator.validateName(name);
        this.name = name;
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
        return "Instrument{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", inventoryNumber='" + inventoryNumber + '\'' +
                ", location='" + location + '\'' +
                ", status=" + status +
                ", ownerUsername='" + ownerUsername + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}