package ru.itmo.zhmeh.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class AbstractDataStorage implements CollectionStorage {

    protected void ensureParentDirectoryExists(Path path) throws StorageException {
        try {
            Path parent = path.getParent();
            if (parent != null && Files.notExists(parent)) {
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            throw new StorageException("Не удалось создать папку: " + path.getParent(), e); // криэйт дерикториез кидает ИОЕ, поэтому мы оборачиваем ИОЕ в своё
        }
    }

}

