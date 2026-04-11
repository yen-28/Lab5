package ru.itmo.zhmeh.service;

import ru.itmo.zhmeh.domain.Calibration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.itmo.zhmeh.domain.InstrumentStatus.OUT_OF_SERVICE;

public final class CalibrationManager {
    private final Map<Long, Calibration> calibrations = new HashMap<>();//квен порекомендовал меп на всякий случай
    private final InstrumentsManager instrumentsManager;
    private long nextId = 1;

    public CalibrationManager(InstrumentsManager instrumentsManager) {
        this.instrumentsManager = instrumentsManager;
    }

    public void checkCalibrationExistsId(long id){ //паблик на всякий случай, вдруг надо будет
        if (!calibrations.containsKey(id)){
            throw new IllegalArgumentException("Ошибка: калибровка с id: " + id + " не найдена");
        }
    }

    public Calibration getById(long id){
        if (calibrations.containsKey(id)){
            return calibrations.get(id);
        } else throw new IllegalArgumentException("Калибровка с ID: " + id + " не найдена");
    }

    public long addNew(String type, long instrumentId, String result, String comment, String calibratedAt, String ownerUsername){
        long id = nextId++;
        instrumentsManager.checkInstrumentExistsId(instrumentId);
        if (instrumentsManager.getById(instrumentId).getStatus() == OUT_OF_SERVICE){
            throw new IllegalArgumentException("Ошибка: прибор не в работе");
        }

        Calibration calibration = new Calibration(id, type, instrumentId, result, comment, calibratedAt, ownerUsername);
        calibrations.put(id, calibration);

        instrumentsManager.getById(instrumentId).setLastCalibration(calibration.getCalibratedAt());

        return id;
    }

    public List<Calibration> getCalibrationsListByInstId(long InstId, String key, long value){ //TODO насколько норм делать обработку ключа здесь?
        instrumentsManager.checkInstrumentExistsId(InstId);
        List<Calibration> instCals =  calibrations.values().stream()
                .filter(cal -> cal.getInstrumentId() == InstId )
                .toList();
        if (key.equalsIgnoreCase("--last")){
            return  instCals.stream()
                    .filter(cal -> cal.getId() <= value) //id же разные
                    .toList();

        }
        else return instCals;
    }









}
