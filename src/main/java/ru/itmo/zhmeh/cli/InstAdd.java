package ru.itmo.zhmeh.cli;

import ru.itmo.zhmeh.service.InstrumentsManager;

public final class InstAdd extends Command{

    private final boolean isInputSeparated = true; //???
    private final String name = "inst_add";

/*
ВСЁ СДЕЛАТЬ ПО УМУ
 */
    @Override
    public void execute() {


    }

    @Override
    public String getHelp() {
        return name + ": добавить новый прибор";
    }

    @Override
    public void checkArgs() {

    }

    public String getName() {
        return name;
    }

    @Override
    public boolean isInputSeparated() {
        return isInputSeparated;
    }
}
