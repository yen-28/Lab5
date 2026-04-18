package ru.itmo.zhmeh.cli;

import ru.itmo.zhmeh.cli.commands.Command;

import java.util.Scanner;

public final class MyReader {

    private final Scanner scanner;

    public MyReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String[] splitInput(String input){
        return input.trim().split(" ", 2);
    }

    public String[] splitArgs(String args){
        if (args != null || !args.isEmpty() ){
            return args.trim().split(" ");
        } else return new String[]{""}; //TODO норм/не норм?
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Ошибка: значение не может быть пустым");
        }
    }

    public long readLongId(String prompt){
        System.out.print(prompt);
        String value = scanner.nextLine().trim();
        return Command.parseId(value);

    }


}
