package ru.itmo.zhmeh;

import ru.itmo.zhmeh.cli.*;

import java.util.Scanner;

public class Main {
    //public static final InstrumentsManager instrumentsManager = new InstrumentsManager();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Environment environment = new Environment(new MyReader(scanner)); //пока не разбирался

        //TODO сделать хелп


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
                if(environment.getCommandManager().getCommands().containsKey(commandName)) {
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
