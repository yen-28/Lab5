package ru.itmo.zhmeh.service;

import ru.itmo.zhmeh.domain.Calibration;
import ru.itmo.zhmeh.domain.Instrument;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static ru.itmo.zhmeh.service.IdGenerator.generateId;

public final class CalibrationManager {
    private final Map<Long, Calibration> calibrations = new HashMap<>(); //квен порекомендовал меп на всякий случай


    public void checkCalibrationExistsId(long id){ //паблик на всякий случай, вдруг надо будет
        if (!calibrations.containsKey(id)){
            throw new IllegalArgumentException("Ошибка: калибровка с id: " + id + " не найдена");
        }
    }

    public String addNew(String type, long instrumentId, String result, String comment, Instant calibratedAt, String ownerUsername){
        long id = generateId();
        Calibration calibration = new Calibration(id, type, instrumentId, result, comment, calibratedAt, ownerUsername);
        calibrations.put(id, calibration); //добавить проверку инструмент айди?

        return "OK calibration_id = " + id;
    }









}
