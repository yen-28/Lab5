package ru.itmo.zhmeh.domain;

import ru.itmo.zhmeh.validation.FieldValidator;
import ru.itmo.zhmeh.validation.StringValidationType;

public enum CalibrationType {
    ONE_POINT,
    TWO_POINT;

    public static CalibrationType calTypeFromString(String type){ // вопросы к логике, но вроде нормально + расположение метода
        FieldValidator.validateIsStringEmpty(type, StringValidationType.CAL_TYPE);
        for (CalibrationType tp : CalibrationType.values()) {
            if (tp.name().equalsIgnoreCase(type)) {
                return tp;
            }
        }
        throw new IllegalArgumentException("Ошибка: неизвестный тип: " + type);
    }
}
