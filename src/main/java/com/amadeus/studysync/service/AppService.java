package com.amadeus.studysync.service;

import java.security.Principal;
import java.util.List;

public interface AppService<T, K, P, ID> {

    List<T> findAll(Principal connectedUser);

    T findById(ID theId, Principal connectedUser);

    T save(K theEntity);

    void deleteById(ID theId, Principal connectedUser);

    T update(T theEntity, Principal connectedUser);

    T partialUpdate(ID theId, P updates, Principal connectedUser) throws Exception;
}