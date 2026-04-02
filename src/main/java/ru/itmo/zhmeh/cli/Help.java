package ru.itmo.zhmeh.cli;

import ru.itmo.zhmeh.cli.CommandManager;

public final class Help extends Command{

    @Override
    public void execute(CommandManager cm, String[] args) {
        for(Command command : cm.getCommands().values()){
            System.out.println(command.getHelp());
        }
        System.out.println("exit: выход из программы");
    }

    @Override
    public String getHelp() {
        return name + ": возвращает список команд";
    }

    @Override
    public void checkArgs() {

    }
}
