package es.fjmarlop.encuestas.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.service.voice.VoiceInteractionSession;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import es.fjmarlop.encuestas.MainActivity;
import es.fjmarlop.encuestas.R;
import es.fjmarlop.encuestas.entidades.Pregunta;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreguntasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class PreguntasFragment extends Fragment {

    private static final String PARAMETROS = "preguntas";

    public PreguntasFragment() {
        // Required empty public constructor
    }

    public static PreguntasFragment newInstance(ArrayList<Pregunta> preguntas) {
        PreguntasFragment fragment = new PreguntasFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAMETROS, preguntas);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preguntas, container, false);
        int n = 0;
        if (getArguments() != null) {
            ArrayList<Pregunta> preguntas = (ArrayList<Pregunta>) getArguments().getSerializable(PARAMETROS);
            n = preguntas.size();
            Toast.makeText(getContext(), "n = " + n, Toast.LENGTH_SHORT).show();
        }


        TextView enunciado = view.findViewById(R.id.txtEnunciado);
        TextView respuesta1 = view.findViewById(R.id.respuesta1);
        TextView respuesta2 = view.findViewById(R.id.respuesta2);
        TextView respuesta3 = view.findViewById(R.id.respuesta3);
        TextView respuesta4 = view.findViewById(R.id.respuesta4);


        return view;
    }


}