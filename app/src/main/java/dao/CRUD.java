package dao;

import java.util.List;

public interface CRUD<T> {

    void registrar(T t);

    void actualizar(T t);

    List<T> listar();

    void eliminar(int id);

    List<T> listarPorId(int id);
}
