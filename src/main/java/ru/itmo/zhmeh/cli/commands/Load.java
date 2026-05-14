package ru.itmo.zhmeh.cli.commands;

import ru.itmo.zhmeh.cli.Environment;

public class Load extends Command{
    private static final String name = "load";


    @Override
    public void execute(Environment environment, String args) {



    }

    @Override
    public String getHelp() {
        return "загрузить данные из файла";
    }

    public static String getName() {
        return name;
    }
}
