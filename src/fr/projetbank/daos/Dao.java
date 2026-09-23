package fr.projetbank.daos;

import java.util.List;

public interface Dao<T> {

    T readById(int id);

    List<T> readAll();

    T create(T object);

    boolean update(T object);

    boolean delete(int id);
}