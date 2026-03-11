package ru.itmo.zhmeh.things;

import java.time.Instant;

public final class Maintenance {
    // Уникальный номер записи обслуживания. Программа назначает сама.
    private final long id;
    // Какому прибору относится (id прибора).
// Должен ссылаться на реально существующий Instrument.
    public final long instrumentId;
    // Тип: SERVICE (обслуживание) или REPAIR (ремонт).
    public MaintenanceType type;
    // Что сделали (например "electrode cleaning"). Нельзя пустое. До 128 символов.
    public String details;
    // Когда сделали. Если не вводят — текущее время.
    public Instant doneAt;
    // Кто сделал (логин). На ранних этапах можно "SYSTEM".
    public String ownerUsername;
    // Когда запись создана. Программа ставит автоматически.
    public Instant createdAt;

}
