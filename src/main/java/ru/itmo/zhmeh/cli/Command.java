package ru.itmo.zhmeh.cli;

public abstract class Command {





    public abstract void execute(Environment environment, String args);
    public abstract String getHelp();

    protected String truncate(String text, int maxLength) {
        if (text == null) return "-";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }

    protected long readId(String sId){
        try {
           return Long.parseLong(sId.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат ID",e);
        }
    }

    //public abstract void checkArgs();//????

//    public boolean getIsInputSeparated() {
//        return isInputSeparated;
//    }


}
