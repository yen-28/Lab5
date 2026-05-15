package ru.itmo.zhmeh;

import ru.itmo.zhmeh.cli.*;
import ru.itmo.zhmeh.cli.commands.Load;
import ru.itmo.zhmeh.storage.DataContainer;
import ru.itmo.zhmeh.storage.DataLoader;
import ru.itmo.zhmeh.storage.StorageException;
import ru.itmo.zhmeh.validation.FileValidationException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    //public static final InstrumentsManager instrumentsManager = new InstrumentsManager();
    public static void main(String[] args) {

        // для второй лабы парсим аргументы запуска
        Path defaultPath = Path.of("data.json");
        for (String arg : args){
            if (arg.startsWith("--data-file=")) {
                defaultPath = Path.of(arg.substring("--data-file=".length()));
            }
        }


        Scanner scanner = new Scanner(System.in);
        Environment environment = new Environment(new MyReader(scanner));

        if (Files.exists(defaultPath)) {
            try {
                DataLoader.loadData(defaultPath, environment); // TODO но это не load???
                System.out.println("ДАННЫЕ ЗАГРУЖЕНЫ ИЗ: " + defaultPath);
            } catch (StorageException e) {
                System.err.println("Ошибка чтения файла (" + e.getMessage() + "). Запуск с пустой базы");
            } catch (FileValidationException e) {
                System.err.println("Ошибка валидации файла (" + e.getMessage() + "). Запуск с пустой базы");
            } catch (Exception e) { //TODO под вопросом
                System.err.println("Другая ошибка чтения файла (" + e.getMessage() + "). Запуск с пустой базы");
            }
        }
        else {
            System.out.println("Файл " + defaultPath + " не найден. Запуск с пустой базы");
        }

        System.out.println("ДОБРО ПОЖАЛОВАТЬ! МЫ РАДЫ ВАС ВИДЕТЬ \n" +
                "Введите команду или 'help' (ПОМОГИТЕ!) \n ");

        while (true) {
            String input = scanner.nextLine().trim();

            try {
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("ВЫХОД. СПАСИБО ЗА ИСПОЛЬЗОВАНИЕ!");
                    break;
                }
                if (input.isEmpty()) continue;

                String[] splitedInput = environment.getReader().splitInput(input);
                String commandName = splitedInput[0];
                String commandArgs = (splitedInput.length > 1) ? splitedInput[1] : "";
                if (environment.getCommandManager().getCommands().containsKey(commandName)) {
                    environment.getCommandManager().getCommands().get(commandName).execute(environment, commandArgs); //замучился придумывать гениальную архитектуру
                } else {
                    System.err.println("Command not found");
                }

                System.out.println();
                //TODO сделать ввод и использование команд

            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }


        }
    }
}
