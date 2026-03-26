package ru.itmo.zhmeh.cli;

public abstract class Command {

    boolean isInputSeparated;

    public abstract void execute();
    public abstract String getHelp();
    public abstract void checkArgs(); //????
}
