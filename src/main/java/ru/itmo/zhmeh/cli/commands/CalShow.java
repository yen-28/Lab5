package ru.itmo.zhmeh.cli.commands;

import ru.itmo.zhmeh.cli.Environment;

public final class CalShow extends Command {

    private static final String name = "cal_show";

    @Override
    public void execute(Environment environment, String args) {
        long id = parseId(args);
        System.out.println(environment.getCalibrationManager().getById(id));
    }

    @Override
    public String getHelp() {
        return name + ": карточка калибровки";
    }

    public static String getName() {
        return name;
    }
}
