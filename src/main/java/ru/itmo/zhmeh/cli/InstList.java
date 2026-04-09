package ru.itmo.zhmeh.cli;

import ru.itmo.zhmeh.domain.Instrument;

import java.util.List;

public final class InstList extends Command {

    private static final String name = "inst_list";


    @Override
    public void execute(Environment environment, String args) {
        String[] argsList = environment.getReader().splitArgs(args);

        String key = "";
        String value = "";
        List<Instrument> filtred;

        if (argsList.length == 2) {
            key = argsList[0];
            value = argsList[1]; //TODO Где-то ошибка с индексами, может в ридере
            filtred = environment.getInstrumentsManager().filterInstList(key, value);
        } else {
            filtred = environment.getInstrumentsManager().filterInstList(key, value);
        }


        if (filtred.isEmpty()) {
            throw new IllegalArgumentException("Список инструментов с выбранным параметром пуст");
        }

        switch (key) {
            case "--type":
                System.out.printf("%-5s %-30s %-25s %s%n", "ID", "Name", "Status", "\n");
                for (Instrument inst : filtred) {
                    System.out.printf("%-5d %-30s %-25s %s%n",
                            inst.getId(),
                            truncate(inst.getName(), 30),
                            inst.getStatus(), "\n"
                    );
                } break;
            case "--status":
                System.out.printf("%-5s %-30s %-25s %s%n", "ID", "Name", "Type", "\n");
                for (Instrument inst : filtred) {
                    System.out.printf("%-5d %-30s %-25s %s%n",
                            inst.getId(),
                            truncate(inst.getName(), 30),
                            inst.getType().toString(), "\n"
                    );
                } break;
            default:
                System.out.printf("%-5s %-30s %-25s %-25s %s%n", "ID", "Name", "Type", "Status", "\n");
                for (Instrument inst : filtred) {
                    System.out.printf("%-5d %-30s %-25s %-25s %s%n",
                            inst.getId(),
                            truncate(inst.getName(), 30),
                            inst.getType().toString(),
                            inst.getStatus().toString(), "\n"
                    );
                }

        }
    }


    public static String getName() {
        return name;
    }


    @Override
    public String getHelp() {
        return name + ": Показывает список приборов по определённому типу(--type TYPE)/статусу(--status STATUS)/все()";
    }
}
