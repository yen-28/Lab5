package ru.itmo.zhmeh;

import ru.itmo.zhmeh.cli.*;
import ru.itmo.zhmeh.service.*;

import java.util.Scanner;

public class Main {
    public static final InstrumentsManager instrumentsManager = new InstrumentsManager();
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);  //пока не разбирался

        CommandManager commandManager = new CommandManager(); // добавляю команды в список
        commandManager.addCommand(InstAdd.getName(), new InstAdd());
        commandManager.addCommand(Help.getName(), new Help()); //TODO сделать хелп



        while (true){
            String input = scanner.nextLine();
            try {
                if (input.equalsIgnoreCase("exit")){
                    System.out.println("ВЫХОД. СПАСИБО ЗА ИСПОЛЬЗОВАНИЕ!");
                    break;
                }
                if (input.isEmpty()) continue;

                //TODO сделать ввод и использование команд

            }
            catch (Exception e){
                System.out.println("Ошибка: " + e.getMessage());
            }


        }
    }
}
