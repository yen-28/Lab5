package ru.itmo.zhmeh;

import ru.itmo.zhmeh.service.InstrumentsManager;

public class Main {
    public static void main(String[] args){
        InstrumentsManager manager = new InstrumentsManager();
        manager.addNew(null, "gbsdv", "PH_METER", "123", "баня", "active");
        System.out.println(manager.getById(1));
    }
}
