package ru.itmo.zhmeh.cli;

public abstract class Command {

     //TODO АДЕКВАТНО СДЕЛАТЬ ИМЯ
    //protected boolean isInputSeparated; //TODO решить надо или нет




    public abstract void execute(CommandManager cm, String args);
    public abstract String getHelp();
    //public abstract void checkArgs();//????

//    public boolean getIsInputSeparated() {
//        return isInputSeparated;
//    }


}
