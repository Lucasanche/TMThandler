package repositories;

import entities.Network;

import java.util.List;

public interface Repository <T>{
    //CRUD
    void save(T t);
    List<T> listAll();
    T findById(String t);
    void update(T t);
    void delete(T t);
}
