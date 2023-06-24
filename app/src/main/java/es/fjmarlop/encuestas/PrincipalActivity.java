package es.fjmarlop.encuestas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.fjmarlop.encuestas.entidades.Pregunta;
import es.fjmarlop.encuestas.fragmentos.PreguntasFragment;
import es.fjmarlop.encuestas.menuActivitys.AcercaActivity;
import es.fjmarlop.encuestas.menuActivitys.AyudaActivity;

public class PrincipalActivity extends AppCompatActivity {

    private final ArrayList<Pregunta> todasLasPreguntas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbarPrincipalActivity);
        // cambiar el icono del menú (3 puntos por defecto)
        toolbar.setOverflowIcon(AppCompatResources.getDrawable(this, R.drawable.ic_action_name));
        setSupportActionBar(toolbar);

        TextView txtEdad = findViewById(R.id.lblEdad);
        TextView txtGenero = findViewById(R.id.lblGenero);
        TextView txtProvincia = findViewById(R.id.lblProvincia);
        EditText edad = findViewById(R.id.numEdad);
        Spinner genero = findViewById(R.id.spGenero);
        Spinner provincia = findViewById(R.id.spinnerProvincias);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://www.fjmarlop.es/encuestas/preguntas.json";

        FrameLayout contenedor = findViewById(R.id.contenedor);

        requestQueue.add(cargarDatos(url));

        Button btnComenzar = findViewById(R.id.btnComenzarPrincipal);

        btnComenzar.setOnClickListener(v -> {
            txtEdad.setTextColor(Color.BLACK);
            txtGenero.setTextColor(Color.BLACK);
            txtProvincia.setTextColor(Color.BLACK);

            // Comprobar edad
            if (edad.getText().toString().isEmpty()){
                Toast.makeText(this, "Tienes que rellenar la edad para continuar", Toast.LENGTH_SHORT).show();
                txtEdad.setTextColor(Color.RED);
                return;
            }
            int anios = Integer.parseInt(edad.getText().toString());
            if (anios < 18 ){
                Toast.makeText(this, "Tienes que ser mayor de edad para continuar", Toast.LENGTH_SHORT).show();
                return;
            }
            // Comprobar género
            if(genero.getSelectedItem().toString().equals("Seleccionar")){
                Toast.makeText(this, "Tienes que selecionar una opción para continuar", Toast.LENGTH_SHORT).show();
                txtGenero.setTextColor(Color.RED);
                return;
            }
            // Comprobar provincia
            if(provincia.getSelectedItem().toString().equals("Seleccionar")){
                Toast.makeText(this, "Tienes que selecionar una opción para continuar", Toast.LENGTH_SHORT).show();
                txtProvincia.setTextColor(Color.RED);
                return;
            }

            contenedor.setVisibility(View.VISIBLE);

            PreguntasFragment fragment = PreguntasFragment.newInstance(todasLasPreguntas);
            FragmentTransaction iniciar = getSupportFragmentManager().beginTransaction();
            iniciar.replace(R.id.contenedor,fragment);
            iniciar.commit();
            }
        );

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

        if (item.getItemId() == R.id.ayuda) {
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

    public JsonArrayRequest cargarDatos(String url) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {

            for (int i = 0; i < response.length(); i++) {

                try {
                    JSONObject object = new JSONObject(response.get(i).toString());

                    Pregunta pregunta = new Pregunta();
                    pregunta.setIdPregunta(i + 1);
                    pregunta.setEnunciado(object.getString("enunciado"));

                    JSONArray respuestas = object.getJSONArray("respuestas");
                    ArrayList<String> r = new ArrayList<>();

                    for (int j = 0; j < respuestas.length(); j++) {
                        r.add(respuestas.getString(j));
                    }
                    pregunta.setRespuestas(r);
                    pregunta.setNumeroRespuestas(object.getInt("numeroRespuestas"));

                    todasLasPreguntas.add(pregunta);

                } catch (JSONException e) {
                    Toast.makeText(PrincipalActivity.this, "Error al cargar los datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> {
            Toast.makeText(PrincipalActivity.this, "Error al cargar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        });

        return request;
    }
}