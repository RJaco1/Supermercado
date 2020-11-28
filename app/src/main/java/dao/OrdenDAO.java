package dao;

import java.util.List;

import modelo.CompraDetalle;
import modelo.Orden;

public interface OrdenDAO extends CRUD<Orden> {

    List<CompraDetalle> listarCompra(int id);
    double mostrarTotal(int id);
    void eliminarOrden(int id);
}
