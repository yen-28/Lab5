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

    private long nextId = 1;

    public MaintenanceManager(InstrumentsManager instrumentsManager) {
        this.instrumentsManager = instrumentsManager;
    }

    public long addNew(long instrumentId, String type, String details, Instant doneAt, String ownerUsername){
        long id = nextId++;
        instrumentsManager.checkInstrumentExistsId(instrumentId);

        Maintenance maintenance = new Maintenance(id, instrumentId, type, details, doneAt, ownerUsername);
        maintenances.put(id, maintenance);

        return id;
    }

    public Collection<Maintenance> getMaintenancesListByInstId(long instId){
        instrumentsManager.checkInstrumentExistsId(instId);
        return maintenances.values().stream()
                .filter(maint -> maint.getInstrumentId() == instId)
                .collect(Collectors.toList());
    }





}
