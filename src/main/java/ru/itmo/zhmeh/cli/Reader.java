package ru.itmo.zhmeh.cli;

import java.util.Scanner;

public final class Reader {

    private final Scanner scanner;

    public Reader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();
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
