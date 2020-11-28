package DAOImpl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import conexion.ConexionSQLite;
import dao.ProductoDAO;
import modelo.Producto;

public class ProductoDAOImpl implements ProductoDAO {

    private Context contexto;
    private ConexionSQLite objConexion;
    private SQLiteDatabase objQuery;

    public ProductoDAOImpl(Context contexto) {
        this.contexto = contexto;
        objConexion = new ConexionSQLite(this.contexto);
        objQuery = objConexion.getWritableDatabase();
    }

    @Override
    public void registrar(Producto producto) {
        String query = "Insert into producto(producto, cantidad , precio, imagenProducto, idCategoria)" +
                "values('" + producto.getProducto() + "','" + producto.getCantidad() + "'," +
                "'" + producto.getPrecio() + "','" + producto.getImagenProducto() + "','" + producto.getIdCategoria() + "')";
        objQuery.execSQL(query);
    }

    @Override
    public void actualizar(Producto producto) {
        String query = "UPDATE producto SET producto = '" + producto.getProducto() + "', cantidad ='" + producto.getCantidad() + "'," +
                "precio = '" + producto.getPrecio() + "', imagenProducto = '" + producto.getImagenProducto() + "', idCategoria = '" + producto.getIdCategoria() + "'" +
                "WHERE idProducto = '" + producto.getIdProducto() + "'";
        objQuery.execSQL(query);
    }

    @Override
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<Producto>();
        Producto prod = null;
        String consulta = "Select * from producto";
        Cursor datos = objQuery.rawQuery(consulta, null);
        while (datos.moveToNext()) {
            prod = new Producto();
            prod.setIdProducto(datos.getInt(0));
            prod.setProducto(datos.getString(1));
            prod.setCantidad(datos.getInt(2));
            prod.setPrecio(datos.getDouble(3));
            prod.setImagenProducto(datos.getString(4));
            prod.setIdCategoria(datos.getInt(5));
            lista.add(prod);
        }
        return lista;
    }

    @Override
    public void eliminar(int id) {
        String query = "DELETE FROM producto WHERE idProducto = '" + id + "'";
        objQuery.execSQL(query);
    }

    @Override
    public List<Producto> listarPorId(int id) {
        List<Producto> lista = new ArrayList<Producto>();
        Producto prod = null;
        String consulta = "SELECT * FROM producto WHERE idCategoria = '" + id + "'";
        Cursor datos = objQuery.rawQuery(consulta, null);
        while (datos.moveToNext()) {
            prod = new Producto();
            prod.setIdProducto(datos.getInt(0));
            prod.setProducto(datos.getString(1));
            prod.setCantidad(datos.getInt(2));
            prod.setPrecio(datos.getDouble(3));
            prod.setImagenProducto(datos.getString(4));
            prod.setIdCategoria(datos.getInt(5));
            lista.add(prod);
        }
        return lista;
    }
}
