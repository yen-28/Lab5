package ru.itmo.zhmeh.service;

import ru.itmo.zhmeh.domain.Calibration;

import java.util.HashMap;
import java.util.Map;

public final class CalibrationManager extends Manager {
    private final Map<Long, Calibration> calibrations = new HashMap<>(); //квен порекомендовал меп на всякий случай


    public void checkCalibrationExistsId(long id){ //паблик на всякий случай, вдруг надо будет
        if (!calibrations.containsKey(id)){
            throw new IllegalArgumentException("Ошибка: калибровка с id: " + id + " не найдена");
        }
    }









}
