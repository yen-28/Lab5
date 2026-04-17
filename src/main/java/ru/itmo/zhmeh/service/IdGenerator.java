package ru.itmo.zhmeh.service;

public final class IdGenerator {

    public static long generateId(long nextId) {

        return nextId++;
    }
}
