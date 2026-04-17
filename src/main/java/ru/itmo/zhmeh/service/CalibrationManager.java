package ru.itmo.zhmeh.service;

import ru.itmo.zhmeh.domain.Calibration;
import ru.itmo.zhmeh.domain.Instrument;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.itmo.zhmeh.domain.InstrumentStatus.OUT_OF_SERVICE;

/**
 * Сервис для управления калибровками (Calibration).
 * <p>
 * Отвечает за:
 * <ul>
 *   <li>Создание и добавление калибровок в коллекцию</li>
 *   <li>Валидацию данных перед сохранением</li>
 *   <li>Поиск калибровок</li>
 * </ul>
 * <p>
 * Данные хранятся в памяти в HashMap<Long, Instrument>.
 * Генерация ID осуществляется автоматически.
 *
 * @see Calibration
 * @see InstrumentsManager
 */

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
        instrumentsManager.checkInstrumentExistsId(instrumentId);

        if (instrumentsManager.getById(instrumentId).getStatus() == OUT_OF_SERVICE){
            throw new IllegalArgumentException("Ошибка: прибор не в работе");
        }



        if (calibrations.containsKey(nextId)) {
            nextId++; // если предыдущий не занят, то занять его!!!
        }


        Calibration calibration = new Calibration(nextId, type, instrumentId, result, comment, calibratedAt, ownerUsername);
        calibrations.put(nextId, calibration);

        instrumentsManager.getById(instrumentId).setLastCalibration(calibration.getCalibratedAt());

        return nextId;
    }

    public List<Calibration> getCalibrationsListByInstId(long InstId, String key, long value){ //TODO насколько норм делать обработку ключа здесь?
        instrumentsManager.checkInstrumentExistsId(InstId);
        List<Calibration> instCals =  calibrations.values().stream()
                .filter(cal -> cal.getInstrumentId() == InstId )
                .toList();
        if (key.equalsIgnoreCase("--last")){
            return  instCals.stream()
                    .filter(cal -> cal.getId() <= value)
                    .toList();
        }
        else return instCals;
    }









}
