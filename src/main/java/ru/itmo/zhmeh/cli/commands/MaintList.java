package ru.itmo.zhmeh.cli.commands;

import ru.itmo.zhmeh.cli.Environment;
import ru.itmo.zhmeh.domain.Maintenance;

import java.util.List;

public final class MaintList extends Command{

    private static final String name = "maint_list";

    @Override
    public void execute(Environment environment, String args) {
        String[] argsList = environment.getReader().splitArgs(args);

        long instId = parseId(argsList[0]);
        String key = "";
        long value = 0;
        List<Maintenance> filtred;

        if (argsList.length == 3){
            key = argsList[1];
            value = Long.parseLong(argsList[2]);
            filtred = environment.getMaintenanceManager().getMaintenancesListByInstId(instId, key, value);
        }
        else {
            filtred = environment.getMaintenanceManager().getMaintenancesListByInstId(instId, key, value);
        }

        if ((filtred.isEmpty())){
            throw new IllegalArgumentException("Список обслуживаний для выбранного инструмента пуст");
        }

        System.out.printf("%-5s %-20s %-15s %-30s %s%n", "ID", "Type", "Time", "Details", "\n");
        for (Maintenance maint : filtred) {
            System.out.printf("%-5d %-20s %-15s %-30s %s%n",
                    maint.getId(),
                    maint.getType().toString(),
                    dateFormater(maint.getDoneAt()),
                    truncate(maint.getDetails(), 30), "\n"
            );
        }
    }

    @Override
    public String getHelp() {
        return name + ": список обслуживаний по ID прибора (можно последние N (--last N))";
    }

    public static String getName() {
        return name;
    }
}
