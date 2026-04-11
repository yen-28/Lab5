package ru.itmo.zhmeh.cli.commands;

import ru.itmo.zhmeh.cli.Environment;
import ru.itmo.zhmeh.domain.Instrument;

import java.util.List;

public final class InstDue extends Command{

    private static final String name = "inst_due";

    @Override
    public void execute(Environment environment, String args) {
        String[] argsList = environment.getReader().splitArgs(args);

        if (argsList.length == 2 && argsList[0].equalsIgnoreCase("--days")) {
            int days;
            try{
                days = Integer.parseInt(argsList[1].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Ошибка: days должно быть числом");
            }

            List<Instrument> filtred = environment.getInstrumentsManager().dueInstruments(days);
            if (filtred.isEmpty()){
                throw new IllegalArgumentException("Список приборов пуст");
            }

            System.out.printf("%-12s %-20s %-20s %s%n", "Instrument", "LastCalibration", "Status", "\n");
            for (Instrument inst : filtred){
                System.out.printf("%-12d %-20s %-20s %s%n",
                        inst.getId(),
                        dateFormater(inst.getLastCalibration()),
                        inst.getStatus(), "\n");
            }
        }
        else {
            throw new IllegalArgumentException("Ошибка: Недостаточно параметров");
        }


    }

    @Override
    public String getHelp() {
        return name + ": список приборов с калибровкой старше N дней (--days N)";
    }

    public static String getName() {
        return name;
    }
}
