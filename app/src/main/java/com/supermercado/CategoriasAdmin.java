package com.supermercado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class CategoriasAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias_admin);

        Bundle bundle = getIntent().getExtras();
        int rol = bundle.getInt("rol");
        int id = bundle.getInt("idUser");

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout_categorias);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            Bundle arg = new Bundle();
            arg.putInt("user", id);
            getSupportFragmentManager().setFragmentResult("userId", arg);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Categorias()).commit();
            navigationView.setCheckedItem(R.id.nav_categorias);
        }
        if (rol != 1) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_categorias:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Categorias()).commit();
                break;
            case R.id.nav_agregar_producto:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AgregarProducto()).commit();
                break;
            case R.id.nav_actualizar_producto:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ActualizarProducto()).commit();
                break;
            case R.id.nav_eliminar_producto:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EliminarProducto()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}