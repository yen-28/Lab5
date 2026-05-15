package ru.itmo.zhmeh;

import ru.itmo.zhmeh.cli.*;
import ru.itmo.zhmeh.cli.commands.Load;
import ru.itmo.zhmeh.storage.DataContainer;
import ru.itmo.zhmeh.storage.StorageException;

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
                new Load().execute(environment, defaultPath.toString());
                System.out.println("ДАННЫЕ ЗАГРУЖЕНЫ ИЗ: " + defaultPath);
            } catch (Exception e) {
                System.err.println("Ошибка загрузки файла (" + e.getMessage() + "). Запуск с пустой базы");
            }
        }
        else {
            System.out.println("Файл " + defaultPath + " не найден. Запуск с пустой базы");
        }

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
                //TODO сделать ввод и использование команд

            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }


        }
    }
}
