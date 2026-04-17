package ru.itmo.zhmeh.cli.commands;

import ru.itmo.zhmeh.cli.Environment;

/**
 * Команда добавления прибора (inst_add).
 * <p>
 * Работает в интерактивном режиме:
 * <ol>
 *   <li>Запрашивает название прибора</li>
 *   <li>Запрашивает тип из списка InstrumentType</li>
 *   <li>Запрашивает инвентарный номер</li>
 *   <li>Запрашивает место нахождения</li>
 * </ol>
 * <p>
 * После ввода всех полей вызывает InstrumentService.addInstrument().
 *
 * @see ru.itmo.zhmeh.service.InstrumentsManager
 * @see Environment
 */
public final class InstAdd extends Command {

    private static final String name = "inst_add";

    @Override
    public void execute(Environment environment, String args) {
        String ownerUsername = environment.getReader().readString("Ваше имя: ");
        String name = environment.getReader().readNonEmptyString("Имя прибора: ");
        String type = environment.getReader().readNonEmptyString("Выберите тип прибора:" +
                " (PH_METER,\n" +
                "    BALANCE,\n" +
                "    SPECTROPHOTOMETER,\n" +
                "    CONDUCTIVITY_METER,\n" +
                "    THERMOMETER, \n" + ">");
        String inventoryNumber = environment.getReader().readString("Инвентарный номер: ");
        String location = environment.getReader().readNonEmptyString("Расположение: ");
        String status = environment.getReader().readNonEmptyString("Выберите статус прибора: " +
                "ACTIVE,\n" +
                "    OUT_OF_SERVICE, \n" + ">");

        long id = environment.getInstrumentsManager().addNew(ownerUsername, name, type, inventoryNumber, location, status);
        System.out.println("Прибор " + name + " добавлен, ID: " +id);

    }

    public static String getName() {
        return name;
    }

    @Override
    public String getHelp() {
        return name + ": добавить новый прибор (интерактивно)";
    }

}
