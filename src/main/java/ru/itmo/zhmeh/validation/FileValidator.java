package ru.itmo.zhmeh.validation;

import ru.itmo.zhmeh.domain.*;
import ru.itmo.zhmeh.storage.DataContainer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Валидатор данных, загруженных из файла.
 * Проверяет целостность и корректность всех сущностей.
 */
public class FileValidator {

    /**
     * Проверить весь контейнер данных.
     * @param container загруженные данные
     * @throws FileValidationException если данные невалидны
     */
    public void validate(DataContainer container) throws FileValidationException {

        // проверка сущностей и их полей
        validateInstruments(container.getInstruments());
        validateCalibrations(container.getCalibrations());
        validateMaintenences(container.getMaintenances());

        // проверка ссылочной целостности
        checkReferentialIntegrity(container);
    }

    /**
     * Проверить приборы на дубликаты и корректность полей.
     */
    private void validateInstruments(List<Instrument> instruments) throws FileValidationException {
        Set<Long> IDs = new HashSet<>();

        for (Instrument inst : instruments) {
            // Проверка дубликатов ID
            if (!IDs.add(inst.getId())) {
                throw new FileValidationException("Дублирующийся ID прибора: " + inst.getId());
            }

            // проверка обязательных полей
            try {

                FieldValidator.validateName(inst.getName());
                FieldValidator.validateLocation(inst.getLocation());
                FieldValidator.validateInventoryNumber(inst.getInventoryNumber()); // только длина
                InstrumentType.typeFromString(inst.getType().toString()); //пойдет?
                InstrumentStatus.statusFromString((inst.getStatus().toString()));

            } catch (IllegalArgumentException e) {
                throw new FileValidationException("Ошибка чтения файла: прибор ID = " + inst.getId() + e.getMessage());
            }
        }
    }

    /**
     * Проверить калибровки
     *
     * @param calibrations
     * @throws FileValidationException
     */

    private void validateCalibrations(List<Calibration> calibrations) throws FileValidationException {
        Set<Long> IDs = new HashSet<>();

        for (Calibration cal : calibrations) {

            if (!IDs.add(cal.getId())) {
                throw new FileValidationException("Дублирующийся ID калибровки: " + cal.getId());
            }

            try {

                FieldValidator.validateComment(cal.getComment());
                CalibrationType.calTypeFromString(cal.getType().toString());
                CalibrationResult.calResultFromString(cal.getResult().toString());

            } catch (IllegalArgumentException e) {
                throw new FileValidationException("Ошибка чтения файла: калибровка ID = " + cal.getId() + e.getMessage());
            }
        }
    }

    /**
     * проверить обслуживания
     *
     * @param maintenances
     * @throws FileValidationException
     */
    private void validateMaintenences(List<Maintenance> maintenances) throws FileValidationException{
        Set<Long> IDs = new HashSet<>();

        for (Maintenance maint : maintenances) {

            if (!IDs.add(maint.getId())) {
                throw new FileValidationException("Дублирующийся ID обслуживания: " + maint.getId());
            }

            try {

                FieldValidator.validateDetails(maint.getDetails());
                MaintenanceType.fromString(maint.getType().toString());

            } catch (IllegalArgumentException e) {
                throw new FileValidationException("Ошибка чтения файла: Обслуживание ID = " + maint.getId() + e.getMessage());
            }
        }
    }

    /**
     * Проверить ссылочную целостность между сущностями
     *
     * @param container
     * @throws FileValidationException
     */

    private void checkReferentialIntegrity(DataContainer container) throws FileValidationException {

        Set<Long> instrumentIDs = new HashSet<>();

        for (Instrument inst : container.getInstruments()) {
            instrumentIDs.add(inst.getId());
        }

        // Проверить, что все калибровки ссылаются на существующие приборы
        for (Calibration cal : container.getCalibrations()) {
            if (!instrumentIDs.contains(cal.getInstrumentId())) {
                throw new FileValidationException("Калибровка id = " + cal.getId() + " ссылается на несуществующий прибор id=" + cal.getInstrumentId());
            }
        }

        // Проверить, что все обслуживания ссылаются на существующие приборы
        for (Maintenance maint : container.getMaintenances()) {
            if (!instrumentIDs.contains(maint.getInstrumentId())) {
                throw new FileValidationException("Обслуживание id=" + maint.getId() + " ссылается на несуществующий прибор id=" + maint.getInstrumentId()
                );
            }
        }
    }
}