package ru.itmo.zhmeh.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import ru.itmo.zhmeh.domain.Instrument;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InstrumentManagerTest {

    private InstrumentsManager manager;

    @BeforeEach
    void  setUp(){
        manager = new InstrumentsManager();
    }

    @Test
    @DisplayName("Добавление прибора")
    void testAddInstrument(){
        //Подготовка

        InstrumentsManager manager = new InstrumentsManager();
        manager.addNew(null, "gbsdv", "PH_METER", "123", "баня", "active");

        //Действие

        Map<Long, Instrument> instruments = manager.getInstruments();
        Assertions.assertTrue(instruments.containsKey(1L));

    }


}
