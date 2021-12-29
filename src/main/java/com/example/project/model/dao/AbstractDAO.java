package com.example.project.model.dao;

import com.example.project.domain.Entity;

import java.util.List;

public abstract class AbstractDAO<K, T extends Entity> implements GenericDAO<K, T> {
    public abstract List<T> findAll();
    public abstract T findById(K id);
    public abstract boolean delete(K id);
    public abstract boolean delete(T entity);
    public abstract T create(T entity);
    public abstract T update(T entity);
}
