package DAOImpl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import conexion.ConexionSQLite;
import dao.UsuarioDAO;
import modelo.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

    private Context contexto;
    private ConexionSQLite objConexion;
    private SQLiteDatabase objQuery;

    public UsuarioDAOImpl(Context contexto) {
        this.contexto = contexto;
        objConexion = new ConexionSQLite(this.contexto);
        objQuery = objConexion.getWritableDatabase();
    }

    @Override
    public void registrar(Usuario usuario) {
    }

    @Override
    public void actualizar(Usuario usuario) {

    }

    @Override
    public List<Usuario> listar() {
        return null;
    }

    @Override
    public void eliminar(int id) {

    }

    @Override
    public Usuario login(Usuario us) {
        Usuario u = null;
        String consulta = "SELECT * FROM usuario " +
                "WHERE usuario = '" + us.getUsuario() + "' AND password = '" + us.getPassword() + "'";
        Cursor datos = objQuery.rawQuery(consulta, null);
        while (datos.moveToNext()){
            u = new Usuario();
            u.setIdUsuario(datos.getInt(0));
            u.setUsuario(datos.getString(1));
            u.setPassword(datos.getString(2));
            u.setEstado(datos.getInt(3));
            u.setIdRol(datos.getInt(4));
        }
        return u;
    }

    @Override
    public List<Usuario> listarPorId(int id) {
        return null;
    }
}
