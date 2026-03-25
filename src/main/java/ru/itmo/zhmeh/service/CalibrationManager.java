package ru.itmo.zhmeh.service;

import ru.itmo.zhmeh.domain.Calibration;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.itmo.zhmeh.domain.InstrumentStatus.OUT_OF_SERVICE;
import static ru.itmo.zhmeh.service.IdGenerator.generateId;

public final class CalibrationManager {
    private final Map<Long, Calibration> calibrations = new HashMap<>();//квен порекомендовал меп на всякий случай
    private final InstrumentsManager instrumentsManager;

    public CalibrationManager(InstrumentsManager instrumentsManager) {
        this.instrumentsManager = instrumentsManager;
    }

    public void checkCalibrationExistsId(long id){ //паблик на всякий случай, вдруг надо будет
        if (!calibrations.containsKey(id)){
            throw new IllegalArgumentException("Ошибка: калибровка с id: " + id + " не найдена");
        }
    }

    public String addNew(String type, long instrumentId, String result, String comment, Instant calibratedAt, String ownerUsername){
        long id = generateId();
        instrumentsManager.checkInstrumentExistsId(instrumentId);
        if (instrumentsManager.getById(instrumentId).getStatus() == OUT_OF_SERVICE){
            throw new IllegalArgumentException("Ошибка: прибор не в работе");
        }

        Calibration calibration = new Calibration(id, type, instrumentId, result, comment, calibratedAt, ownerUsername);
        calibrations.put(id, calibration);

        return "OK calibration_id = " + id;
    }

    public Collection<Calibration> getCalibrationsListByInstId(long InstId){// коллекшион потому что универсально
        instrumentsManager.checkInstrumentExistsId(InstId);
        return calibrations.values().stream()
                .filter(cal -> cal.getInstrumentId() == InstId)
                .collect(Collectors.toList());
    }









}
