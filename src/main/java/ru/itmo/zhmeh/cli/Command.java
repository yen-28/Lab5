package ru.itmo.zhmeh.cli;

public abstract class Command {

    protected String name;
    protected boolean isInputSeparated;

    public abstract void execute(CommandManager cm, String[] args);
    public abstract String getHelp();
    public abstract void checkArgs();//????

    public boolean getIsInputSeparated() {
        return isInputSeparated;
    }
    public String getName(){
        return name;
    }
}
