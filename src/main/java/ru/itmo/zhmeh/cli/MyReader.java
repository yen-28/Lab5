package ru.itmo.zhmeh.cli;

import java.util.Scanner;

public final class MyReader {

//    private String commandName;
//    public String[] commandArgs;

    private final Scanner scanner;

    public MyReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String[] splitInput(String input){
        return input.trim().split(" ", 2);


    }

    public String readString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

    public String readNonEmptyString(String prompt) { //TODO хочу или нет?
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Ошибка: значение не может быть пустым");
        }
    }

    public long readLong(String prompt){
        System.out.println(prompt);
        String value = scanner.nextLine().trim();
        while (true) {
            try {
                return Long.parseLong(value); //есть get? но пусть будет так
            } catch (NumberFormatException e) {
                System.out.println("Некорректное число");
            }
        }

    }


}
