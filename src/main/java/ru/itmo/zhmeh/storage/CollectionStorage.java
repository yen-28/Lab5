package ru.itmo.zhmeh.storage;

import java.nio.file.Path;
import java.util.List;

public interface CollectionStorage<T> {

    void save(T data, Path path) throws StorageException;

    T load (Path path) throws StorageException;

}
