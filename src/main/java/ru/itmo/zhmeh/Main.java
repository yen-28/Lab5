package ru.itmo.zhmeh;

import ru.itmo.zhmeh.cli.*;

import java.util.Scanner;

public class Main {
    //public static final InstrumentsManager instrumentsManager = new InstrumentsManager();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  //пока не разбирался

        CommandManager commandManager = new CommandManager(); // добавляю команды в список
        commandManager.addCommand(InstAdd.getName(), new InstAdd());
        commandManager.addCommand(Help.getName(), new Help()); //TODO сделать хелп


        while (true) {
            String input = scanner.nextLine().trim();
            MyReader reader = new MyReader(scanner);
            try {
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("ВЫХОД. СПАСИБО ЗА ИСПОЛЬЗОВАНИЕ!");
                    break;
                }
                if (input.isEmpty()) continue;

                String[] splitedInput = reader.splitInput(input);
                String commandName = splitedInput[0];
                String commandArgs = (splitedInput.length > 1) ? splitedInput[1] : "";
                if(commandManager.getCommands().containsKey(commandName)) {
                    commandManager.getCommands().get(commandName).execute(commandManager, commandArgs); //замучился придумывать гениальную архитектуру
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
