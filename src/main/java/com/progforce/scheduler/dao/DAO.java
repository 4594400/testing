package com.progforce.scheduler.dao;

import java.util.List;

public interface DAO<T> {
    void save(T value);
    T getById(int id);
    List<T> getAll();


}
