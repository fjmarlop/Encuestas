package es.fjmarlop.encuestas.menuActivitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import es.fjmarlop.encuestas.R;

public class AyudaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        Button btnVolver = findViewById(R.id.btnVolverAyuda);
        btnVolver.setOnClickListener(v->{
            finish();
        });
    }
}