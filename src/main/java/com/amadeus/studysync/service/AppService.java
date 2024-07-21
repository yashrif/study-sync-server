package com.amadeus.studysync.service;

import java.util.List;
import java.util.Map;

public interface AppService<T, K, ID> {

    List<T> findAll();

    T findById(ID theId);

    T save(K theEntity);

    void deleteById(ID theId);

    T update(T theEntity);

    T partialUpdate(ID theId, Map<String, Object> updates);
}