package ru.itmo.zhmeh.validation;

public final class FieldValidator {
    // для инструмента
    public static void validateName(String name){
        if ( name == null || name.isEmpty()){
            throw new IllegalArgumentException("Ошибка: имя не может быть пустым");
        } else if (name.length() > 128) {
            throw new IllegalArgumentException("Ошибка: длина больше 128 символов");
        }
    }

    public static void validateInventoryNumber(String inventoryNumber){
        if (inventoryNumber.length() > 32){
            throw new IllegalArgumentException("Ошибка: длина более 32 символов");
        }
    }

    public static void validateLocation(String location){
        if ( location == null || location.isEmpty()){
            throw new IllegalArgumentException("Ошибка: расположение не может быть пустым");
        } else if (location.length() > 64) {
            throw new IllegalArgumentException("Ошибка: длина больше 64 символов");
        }
    }

    public static void validateStatus(String status){
        if (status == null || status.isEmpty()){
            throw new IllegalArgumentException("Ошибка: статус не может быть пустым, выберете из списка");
        }
    }

    public static void validateType(String type){
        if (type == null || type.isEmpty()){
            throw new IllegalArgumentException("Ошибка: тип прибора не может быть пустым, выберете из списка");
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
        if ( details == null || details.isEmpty()){
            throw new IllegalArgumentException("Ошибка: не может быть пустым");
        } else if (details.length() > 128) {
            throw new IllegalArgumentException("Ошибка: длина больше 128 символов");
        }
    }


}
