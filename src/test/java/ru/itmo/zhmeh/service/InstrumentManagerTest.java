package ru.itmo.zhmeh.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

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

        String name = "PH_METER";

        //Действие

    }


}
