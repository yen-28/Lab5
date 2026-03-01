package ru.itmo.zhmeh.things;

import java.time.Instant;

public final class Instrument {
    // Уникальный номер прибора. Программа назначает сама.
    public long id;
    // Название прибора (чтобы человек понял, что это). Нельзя пустое. До 128 символов.
    public String name;
    // Тип прибора (например PH_METER, BALANCE). Выбирается из списка InstrumentType.
    public InstrumentType type;
    // Инвентарный номер (например "INV-00077"). Можно пусто. До 32 символов.
    public String inventoryNumber;
    // Где находится (например "Lab-2 bench"). Нельзя пустое. До 64 символов.
    public String location;
    // Статус прибора: ACTIVE или OUT_OF_SERVICE.
    public InstrumentStatus status;
    // Кто добавил запись (логин). На ранних этапах можно "SYSTEM".
    public String ownerUsername;
    // Когда создано. Программа ставит автоматически.
    public Instant createdAt;
    // Когда обновляли. Программа обновляет автоматически.
    public Instant updatedAt;
}