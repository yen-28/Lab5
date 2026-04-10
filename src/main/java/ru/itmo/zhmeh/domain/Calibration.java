package ru.itmo.zhmeh.domain;

import ru.itmo.zhmeh.validation.FieldValidator;

import java.time.Instant;
import java.util.Objects;

public final class Calibration {
    // Уникальный номер калибровки. Программа назначает сама. +
    private final long id;
    // Какому прибору принадлежит (id прибора).
// Должен ссылаться на реально существующий Instrument.
    private final long instrumentId;
    // Тип калибровки (например ONE_POINT, TWO_POINT). Выбирается из списка CalibrationType. -
    private CalibrationType type;
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


    public Calibration(long id, String type, long instrumentId, String result, String comment, String calibratedAt, String ownerUsername) {
        this.id = id;
        this.setCalType(type);
        this.instrumentId = instrumentId; //СДЕЛАТЬ
        this.setResult(result);
        this.setComment(comment);
        this.setCalibratedAt(calibratedAt);
        this.setOwnerUsername(ownerUsername);
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

    public void setResult(String result) {
        this.result = CalibrationResult.calResultFromString(result);
    }

    public void setComment(String comment) {
        FieldValidator.validateComment(comment);
        this.comment = comment;
    }

    public void setCalibratedAt(String calibratedAt) {
        if (calibratedAt == null || calibratedAt.isEmpty()) {//Возможно не налл недостаточно
            this.calibratedAt = Instant.now();
            System.out.println("Время выставлено по умолчанию: " + getCalibratedAt().toString());
        } else {
            this.calibratedAt = FieldValidator.parseInstant(calibratedAt);
        }
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = FieldValidator.validateOwnerUsername(ownerUsername); //валидация?
    }

    public void setCalType(String type) {
        this.type = CalibrationType.calTypeFromString(type);
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

    @Override
    public String toString() {
        return "Calibration " +
                "id: " + id + '\n' +
                " instrumentId: " + instrumentId + '\n' +
                " type: " + type + '\n' +
                " result: " + result + '\n' +
                " comment: '" + comment + "' \n" +
                " calibratedAt: " + calibratedAt;
    }
}