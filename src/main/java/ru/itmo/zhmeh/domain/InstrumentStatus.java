package ru.itmo.zhmeh.domain;

import ru.itmo.zhmeh.validation.FieldValidator;

public enum InstrumentStatus {
    ACTIVE,
    OUT_OF_SERVICE;

    public static InstrumentStatus statusFromString(String status){ // вопросы к логике, но вроде нормально + расположение метода
        FieldValidator.validateStatus(status);
        for (InstrumentStatus st : InstrumentStatus.values()) {
            if (st.name().equalsIgnoreCase(status)) {
                return st;
            }
        }
        throw new IllegalArgumentException("Ошибка: неизвестный статус: " + status);
    }


}
