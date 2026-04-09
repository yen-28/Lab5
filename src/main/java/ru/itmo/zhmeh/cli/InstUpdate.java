package ru.itmo.zhmeh.cli;

public final class InstUpdate extends Command{

    private static final String name = "inst_update";

    @Override
    public void execute(Environment environment, String args) {

        String[] argsList = environment.getReader().splitArgs(args);
        long id = readId(argsList[0]);

        String[] secondArgs = argsList[1].split("=");
        String field = secondArgs[0];
        String value = secondArgs[1];

        environment.getInstrumentsManager().update(id, field, value);
        System.out.println("OK");

    }

    @Override
    public String getHelp() {
        return name + ": позволяет изменить имя(name)/расположение(location)/статус(status) прибора";
    }

    public static String getName(){
        return name;
    }
}
