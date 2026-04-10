package ru.itmo.zhmeh.cli.commands;

import ru.itmo.zhmeh.cli.Environment;

public final class CalAdd extends Command{

    private static final String name = "cal_add";

    @Override
    public void execute(Environment environment, String args) {
        String type = environment.getReader().readNonEmptyString("Введите тип: \n" +
                "    ONE_POINT,\n" +
                "    TWO_POINT \n" +
                "> ");
        long instrumentId = environment.getReader().readLongId("Введите ID прибора: ");
        String result = environment.getReader().readNonEmptyString("Введите результат (OK или FAIL): ");
        String comment = environment.getReader().readString("Введите комментарий: ");
        String calibratedAt = environment.getReader().readString("Введите время калибровки (в формате YYYY-MM-DD): ");
        String ownerUsername = environment.getReader().readString("Введите имя пользователя (по умолчанию - SYSTEM): ");

        long id = environment.getCalibrationManager().addNew(type, instrumentId, result, comment, calibratedAt, ownerUsername);
        System.out.println("Калибровка добавленa, ID: " + id);
    }

    @Override
    public String getHelp() {
        return name + ": добавить калибровку (интерактивно)";
    }

    public static String getName() {
        return name;
    }
}
