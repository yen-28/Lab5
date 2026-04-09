package ru.itmo.zhmeh.cli;

public abstract class Command {





    public abstract void execute(Environment environment, String args);
    public abstract String getHelp();
    //public abstract void checkArgs();//????

//    public boolean getIsInputSeparated() {
//        return isInputSeparated;
//    }


}
