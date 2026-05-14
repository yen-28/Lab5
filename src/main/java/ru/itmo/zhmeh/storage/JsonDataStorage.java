package ru.itmo.zhmeh.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonDataStorage extends AbstractDataStorage { // TODO сделать наследование нормально

    private final ObjectMapper objectMapper;


    public JsonDataStorage() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);  // Отключаем сериализацию дат как timestamp (будет строка ISO-8601)
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);


    }


    @Override
    public void save(DataContainer data, Path path) throws StorageException {
        try {
            ensureParentDirectoryExists(path);
            objectMapper.writeValue(path.toFile(), data);
        } catch (IOException e) {
            throw new StorageException("Ошибка сохранения: " + path, e);
        }
    }

    @Override
    public DataContainer load(Path path) throws StorageException {
        try {
            if (!Files.exists(path)) {
                return new DataContainer();
            }
            return objectMapper.readValue(path.toFile(), DataContainer.class);
        } catch (JsonProcessingException e) {
            // Специфичная ошибка парсинга JSON
            String userMsg = "Файл повреждён или не соответствует формату. " + e.getOriginalMessage();
            throw new StorageException(userMsg, e);
        } catch (IOException e) {
            // Ошибка файловой системы
            throw new StorageException("Нет доступа к файлу или диск переполнен: " + path, e);
        }
    }
}
