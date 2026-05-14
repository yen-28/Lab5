package ru.itmo.zhmeh.storage;

import java.nio.file.Path;

public interface CollectionStorage {

    void save(DataContainer data, Path path) throws StorageException;

    DataContainer load (Path path) throws StorageException;

}
