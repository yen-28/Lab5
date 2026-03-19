package ru.itmo.zhmeh.service;

public abstract class Manager {
    protected long nextId = 1;


    protected long generateId() {
        return nextId++;
    }
}
