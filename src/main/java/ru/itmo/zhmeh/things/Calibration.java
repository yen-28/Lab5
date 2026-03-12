package ru.itmo.zhmeh.things;

import ru.itmo.zhmeh.service.Validator;

import java.time.Instant;
import java.util.Objects;

public final class Calibration {
    // Уникальный номер калибровки. Программа назначает сама.
    private final long id;
    // Какому прибору принадлежит (id прибора).
// Должен ссылаться на реально существующий Instrument.
    private final long instrumentId;
    // Тип калибровки (например ONE_POINT, TWO_POINT). Выбирается из списка CalibrationType. -
    private final CalibrationType type;
    // Результат: OK или FAIL. -
    private CalibrationResult result;
    // Комментарий (например "buffers 4.00 and 7.00"). Можно пусто. До 128 символов. +
    private String comment;
    // Когда делали калибровку. Если не вводят — текущее время. -
    private Instant calibratedAt;
    // Кто делал (логин). На ранних этапах можно "SYSTEM".
    private String ownerUsername;
    // Когда запись создана. Программа ставит автоматически.
    private final Instant createdAt;

    public Calibration(long id, CalibrationType type, Instant createdAt, long instrumentId) {
        this.id = id; //сделать
        this.type = type;
        this.createdAt = Instant.now();
        this.instrumentId = instrumentId; //
    }

    public Calibration(long id, CalibrationType type, long instrumentId, CalibrationResult result, String comment, Instant calibratedAt, String ownerUsername) {
        this.id = id; // сделать
        this.type = type;
        this.instrumentId = instrumentId;
        this.result = result;
        this.comment = comment;
        this.calibratedAt = calibratedAt;
        this.setOwnerUsername();
        this.createdAt = Instant.now();
    }

// getters

    public long getId() {
        return id;
    }

    public long getInstrumentId() {
        return instrumentId;
    }

    public CalibrationType getType() {
        return type;
    }

    public CalibrationResult getResult() {
        return result;
    }

    public String getComment() {
        return comment;
    }

    public Instant getCalibratedAt() {
        return calibratedAt;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

// setters

    public void setResult(CalibrationResult result) {
        this.result = result;
    }

    public void setComment(String comment) {
        Validator.validateComment(comment);
        this.comment = comment;
    }

    public void setCalibratedAt(Instant calibratedAt) {
        this.calibratedAt = calibratedAt;
    }

    public void setOwnerUsername() {
        this.ownerUsername = "SYSTEM"; //временно
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Calibration that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}