package ru.itmo.zhmeh.domain;

import ru.itmo.zhmeh.validation.FieldValidator;
import ru.itmo.zhmeh.validation.StringValidationType;

public enum InstrumentStatus {
    ACTIVE,
    OUT_OF_SERVICE;

    public static InstrumentStatus statusFromString(String status){ // вопросы к логике, но вроде нормально + расположение метода
        FieldValidator.validateIsStringEmpty(status, StringValidationType.INST_STATUS);
        for (InstrumentStatus st : InstrumentStatus.values()) {
            if (st.name().equalsIgnoreCase(status)) {
                return st;
            }
        }
        throw new IllegalArgumentException("Ошибка: неизвестный статус: " + status);
    }


}
