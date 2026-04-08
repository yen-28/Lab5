package ru.itmo.zhmeh.cli;

import ru.itmo.zhmeh.service.InstrumentsManager;

public final class InstAdd extends Command {

    private static final String name = "inst_add";

    /*
    ВСЁ СДЕЛАТЬ ПО УМУ
     */
    @Override
    public void execute(CommandManager cm, MyReader reader, String args) {


//TODO 1 Здесь делаем ввод с помощью ридера
    }

    public static String getName() {
        return name;
    }

    @Override
    public String getHelp() {
        return name + ": добавить новый прибор";
    }

//    @Override
//    public void checkArgs() {
//
//    }
}
