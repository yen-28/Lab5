package ru.itmo.zhmeh.things;

import java.time.Instant;
import java.util.Objects;

public final class Instrument {
    // Уникальный номер прибора. Программа назначает сама.
    private final long id;
    // Название прибора (чтобы человек понял, что это). Нельзя пустое. До 128 символов.
    private String name;
    // Тип прибора (например PH_METER, BALANCE). Выбирается из списка InstrumentType.
    private InstrumentType type;
    // Инвентарный номер (например "INV-00077"). Можно пусто. До 32 символов.
    private String inventoryNumber;
    // Где находится (например "Lab-2 bench"). Нельзя пустое. До 64 символов.
    private String location;
    // Статус прибора: ACTIVE или OUT_OF_SERVICE.
    private InstrumentStatus status;
    // Кто добавил запись (логин). На ранних этапах можно "SYSTEM".
    private String ownerUsername;
    // Когда создано. Программа ставит автоматически.
    private final Instant createdAt;
    // Когда обновляли. Программа обновляет автоматически.
    private Instant updatedAt;

    public Instrument(long id, Instant createdAt, Instant updatedAt, String ownerUsername, String name, InstrumentType type, String inventoryNumber, String location, InstrumentStatus status) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.setOwnerUsername(ownerUsername);
        this.setName(name);
        this.setType(type);
        this.inventoryNumber = inventoryNumber;
        this.location = location;
        this.status = status;
    }

    public Instrument(long id, Instant createdAt) {
        this.id = id;
        this.createdAt = createdAt;
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
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStatus(InstrumentStatus status) {
        this.status = status;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setName(String name) {
        if (!(name == null) & !(name.isEmpty()) & name.length() <= 128 ) {
            this.name = name;
        } else throw new IllegalArgumentException("Invalid name: " + name);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Instrument that)) return false;
        return id == that.id && Objects.equals(name, that.name) && type == that.type && Objects.equals(inventoryNumber, that.inventoryNumber) && Objects.equals(location, that.location) && status == that.status && Objects.equals(ownerUsername, that.ownerUsername) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, inventoryNumber, location, status, ownerUsername, createdAt, updatedAt);
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