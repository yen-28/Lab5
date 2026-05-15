package ru.itmo.zhmeh.storage;

import ru.itmo.zhmeh.cli.Environment;
import ru.itmo.zhmeh.validation.FileValidationException;
import ru.itmo.zhmeh.validation.FileValidator;

import java.nio.file.Path;


public final class DataLoader {

    /**
     * Нужен, чтобы нормально обрабатывать ошибки хранилища в Main
     * @param path
     * @param environment
     * @throws StorageException
     * @throws FileValidationException
     */
    public static void loadData(Path path, Environment environment) throws StorageException, FileValidationException {

        DataContainer container = environment.getDataStorage().load(path); // временный контейнер

        new FileValidator().validate(container); // валидация

        environment.getInstrumentsManager().replaceAll(container.getInstruments());
        environment.getCalibrationManager().replaceAll(container.getCalibrations());
        environment.getMaintenanceManager().replaceAll(container.getMaintenances());

    }

}
