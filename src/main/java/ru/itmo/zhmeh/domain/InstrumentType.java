package ru.itmo.zhmeh.domain;

import ru.itmo.zhmeh.validation.FieldValidator;
import ru.itmo.zhmeh.validation.StringValidationType;

public enum InstrumentType {
    PH_METER,
    BALANCE,
    SPECTROPHOTOMETER,
    CONDUCTIVITY_METER,
    THERMOMETER;

    public static InstrumentType typeFromString(String type){ // вопросы к логике, но вроде нормально + расположение метода
        FieldValidator.validateIsStringEmpty(type, StringValidationType.INST_TYPE);
        for (InstrumentType tp : InstrumentType.values()) {
            if (tp.name().equalsIgnoreCase(type)) {
                return tp;
            }
        }
        throw new IllegalArgumentException("Ошибка: неизвестный тип: " + type);
    }
}
