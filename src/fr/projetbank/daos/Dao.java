package fr.projetbank.daos;

import java.util.List;

public interface Dao<T, ID> {

    T readById(ID id);

    List<T> readAll();

    T create(T object);

    boolean update(T object);

}