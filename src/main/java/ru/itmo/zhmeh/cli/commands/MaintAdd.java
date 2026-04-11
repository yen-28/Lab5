package ru.itmo.zhmeh.cli.commands;

import ru.itmo.zhmeh.cli.Environment;

public final class MaintAdd extends Command {

    private static final String name = "maint_add";

    @Override
    public void execute(Environment environment, String args) {
        long instrumentId = parseId(args);

        String type = environment.getReader().readNonEmptyString("Введите тип: \n" +
                "    SERVICE,\n" +
                "    REPAIR \n" +
                "> ");
        String details = environment.getReader().readString("Ведите детали обслуживания: ");
        String doneAt = environment.getReader().readString("Введите дату обслуживания (в формате YYYY-MM-DD): ");
        String ownerUsername = environment.getReader().readString("Введите имя пользователя (по умолчанию - SYSTEM): ");

        long id = environment.getMaintenanceManager().addNew(instrumentId, type, details, doneAt, ownerUsername);
        System.out.println("Обслуживание добавлено, ID: " + id);
    }

    @Override
    public String getHelp() {
        return name + ": добавить обслуживание (интерактивно)";
    }

    public static String getName() {
        return name;
    }
}
