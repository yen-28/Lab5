package ru.itmo.zhmeh.cli;

import ru.itmo.zhmeh.cli.CommandManager;

public final class Help extends Command{

    private static final String name =  "help";


    @Override
    public void execute(Environment environment, String args) {
        for(Command command : environment.getCommandManager().getCommands().values()){
            System.out.println(command.getHelp());
        }
        System.out.println("exit: выход из программы");
    }

    @Override
    public String getHelp() {
        return name + ": возвращает список команд";
    }

    public static String getName() {
        return name;
    }

//    @Override
//    public void checkArgs() {
//    }
}
