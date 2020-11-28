package conexion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSQLite extends SQLiteOpenHelper {


    public ConexionSQLite(@Nullable Context context) {
        super(context, DatosConexion.NOMBREBD, null, DatosConexion.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String usuario = "CREATE TABLE usuario (idUsuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "usuario TEXT NOT NULL, password TEXT NOT NULL, estado INTEGER, idRol INTEGER," +
                "FOREIGN KEY(idRol) REFERENCES rolUsuario(idRol))";
        sqLiteDatabase.execSQL(usuario);

        String rolUsuario = "CREATE TABLE rolUsuario (idRol INTEGER PRIMARY KEY AUTOINCREMENT,tipoRol TEXT NOT NULL)";
        sqLiteDatabase.execSQL(rolUsuario);

        String categoria = "CREATE TABLE categoria (idCategoria INTEGER PRIMARY KEY AUTOINCREMENT, categoria TEXT NOT NULL," +
                "imagenProducto TEXT)";
        sqLiteDatabase.execSQL(categoria);

        String producto = "CREATE TABLE producto (idProducto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "producto TEXT NOT NULL, cantidad INTEGER NOT NULL, precio REAL NOT NULL," +
                "imagenProducto TEXT, idCategoria INTEGER," +
                "FOREIGN KEY(idCategoria) REFERENCES categoria(idCategoria))";
        sqLiteDatabase.execSQL(producto);

        String orden = "CREATE TABLE orden (idOrden INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cantidad INTEGER NOT NULL, precioTotal REAL NOT NULL, idProducto INTEGER, idUsuario INTEGER," +
                "FOREIGN KEY(idProducto) REFERENCES producto(idProducto)," +
                "FOREIGN KEY(idUsuario) REFERENCES usuario(idUsuario))";
        sqLiteDatabase.execSQL(orden);

        String queryCat = "Insert into categoria(categoria, imagenProducto)" +
                "values('Carnes', 'carnes')," +
                "('Embutidos', 'salami')," +
                "('Cereales', 'cereales')," +
                "('Bebidas', 'bebidas')," +
                "('Lacteos', 'lacteos')," +
                "('Panaderia', 'pan')," +
                "('Frutas y Verduras', 'frutasverduras')," +
                "('Limpieza', 'limpieza')";
        sqLiteDatabase.execSQL(queryCat);

        String queryRol = "Insert into rolUsuario(tipoRol)" +
                "values('Admin')," +
                "('Cliente')";
        sqLiteDatabase.execSQL(queryRol);

        String queryUsuario = "Insert into usuario(usuario, password, estado, idRol)" +
                "values('admin', '123', 1, 1)," +
                "('cliente', '123', 1, 2)";
        sqLiteDatabase.execSQL(queryUsuario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
