package ru.itmo.zhmeh.service;

import ru.itmo.zhmeh.domain.Maintenance;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class MaintenanceManager {
    private final Map<Long, Maintenance> maintenances = new HashMap<>();
    private final InstrumentsManager instrumentsManager;

    private long nextId = 1;

    public MaintenanceManager(InstrumentsManager instrumentsManager) {
        this.instrumentsManager = instrumentsManager;
    }

    public long addNew(long instrumentId, String type, String details, String doneAt, String ownerUsername){

        instrumentsManager.checkInstrumentExistsId(instrumentId);


        if (maintenances.containsKey(nextId)) {
            nextId++;// если предыдущий не занят, то занять его!!!
        }


        Maintenance maintenance = new Maintenance(nextId, instrumentId, type, details, doneAt, ownerUsername);

        maintenances.put(nextId, maintenance);

        return nextId;
    }

    public List<Maintenance> getMaintenancesListByInstId(long instId, String key, long value){
        instrumentsManager.checkInstrumentExistsId(instId);
        List<Maintenance> instMaint =  maintenances.values().stream()
                .filter(maint -> maint.getInstrumentId() == instId)
                .toList();
        if (key.equalsIgnoreCase("--last")){
            return instMaint.stream()
                    .filter(maint -> maint.getId() <= value)
                    .toList();
        }
        else return instMaint;
    }





}
