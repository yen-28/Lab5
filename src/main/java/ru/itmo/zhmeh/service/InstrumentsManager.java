package ru.itmo.zhmeh.service;

import ru.itmo.zhmeh.things.Instrument;
import ru.itmo.zhmeh.things.InstrumentStatus;
import ru.itmo.zhmeh.things.InstrumentType;

import java.util.HashMap;
import java.util.Map;


public class InstrumentsManager {
    private final Map<Long, Instrument> instruments = new HashMap<>();

    private long nextId = 1;


    public long generateId() {
        return nextId++;
    }

    public void addInstrument(String ownerUsername, String name, InstrumentType type, String inventoryNumber, String location, InstrumentStatus status ){


      //  instruments.put(generateId(), instrument);
    }


}
