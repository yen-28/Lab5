package ru.itmo.zhmeh.validation;

public final class FieldValidator {


    // для всех строковых тк одинаковая логика

    public static void validateIsStringEmpty(String value, StringValidationType type ){
        if (value == null || value.isEmpty()){
            switch (type){
                // инструмент
                case INST_NAME:
                    throw new IllegalArgumentException("Ошибка: имя прибора не может быть пустым");

                case INST_TYPE:
                    throw new IllegalArgumentException("Ошибка: тип прибора не может быть пустым, выберете из списка");

                case INST_STATUS:
                    throw new IllegalArgumentException("Ошибка: статус прибора не может быть пустым, выберете из списка");

                case INST_LOCATION:
                    throw new IllegalArgumentException("Ошибка: расположение прибора не может быть пустым");

                //калибратор
                case CAL_RESULT:
                    throw new IllegalArgumentException("Ошибка: результат калибровки не может быть пустым, выберете из списка");
                case CAL_TYPE:
                    throw new IllegalArgumentException("Ошибка: тип калибровки не может быть пустым, выберете из списка");

                //обслуживание
                case MAINTEN_TYPE:
                    throw new IllegalArgumentException("Ошибка: тип обслуживания не может быть пустым, выберете из списка");

                case MAINTEN_DETAILS:
                    throw new IllegalArgumentException("Ошибка: детали обслуживания не могут быть пустыми");
            }
        }
    }

    // для инструмента
    public static void validateName(String name){
        validateIsStringEmpty(name, StringValidationType.INST_NAME);
        if (name.length() > 128) {
            throw new IllegalArgumentException("Ошибка: длина больше 128 символов");
        }
    }

    public static void validateInventoryNumber(String inventoryNumber){
        if (inventoryNumber.length() > 32){
            throw new IllegalArgumentException("Ошибка: длина более 32 символов");
        }
    }

    public static void validateLocation(String location){
        validateIsStringEmpty(location, StringValidationType.INST_LOCATION);
        if (location.length() > 64) {
            throw new IllegalArgumentException("Ошибка: длина больше 64 символов");
        }
    }


    // для калибратора

    public static void validateComment(String inventoryNumber){
        if (inventoryNumber.length() > 128){
            throw new IllegalArgumentException("Ошибка: длина более 128 символов");
        }
    }


    //для обслуживания

    public static void validateDetails(String details){
        validateIsStringEmpty(details, StringValidationType.MAINTEN_DETAILS);
        if (details.length() > 128) {
            throw new IllegalArgumentException("Ошибка: длина больше 128 символов");
        }
    }
}
