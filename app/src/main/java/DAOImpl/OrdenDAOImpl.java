package DAOImpl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import conexion.ConexionSQLite;
import dao.OrdenDAO;
import modelo.CompraDetalle;
import modelo.Orden;

public class OrdenDAOImpl implements OrdenDAO {

    private Context contexto;
    private ConexionSQLite objConexion;
    private SQLiteDatabase objQuery;

    public OrdenDAOImpl(Context contexto) {
        this.contexto = contexto;
        objConexion = new ConexionSQLite(this.contexto);
        objQuery = objConexion.getWritableDatabase();
    }

    @Override
    public void registrar(Orden orden) {
        String query = "INSERT INTO orden(cantidad, precioTotal, idProducto, idUsuario)" +
                "values('" + orden.getCantidad() + "','" + orden.getPrecioTotal() + "', '" + orden.getIdProducto() + "', '" + orden.getIdUsuario() + "')";
        objQuery.execSQL(query);
    }

    @Override
    public void actualizar(Orden orden) {

    }

    @Override
    public List<Orden> listar() {
        return null;
    }

    @Override
    public void eliminar(int id) {
        String query = "DELETE FROM orden WHERE idOrden = '" + id + "'";
        objQuery.execSQL(query);
    }

    @Override
    public List<Orden> listarPorId(int id) {
        return null;
    }

    @Override
    public List<CompraDetalle> listarCompra(int id) {
        List<CompraDetalle> lista = new ArrayList<CompraDetalle>();
        CompraDetalle comp = null;
        String query = "SELECT usuario.usuario, producto.imagenProducto, producto.producto, orden.cantidad, producto.precio, orden.precioTotal, orden.idOrden " +
                "FROM producto " +
                "INNER JOIN orden ON orden.idproducto = producto.idproducto " +
                "INNER JOIN usuario ON usuario.idusuario = orden.idusuario WHERE usuario.idusuario = '" + id + "'";
        Cursor datos = objQuery.rawQuery(query, null);
        while (datos.moveToNext()) {
            comp = new CompraDetalle();
            comp.setUsuario(datos.getString(0));
            comp.setImagen(datos.getString(1));
            comp.setProducto(datos.getString(2));
            comp.setCantidad(datos.getInt(3));
            comp.setPrecio(datos.getDouble(4));
            comp.setPrecioTotal(datos.getDouble(5));
            comp.setIdOrden(datos.getInt(6));
            lista.add(comp);
        }
        return lista;
    }

    @Override
    public double mostrarTotal(int id) {
        double t = 0;
        String query = "SELECT sum(preciototal) " +
                "FROM orden " +
                "WHERE idusuario = '" + id + "'";
        Cursor datos = objQuery.rawQuery(query, null);
        while (datos.moveToNext()) {
            t = datos.getDouble(0);
        }
        return t;
    }

    @Override
    public void eliminarOrden(int id) {
        String query = "DELETE FROM orden WHERE idUsuario = '" + id + "'";
        objQuery.execSQL(query);
    }
}
