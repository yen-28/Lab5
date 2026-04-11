package ru.itmo.zhmeh.service;

import ru.itmo.zhmeh.domain.Instrument;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/*
К КАЖДОМУ МЕТОДУ ДОБАВИТЬ ВЫВОД О ЗАВЕРШЕНИИ? ++
 */
public final class InstrumentsManager {
    private final Map<Long, Instrument> instruments = new HashMap<>(); //квен порекомендовал меп на всякий случай

    private long nextId = 1;

    public void checkInstrumentExistsId(long id){ //паблик на всякий случай, вдруг надо будет
        if (!instruments.containsKey(id)){
            throw new IllegalArgumentException("Ошибка: прибор с id: " + id + " не найден");
        }
    }

    /*
    Instrument :: getInventoryNumber эквивалентно лямбда выражению:
    inst -> inst.getInventoryNumbe
    - оно берёт каждый новый "инст" и для него выполняет метод
     */

    private void validateInventoryNumber(String number) throws IllegalArgumentException {
        if (number == null || number.isEmpty()) { //пустые и налл не проверяем
            return;
        }
        boolean exist = instruments.values().stream() //создали конвейер
                .map(Instrument::getInventoryNumber) //для каждого выбрали инвентори намбер, создали
                .filter(Objects::nonNull) //отбросили налл
                .anyMatch(number::equals); //проверка на принадлежность
        if (exist) {
            throw new IllegalArgumentException("Ошибка: инвентарный номер: " + number + " занят");
        }
    }
    public long addNew(String ownerUsername, String name, String type, String inventoryNumber, String location, String status){
        validateInventoryNumber(inventoryNumber);
        long id = nextId++;
        Instrument instrument = new Instrument(id, ownerUsername, name, type, inventoryNumber, location, status);
        instruments.put(id, instrument);

        return id;
    }

    public Instrument getById(long id){
        checkInstrumentExistsId(id);
        return instruments.get(id);
    }

    public List<Instrument> filterInstList(String key, String value) {
        switch (key){
            case "--type":
                return instruments.values().stream()
                        .filter(inst -> inst.getType().toString().equalsIgnoreCase(value))
                        .collect(Collectors.toList());
            case "--status":
                return instruments.values().stream()
                        .filter(inst -> inst.getStatus().toString().equalsIgnoreCase(value))
                        .collect(Collectors.toList());
            default: return instruments.values().stream().toList();
        }

    }

    public void update(long id, String field, String value){
        checkInstrumentExistsId(id);
        Instrument inst = instruments.get(id);

        switch (field){
            case "name":
                inst.setName(value);
                break;
            case "location":
                inst.setLocation(value);
                break;
            case "status":
                inst.setStatus(value);
                break;

            default:
                throw new IllegalArgumentException("Ошибка: нельзя изменить поле: " + field);
        }
    }

    public long remove(Long id){
        checkInstrumentExistsId(id);
        instruments.remove(id);
        return id;
    }

    public List<Instrument> dueInstruments(int days){  //название?
        Instant deadline = Instant.now().minus(days, ChronoUnit.DAYS);
        return instruments.values().stream()
                .filter(inst -> isInstrumentDue(inst, deadline))
                .collect(Collectors.toList());
    }

    public boolean isInstrumentDue(Instrument instrument, Instant deadline){ //логика для dueInstruments
        return instrument.getLastCalibration() == null || instrument.getLastCalibration().isBefore(deadline) || instrument.getLastCalibration().equals(deadline);
    }




    public Map<Long, Instrument> getInstruments() {
        return instruments;
    }
}
