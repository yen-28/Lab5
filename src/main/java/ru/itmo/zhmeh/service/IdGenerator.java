package ru.itmo.zhmeh.service;

public final class IdGenerator {
    public static long nextId = 1;

    public static long generateId() {
        return nextId++;
    }
}
