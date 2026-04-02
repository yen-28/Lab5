package ru.itmo.zhmeh.cli;

import java.util.HashMap;

public final class CommandManager {
    private final HashMap<String, Command> commands = new HashMap<>();


    public HashMap<String, Command> getCommands() {
        return commands;
    }

}
