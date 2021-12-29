package com.example.project.model.dao;

import com.example.project.domain.Entity;

import java.util.List;

public interface GenericDAO<K, T extends Entity> {
    List<T> findAll();
    T findById(K id);
    boolean delete(K id);
    boolean delete(T entity);
    T create(T entity);
    T update(T entity);
}
