package com.supermercado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import DAOImpl.UsuarioDAOImpl;
import dao.UsuarioDAO;
import modelo.Usuario;

public class MainActivity extends AppCompatActivity {

    private Button btnIngresar;
    private EditText edtUser, edtPass;

    private UsuarioDAO dao;
    private Usuario u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Sepermercado UTEC");

        dao = new UsuarioDAOImpl(this);
        u = new Usuario();

        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnIngresar = findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(edtUser.getText().toString().isEmpty() ||
                        edtPass.getText().toString().isEmpty())) {
                    u.setUsuario(edtUser.getText().toString());
                    u.setPassword(edtPass.getText().toString());
                    Usuario usuario = dao.login(u);
                    if (usuario != null) {
                        Intent intent = new Intent(MainActivity.this, CategoriasAdmin.class);
                        intent.putExtra("rol", usuario.getIdRol());
                        intent.putExtra("idUser", usuario.getIdUsuario());
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "usuario o contraseña son incorrectos", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "llene todos los campos", Toast.LENGTH_LONG).show();
                }
                limpiarCampos();
            }
        });

    }

    public void limpiarCampos() {
        edtUser.setText(null);
        edtPass.setText(null);
    }
}