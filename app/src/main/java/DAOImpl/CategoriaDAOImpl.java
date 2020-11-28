package DAOImpl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import conexion.ConexionSQLite;
import dao.CategoriaDAO;
import modelo.Categoria;

public class CategoriaDAOImpl implements CategoriaDAO {

    private Context contexto;
    private ConexionSQLite objConexion;
    private SQLiteDatabase objQuery;

    public CategoriaDAOImpl(Context contexto) {
        this.contexto = contexto;
        objConexion = new ConexionSQLite(this.contexto);
        objQuery = objConexion.getWritableDatabase();
    }

    @Override
    public void registrar(Categoria categoria) {

    }

    @Override
    public void actualizar(Categoria categoria) {

    }

    @Override
    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<Categoria>();
        Categoria cat = null;
        String consulta = "SELECT * FROM categoria";
        Cursor datos = objQuery.rawQuery(consulta, null);
        while (datos.moveToNext()) {
            cat = new Categoria();
            cat.setIdCategoria(datos.getInt(0));
            cat.setCategoria(datos.getString(1));
            cat.setImagenProducto(datos.getString(2));
            lista.add(cat);
        }
        return lista;
    }

    @Override
    public void eliminar(int id) {

    }

    @Override
    public List<Categoria> listarPorId(int id) {
        return null;
    }
}
