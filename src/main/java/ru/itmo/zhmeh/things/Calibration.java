package ru.itmo.zhmeh.things;

import java.time.Instant;
import java.util.Objects;

public final class Calibration {
    // Уникальный номер калибровки. Программа назначает сама.
    private final long id;
    // Какому прибору принадлежит (id прибора).
// Должен ссылаться на реально существующий Instrument.
    private final long instrumentId;
    // Тип калибровки (например ONE_POINT, TWO_POINT). Выбирается из списка CalibrationType.
    private final CalibrationType type;
    // Результат: OK или FAIL.
    private CalibrationResult result;
    // Комментарий (например "buffers 4.00 and 7.00"). Можно пусто. До 128 символов.
    private String comment;
    // Когда делали калибровку. Если не вводят — текущее время.
    private Instant calibratedAt;
    // Кто делал (логин). На ранних этапах можно "SYSTEM".
    private String ownerUsername;
    // Когда запись создана. Программа ставит автоматически.
    private final Instant createdAt;

    public Calibration(long id, CalibrationType type, Instant createdAt, long instrumentId) {
        this.id = id;
        this.type = type;
        this.createdAt = createdAt;
        this.instrumentId = instrumentId;
    }

    public Calibration(long id, CalibrationType type, long instrumentId, CalibrationResult result, String comment, Instant calibratedAt, String ownerUsername, Instant createdAt) {
        this.id = id;
        this.type = type;
        this.instrumentId = instrumentId;
        this.result = result;
        this.comment = comment;
        this.calibratedAt = calibratedAt;
        this.ownerUsername = ownerUsername;
        this.createdAt = createdAt;
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
        this.comment = comment;
    }

    public void setCalibratedAt(Instant calibratedAt) {
        this.calibratedAt = calibratedAt;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Calibration that)) return false;
        return id == that.id && instrumentId == that.instrumentId && type == that.type && result == that.result && Objects.equals(calibratedAt, that.calibratedAt) && Objects.equals(ownerUsername, that.ownerUsername) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, instrumentId, type, result, calibratedAt, ownerUsername, createdAt);
    }
}