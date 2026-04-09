package ru.itmo.zhmeh.cli;

import ru.itmo.zhmeh.service.CalibrationManager;
import ru.itmo.zhmeh.service.InstrumentsManager;
import ru.itmo.zhmeh.service.MaintenanceManager;

import java.lang.reflect.Member;

public final class Environment {
    private final MyReader reader;
    private final CommandManager commandManager;
    private final InstrumentsManager instrumentsManager;
    private final CalibrationManager calibrationManager;
    private final MaintenanceManager maintenanceManager;


    private CommandManager addCommands(CommandManager commandManager){
        commandManager.addCommand(InstAdd.getName(), new InstAdd());
        commandManager.addCommand(Help.getName(), new Help());
        commandManager.addCommand(InstList.getName(), new InstList());

        return commandManager;

    }


    public Environment(MyReader reader) {
        this.reader = reader;
        this.commandManager = addCommands(new CommandManager());
        this.instrumentsManager = new InstrumentsManager();
        this.calibrationManager = new CalibrationManager(instrumentsManager);
        this.maintenanceManager = new MaintenanceManager(instrumentsManager);
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public MyReader getReader() {
        return reader;
    }

    public InstrumentsManager getInstrumentsManager() {
        return instrumentsManager;
    }

    public CalibrationManager getCalibrationManager() {
        return calibrationManager;
    }

    public MaintenanceManager getMaintenanceManager() {
        return maintenanceManager;
    }
}
