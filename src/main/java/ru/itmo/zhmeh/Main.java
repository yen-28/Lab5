package ru.itmo.zhmeh;

import ru.itmo.zhmeh.service.InstrumentsManager;
import ru.itmo.zhmeh.things.*;

import static ru.itmo.zhmeh.things.InstrumentStatus.ACTIVE;
import static ru.itmo.zhmeh.things.InstrumentType.*;

public class Main {
    public static void main(String[] args){
        InstrumentsManager manager = new InstrumentsManager();
        manager.addInstrument(null, "gbsdv", PH_METER, "123", "баня", "active");
        System.out.println(manager.getById(1));
    }
}
