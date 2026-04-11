package ru.itmo.zhmeh.cli.commands;

import ru.itmo.zhmeh.cli.Environment;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public abstract class Command {





    public abstract void execute(Environment environment, String args);
    public abstract String getHelp();

    protected String truncate(String text, int maxLength) {
        if (text == null) return "-";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }

    public static long parseId(String sId) {
        while (true) { //TODO Посмотрим, как сработает
            try {
                return Long.parseLong(sId.trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Неверный формат ID", e);
            }
        }
    }

    public static String dateFormater(Instant instant){
        if (instant == null) {
            return "-";
        } else return instant.atZone(ZoneId.systemDefault()).toLocalDate().toString();
    }

    //public abstract void checkArgs();//????

//    public boolean getIsInputSeparated() {
//        return isInputSeparated;
//    }


}
