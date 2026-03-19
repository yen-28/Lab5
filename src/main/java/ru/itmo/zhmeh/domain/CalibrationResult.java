package ru.itmo.zhmeh.domain;

import ru.itmo.zhmeh.validation.FieldValidator;
import ru.itmo.zhmeh.validation.StringValidationType;

public enum CalibrationResult {
    OK,
    FAIL;

    public static CalibrationResult calResultFromString(String result) { // вопросы к логике, но вроде нормально + расположение метода
        FieldValidator.validateIsStringEmpty(result, StringValidationType.CAL_RESULT);
        for (CalibrationResult rs : CalibrationResult.values()) {
            if (rs.name().equalsIgnoreCase(result)) {
                return rs;
            }
        }
        throw new IllegalArgumentException("Ошибка: неизвестный результат: " + result + " , выберете из списка");
    }
}
