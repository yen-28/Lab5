package ru.itmo.zhmeh.validation;

import ru.itmo.zhmeh.domain.Calibration;
import ru.itmo.zhmeh.domain.Instrument;
import ru.itmo.zhmeh.domain.InstrumentStatus;
import ru.itmo.zhmeh.domain.InstrumentType;
import ru.itmo.zhmeh.storage.DataContainer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Валидатор данных, загруженных из файла.
 * Проверяет целостность и корректность всех сущностей.
 */
public class FileValidator {

    public void validate(DataContainer container) throws FileValidationException {

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
                throw new FileValidationException(e.getMessage());
            }
        }
    }

    private void validateCalibration(List<Calibration> calibrations){



    }


}