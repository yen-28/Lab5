package ru.itmo.zhmeh.validation;

import java.nio.file.Path;

public final class PathValidator {

    /**
     * Возвращает Path path (обрезанный без <>)
     * @param strPath
     * @return
     */
    public static Path validateStrPath(String strPath){
        if (strPath == null || strPath.isBlank()) {
            throw new IllegalArgumentException("Ошибка: укажите путь к файлу. Пример: save (или load) <data.json>");
        }


        Path path = Path.of(strPath.substring(1, strPath.length() - 1)); // +обрезали

        // Проверка расширения (регистронезависимая)
        String fileName = path.getFileName().toString();
        if (!fileName.toLowerCase().endsWith(".json")) {
            throw new IllegalArgumentException("Ошибка: файл должен иметь расширение .json");
        }

        return path;
    }
}
