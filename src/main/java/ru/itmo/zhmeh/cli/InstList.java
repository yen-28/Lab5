package ru.itmo.zhmeh.cli;

import ru.itmo.zhmeh.domain.Instrument;

import java.util.List;

public final class InstList extends Command {

    private static final String name = "inst_list";


    @Override
    public void execute(Environment environment, String args) {
        String[] argsList = environment.getReader().splitArgs(args);
        String key = argsList[0];
        String value = argsList[1]; //TODO Где-то ошибка с индексами, может в ридере

        List <Instrument> filtred = environment.getInstrumentsManager().filterInstList(key, value);

        if (filtred.isEmpty()){
            System.out.println("Список инструментов с выбранным параметром пуст");
        }

        switch (key) {
            case "--type":
                System.out.printf("%-5s %-30s %-25s %s%n", "ID", "Name", "Status");
                for (Instrument inst : filtred) {
                    System.out.printf("%-5d %-30s %-25s",
                            inst.getId(),
                            truncate(inst.getName(), 30),
                            inst.getStatus()
                    );
                }
            case "--status":
                System.out.printf("%-5s %-30s %-25s %s%n", "ID", "Name", "Type");
                for (Instrument inst : filtred) {
                    System.out.printf("%-5d %-30s %-25s",
                            inst.getId(),
                            truncate(inst.getName(), 30),
                            inst.getType()
                    );
                }
            default:
                System.out.printf("%-5s %-30s %-25s %-25s %s%n", "ID", "Name", "Type", "Status");
                for (Instrument inst : filtred) {
                    System.out.printf("%-5d %-30s %-25s %-25s",
                            inst.getId(),
                            truncate(inst.getName(), 30),
                            inst.getStatus(),
                            inst.getStatus()
                    );
                }

        }


    }
    public static String getName() {
        return name;
    }


    @Override
    public String getHelp() {
        return "Показывает список приборов по определённому типу(--type TYPE)/статусу(--status STATUS)/все()";
    }
}
