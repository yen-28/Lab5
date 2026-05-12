package ru.itmo.zhmeh.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class AbstractFileStorage<T> implements CollectionStorage<T> {

    protected void ensureParentDirectoryExists(Path path) throws StorageException {
        Path parent = path.getParent();

        if (parent != null && Files.notExists(parent)) {
            try {
                Files.createDirectories(parent);
            } catch (IOException e) {
                throw new StorageException("Не удалось создать папку: " + parent); // криэйт дерикториез кидает ИОЕ, поэтому мы оборачиваем ИОЕ в своё
            }
        }
    }
}
