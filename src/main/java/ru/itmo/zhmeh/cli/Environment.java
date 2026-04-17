package ru.itmo.zhmeh.cli;

import ru.itmo.zhmeh.cli.commands.*;
import ru.itmo.zhmeh.service.CalibrationManager;
import ru.itmo.zhmeh.service.InstrumentsManager;
import ru.itmo.zhmeh.service.MaintenanceManager;

/**
 * Класс, хранящий в себе все, что требуется для работы программы
 * <p>
 * Содержит:
 * <ol>
 *     <li> MyReader - для чтения из строки </li>
 *     <li> Менеджеры каждой сущности - для хранения коллекций и работы с ними</li>
 *     <li> CommandManager - для хранения списка команд</li>
 * </ol>
 * @see MyReader
 * @see CommandManager
 * @see InstrumentsManager
 * @see CalibrationManager
 * @see MaintenanceManager
 * </p>
 *
 *
 */

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
        commandManager.addCommand(InstShow.getName(), new InstShow());
        commandManager.addCommand(InstUpdate.getName(), new InstUpdate());
        commandManager.addCommand(CalAdd.getName(), new CalAdd());
        commandManager.addCommand(CalList.getName(), new CalList());
        commandManager.addCommand(CalShow.getName(), new CalShow());
        commandManager.addCommand(MaintAdd.getName(), new MaintAdd());
        commandManager.addCommand(MaintList.getName(), new MaintList());
        commandManager.addCommand(InstDue.getName(), new InstDue());
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
