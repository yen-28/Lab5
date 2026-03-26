package ru.itmo.zhmeh.cli;

public abstract class Command {

    protected String name;
    protected boolean isInputSeparated;

    public abstract void execute();
    public abstract String getHelp();
    public abstract void checkArgs();//????

    public boolean isInputSeparated() {
        return isInputSeparated;
    }
}
