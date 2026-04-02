package ru.itmo.zhmeh.cli;

import java.util.HashMap;

public final class CommandManager {
    private final HashMap<String, Command> commands = new HashMap<>();



    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public void addCommand(String name, Command command){
        commands.put(name, command);
    }



}
