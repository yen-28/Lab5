package ru.itmo.zhmeh.cli.commands;

import ru.itmo.zhmeh.cli.Environment;
import ru.itmo.zhmeh.domain.Calibration;
import ru.itmo.zhmeh.domain.Instrument;

import java.util.List;

public final class CalList extends Command { //TODO КАК-ТО РЕШИТЬ ВОПРОС С id КАЛИБРОВКИ (ПРИ НЕУДАЧНОМ СОЗДАНИИ id ЗАНИМАЕТСЯ, ПОСЛЕДНИЕ N ПОКАЗЫВАЕТ НЕПРАВИЛЬНО)

    private static final String name = "cal_list";

    @Override
    public void execute(Environment environment, String args) {
        String[] argsList = environment.getReader().splitArgs(args);

        long instId = parseId(argsList[0]);
        String key = "";
        long value = 0;
        List<Calibration> filtred;

        if (argsList.length == 3){
            key = argsList[1];
            value = Long.parseLong(argsList[2]);
            filtred = environment.getCalibrationManager().getCalibrationsListByInstId(instId, key, value);
        }
        else {
            filtred = environment.getCalibrationManager().getCalibrationsListByInstId(instId, key, value);
        }

        if ((filtred.isEmpty())){
            throw new IllegalArgumentException("Список калибровок для выбранного инструмента пуст");
        }

        System.out.printf("%-5s %-20s %-10s %-15s %-30s %s%n", "ID", "Type", "Result", "Time", "Comment", "\n");
        for (Calibration cal : filtred) {
            System.out.printf("%-5d %-20s %-10s %-15s %-30s %s%n",
                    cal.getId(),
                    cal.getType().toString(),
                    cal.getResult().toString(),
                    dateFormater(cal.getCalibratedAt()),
                    truncate(cal.getComment(), 30), "\n"
            );
        }
    }

    @Override
    public String getHelp() {
        return name + ": список калибровок по ID прибора (можно последние N (--last N))";
    }

    public static String getName() {
        return name;
    }
}
