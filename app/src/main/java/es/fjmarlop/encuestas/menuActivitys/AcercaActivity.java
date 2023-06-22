package es.fjmarlop.encuestas.menuActivitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import es.fjmarlop.encuestas.R;


public class AcercaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca);

        TextView url = findViewById(R.id.txtWeb);
        url.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(URL));
            startActivity(intent);
        });

        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            finish();
        });
    }

    private final static String URL = "https://www.fjmarlop.es";
}