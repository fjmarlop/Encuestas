package es.fjmarlop.encuestas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;

import es.fjmarlop.encuestas.menuActivitys.AcercaActivity;
import es.fjmarlop.encuestas.menuActivitys.AyudaActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        // cambiar el icono del menú (3 puntos por defecto)
        toolbar.setOverflowIcon(AppCompatResources.getDrawable(this, R.drawable.ic_action_name));
        setSupportActionBar(toolbar);
        Button btnComenzar = findViewById(R.id.btnComenzar);
        btnComenzar.setOnClickListener(v -> {
           Intent principal = new Intent(this, PrincipalActivity.class);
           startActivity(principal);
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection

        if (item.getItemId() == R.id.ayuda ){
            Intent ayuda = new Intent(this, AyudaActivity.class);
            startActivity(ayuda);
            return true;
        } else if (item.getItemId() == R.id.acerca) {
            Intent acerca = new Intent(this, AcercaActivity.class);
            startActivity(acerca);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}

