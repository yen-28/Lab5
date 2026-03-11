package ru.itmo.zhmeh.service;

public final class Validator {
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




}
