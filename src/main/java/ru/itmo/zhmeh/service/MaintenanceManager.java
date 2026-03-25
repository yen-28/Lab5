package ru.itmo.zhmeh.service;

import ru.itmo.zhmeh.domain.Maintenance;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Collection<Maintenance> getMaintenancesListByInstId(long instId){
        instrumentsManager.checkInstrumentExistsId(instId);
        return maintenances.values().stream()
                .filter(maint -> maint.getInstrumentId() == instId)
                .collect(Collectors.toList());
    }





}
