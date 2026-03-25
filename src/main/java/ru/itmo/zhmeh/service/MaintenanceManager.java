package ru.itmo.zhmeh.service;

import ru.itmo.zhmeh.domain.Maintenance;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public final class MaintenanceManager {
    private final Map<Long, Maintenance> maintenances = new HashMap<>();
    private final InstrumentsManager instrumentsManager;

    public MaintenanceManager(InstrumentsManager instrumentsManager) {
        this.instrumentsManager = instrumentsManager;
    }

    public String addNew(long instrumentId, String type, String details, Instant doneAt, String ownerUsername){
        long id = IdGenerator.generateId();
        instrumentsManager.checkInstrumentExistsId(instrumentId);

        Maintenance maintenance = new Maintenance(id, instrumentId, type, details, doneAt, ownerUsername);
        maintenances.put(id, maintenance);

        return "OK maintenance_id = " + id;
    }



}
