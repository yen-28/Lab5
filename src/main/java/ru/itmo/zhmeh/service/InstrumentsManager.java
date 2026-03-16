package ru.itmo.zhmeh.service;

import ru.itmo.zhmeh.things.Instrument;
import ru.itmo.zhmeh.things.InstrumentType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
К КАЖДОМУ МЕТОДУ ДОБАВИТЬ ВЫВОД О ЗАВЕРШЕНИИ?
 */
public class InstrumentsManager {
    private final Map<Long, Instrument> instruments = new HashMap<>();

    private long nextId = 1;


    private long generateId() {
        return nextId++;
    }

    public void checkInstrumentExistsId(long id){
        if (!instruments.containsKey(id)){
            throw new IllegalArgumentException("Ошибка: прибор с id: " + id + " не найден");
        }
    }

    /*
    Instrument :: getInventoryNumber эквивалентно лямбда выражению:
    inst -> inst.getInventoryNumbe
    - оно берёт каждый новый "инст" и для него выполняет метод
     */

    private void validateInventoryNumber(String number) {
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
    public void addInstrument(String ownerUsername, String name, InstrumentType type, String inventoryNumber, String location, String status){
        validateInventoryNumber(inventoryNumber);
        long id = generateId();
        Instrument instrument = new Instrument(id, ownerUsername, name, type, inventoryNumber, location, status);
        instruments.put(id, instrument);
    }

    public Instrument getById(long id){
        checkInstrumentExistsId(id);
        return instruments.get(id);
    }

    public void list() {
        System.out.println(getInstruments()); //возможно стоит сделать красивее !!
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

    public void remove(Long id){
        checkInstrumentExistsId(id);
        instruments.remove(id);
    }




    public Map<Long, Instrument> getInstruments() {
        return instruments;
    }
}
