package es.fjmarlop.encuestas.fragmentos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import es.fjmarlop.encuestas.R;
import es.fjmarlop.encuestas.entidades.Encuesta;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ENCUESTA = "encuesta";

    public FinalFragment() {
        // Required empty public constructor
    }

    public static FinalFragment newInstance(Encuesta encuesta) {
        FinalFragment fragment = new FinalFragment();
        Bundle args = new Bundle();
        args.putSerializable(ENCUESTA, (Serializable) encuesta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_final, container, false);

        Button finalizar = view.findViewById(R.id.btnFinalizar);

        if (getArguments() != null) {
            Encuesta encuesta = (Encuesta) getArguments().getSerializable(ENCUESTA);

            finalizar.setOnClickListener(v -> {

                try {
                    JSONObject object = new JSONObject();
                    object.put("Edad", encuesta.getEdad());
                    object.put("Género", encuesta.getGenero());
                    object.put("provincia", encuesta.getProvincia());

                    for (int i = 0; i < encuesta.getMapa().size(); i++) {
                        String respuesta = encuesta.getMapa().get("respuesta" + (i + 1));
                        object.put("respuesta" + (i + 1), respuesta);
                    }
                    String jsonString = object.toString();

                    Log.d("JSON", jsonString);

                    performNetworkRequest(jsonString);
                    Handler handler = new Handler();
                    handler.postDelayed(() -> getActivity().finish(), 2000);


                } catch (JSONException | ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

        }
        return view;
    }

    public void performNetworkRequest(String jsonString) throws ExecutionException, InterruptedException {

        Executor executor = Executors.newSingleThreadExecutor();
        Future<?> future = ((ExecutorService) executor).submit(() -> {
            try {
                String url = "https://www.fjmarlop.es/encuestas/encuestas.json";

                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                writer.write(jsonString);
                writer.flush();
                writer.close();

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // La solicitud se realizó con éxito
                    Log.i("JSON","Los datos se han guardado correctamente");

                } else {
                    // Hubo un error en la solicitud
                    Log.i("JSON","Error en la solicitud HTTP: " + responseCode);
                }
                connection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}

