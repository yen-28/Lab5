package ru.itmo.zhmeh.cli;

import ru.itmo.zhmeh.service.InstrumentsManager;

public final class InstAdd extends Command {

    private static final String name = "inst_add";

    /*
    ВСЁ СДЕЛАТЬ ПО УМУ
     */
    @Override
    public void execute(Environment environment, String args) {
        String ownerUsername = environment.getReader().readNonEmptyString("Ваше имя: ");
        String name = environment.getReader().readNonEmptyString("Имя прибора: ");
        String type = environment.getReader().readNonEmptyString("Выберите тип прибора:" +
                " (PH_METER,\n" +
                "    BALANCE,\n" +
                "    SPECTROPHOTOMETER,\n" +
                "    CONDUCTIVITY_METER,\n" +
                "    THERMOMETER, \n" + ">");
        String inventoryNumber = environment.getReader().readString("Введите инвентарный номер: ");
        String location = environment.getReader().readNonEmptyString("Расположение ");
        String status = environment.getReader().readNonEmptyString("Выберите статус прибора: " +
                "ACTIVE,\n" +
                "    OUT_OF_SERVICE, \n" + ">");

        environment.getInstrumentsManager().addNew(ownerUsername, name, type, inventoryNumber, location, status);
        System.out.println("Прибор " + name + "добавлен" + "id = ");



//TODO 1 Здесь делаем ввод с помощью ридера
    }

    public static String getName() {
        return name;
    }

    @Override
    public String getHelp() {
        return name + ": добавить новый прибор";
    }

//    @Override
//    public void checkArgs() {
//
//    }
}
