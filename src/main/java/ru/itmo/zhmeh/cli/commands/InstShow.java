package ru.itmo.zhmeh.cli.commands;

import ru.itmo.zhmeh.cli.Environment;

public final class InstShow extends Command {
    private static final String name = "inst_show";

    @Override
    public void execute(Environment environment, String args) {
        long id = parseId(args);
        System.out.println(environment.getInstrumentsManager().getById(id).toString());
    }

    @Override
    public String getHelp() {
        return name + ": карточка инструмента + последня калибровка";
    }
    public static String getName(){
        return name;
    }
}
