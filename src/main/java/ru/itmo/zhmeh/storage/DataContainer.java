package ru.itmo.zhmeh.storage;

import ru.itmo.zhmeh.domain.Calibration;
import ru.itmo.zhmeh.domain.Instrument;
import ru.itmo.zhmeh.domain.Maintenance;

import java.util.ArrayList;
import java.util.List;

public class DataContainer {
    private List<Instrument> instruments = new ArrayList<>();
    private List<Calibration> calibrations = new ArrayList<>();
    private List<Maintenance> maintenances = new ArrayList<>();

    public DataContainer() {} //TODO просто для галочки или что-то добавить? - в видео


    public List<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<Instrument> instruments) {
        this.instruments = instruments;
    }

    public List<Calibration> getCalibrations() {
        return calibrations;
    }

    public void setCalibrations(List<Calibration> calibrations) {
        this.calibrations = calibrations;
    }

    public List<Maintenance> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(List<Maintenance> maintenances) {
        this.maintenances = maintenances;
    }
}
