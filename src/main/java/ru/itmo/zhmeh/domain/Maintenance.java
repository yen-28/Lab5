package ru.itmo.zhmeh.domain;

import ru.itmo.zhmeh.cli.commands.Command;
import ru.itmo.zhmeh.validation.FieldValidator;

import java.time.Instant;
import java.util.Objects;

public final class Maintenance {
    // Уникальный номер записи обслуживания. Программа назначает сама.
    private final long id;
    // Какому прибору относится (id прибора).
// Должен ссылаться на реально существующий Instrument.
    private final long instrumentId;
    // Тип: SERVICE (обслуживание) или REPAIR (ремонт).
    private MaintenanceType type;
    // Что сделали (например "electrode cleaning"). Нельзя пустое. До 128 символов. +
    private String details;
    // Когда сделали. Если не вводят — текущее время. +-
    private Instant doneAt;
    // Кто сделал (логин). На ранних этапах можно "SYSTEM". +
    private String ownerUsername;
    // Когда запись создана. Программа ставит автоматически. +
    private final Instant createdAt;

    public Maintenance(long id, long instrumentId, Instant createdAt) {
        this.id = id;
        this.instrumentId = instrumentId;
        this.createdAt = Instant.now();
        this.doneAt = Instant.now();
    }

    public Maintenance(long id, long instrumentId, String type, String details, Instant doneAt, String ownerUsername) {
        this.createdAt = Instant.now();
        this.id = id;
        this.instrumentId = instrumentId;
        this.setType(type);
        this.setDetails(details);
        this.setDoneAt(doneAt);
        this.setOwnerUsername(ownerUsername); //временно иллюзия выбора
    }

    //getters
    public long getId() {
        return id;
    }

    public long getInstrumentId() {
        return instrumentId;
    }

    public MaintenanceType getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }

    public Instant getDoneAt() {
        return doneAt;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    //setters

    public void setType(String type) {
        this.type = MaintenanceType.fromString(type);
    }

    public void setDetails(String details) {
        FieldValidator.validateDetails(details);
        this.details = details;
    }

    public void setDoneAt(Instant doneAt) {
        if (doneAt != null) { //возможно налл не достаточно
            this.doneAt = doneAt;
        } else { this.doneAt = Instant.now();
        System.out.println("Время выставлено по умолчанию");}
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = "SYSTEM"; //ВРЕМЕННО
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Maintenance that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Maintenance " +
                "id: " + id + '\n' +
                " instrumentId: " + instrumentId + '\n' +
                " type: " + type + '\n' +
                " details: '" + details + '\'' + '\n' +
                " doneAt: " + Command.dateFormater(doneAt) + '\n' +
                " ownerUsername: '" + ownerUsername + '\'' + '\n' +
                " createdAt: " + Command.dateFormater(createdAt) + '\n';
    }
}
