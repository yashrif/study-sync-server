package com.amadeus.studysync.service;

import java.util.List;

public interface AppService<T, K, P, ID> {

    List<T> findAll();

    T findById(ID theId);

    T save(K theEntity);

    void deleteById(ID theId);

    T update(T theEntity);

    T partialUpdate(ID theId, P updates) throws Exception;
}