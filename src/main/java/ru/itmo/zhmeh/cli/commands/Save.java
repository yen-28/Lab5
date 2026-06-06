package ru.itmo.zhmeh.cli.commands;

import ru.itmo.zhmeh.cli.Environment;
import ru.itmo.zhmeh.storage.DatabaseException;
import ru.itmo.zhmeh.validation.PathValidator;

import java.nio.file.Path;
import java.util.ArrayList;

public final class Save extends Command {
    private static final String name = "save";

    @Override
    public void execute(Environment environment, String args) {

        Path path = PathValidator.validateStrPath(args);

//        try {
//
////            DataContainer container = new DataContainer();
////            container.setInstruments(new ArrayList<>(environment.getInstrumentsManager().getColInstruments())); // Collection??
////            container.setCalibrations(new ArrayList<>(environment.getCalibrationManager().getColCalibrations()));
////            container.setMaintenances(new ArrayList<>(environment.getMaintenanceManager().getColMaintenance()));
//
////            environment.getDataStorage().save(container, path);
////            System.out.println("Данные сохранены в " + path); //
//
//        } catch (DatabaseException e) {
//            System.err.println("Ошибка: " + e.getMessage());
//        }
    }

    public static String getName() {
        return name;
    }

    @Override
    public String getHelp() {
        return name + ": сохранить данные";
    }
}
