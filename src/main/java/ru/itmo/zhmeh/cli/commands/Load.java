package ru.itmo.zhmeh.cli.commands;

import ru.itmo.zhmeh.cli.Environment;
import ru.itmo.zhmeh.storage.DataContainer;
import ru.itmo.zhmeh.storage.StorageException;
import ru.itmo.zhmeh.validation.FileValidationException;
import ru.itmo.zhmeh.validation.FileValidator;
import ru.itmo.zhmeh.validation.PathValidator;

import java.nio.file.Path;

public final class Load extends Command{
    private static final String name = "load";


    @Override
    public void execute(Environment environment, String args) {

        Path path = PathValidator.validateStrPath(args);

        try {

            DataContainer container = environment.getDataStorage().load(path); // временный контейнер

            new FileValidator().validate(container); // валидация

            environment.getInstrumentsManager().replaceAll(container.getInstruments());
            environment.getCalibrationManager().replaceAll(container.getCalibrations());
            environment.getMaintenanceManager().replaceAll(container.getMaintenances());

            System.out.println("OK данные загружены из " + path);

        } catch (StorageException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        } catch (FileValidationException e) {
            System.err.println("Ошибка валидации данных: " + e.getMessage());
        }


    }

    @Override
    public String getHelp() {
        return name + ": загрузить данные из файла";
    }

    public static String getName() {
        return name;
    }
}
