package com.ibm.ph.edm.common.dao;

import java.io.Serializable;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

public interface CrudDao <T, ID extends Serializable> {
    <S extends T> S save(S var1);

    <S extends T> Iterable<S> save(Iterable<S> var1);

    T findOne(ID var1);

    boolean exists(ID var1);

    Iterable<T> findAll();

    Iterable<T> findAll(Iterable<ID> var1);

    long count();

    void delete(ID var1);

    void delete(T var1);

    void delete(Iterable<? extends T> var1);

    void deleteAll();
}
