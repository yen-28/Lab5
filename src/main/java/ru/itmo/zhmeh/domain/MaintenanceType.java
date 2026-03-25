package ru.itmo.zhmeh.domain;

import ru.itmo.zhmeh.validation.FieldValidator;
import ru.itmo.zhmeh.validation.StringValidationType;

public enum MaintenanceType {
    SERVICE,
    REPAIR;

    public static MaintenanceType fromString(String type){
        FieldValidator.validateIsStringEmpty(type, StringValidationType.MAINTEN_TYPE);
        for (MaintenanceType tp : MaintenanceType.values()){
            if(tp.name().equalsIgnoreCase(type)){
                return tp;
            }
        }
        throw new IllegalArgumentException("Ошибка: неверный тип обслуживания: " + type + ", выберете из списка");

    }
}
