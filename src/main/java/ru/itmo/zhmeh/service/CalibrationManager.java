package ru.itmo.zhmeh.service;

import ru.itmo.zhmeh.domain.Calibration;
import ru.itmo.zhmeh.domain.Instrument;

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

//    public String addNew(String ownerUsername, String name, String type, String inventoryNumber, String location, String status){
//        long id = generateId();
//        Calibration calibration = new Calibration(id, ownerUsername, name, type, inventoryNumber, location, status);
//        calibrations.put(id, calibration);
//
//        return "OK instrument_id = " + id;
//    }









}
