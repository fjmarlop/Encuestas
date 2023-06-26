package es.fjmarlop.encuestas.fragmentos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.fjmarlop.encuestas.R;
import es.fjmarlop.encuestas.entidades.Encuesta;
import es.fjmarlop.encuestas.entidades.Pregunta;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreguntasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class PreguntasFragment extends Fragment {

    private static final String PARAMETROS = "preguntas";
    private static final String EDAD = "edad";
    private static final String GENERO = "genero";
    private static final String PROVINCIA = "provincia";
    private int contador = 0;

    private String respuestaSeleccionada = "";
    List<String> valoresSeleccionados = new ArrayList<>(); // uso para respuestas multiples

    public PreguntasFragment() {
        // Required empty public constructor
    }

    public static PreguntasFragment newInstance(ArrayList<Pregunta> preguntas, int edad, String genero, String provincia) {
        PreguntasFragment fragment = new PreguntasFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAMETROS, preguntas);
        args.putInt(EDAD, edad);
        args.putString(GENERO, genero);
        args.putString(PROVINCIA, provincia);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preguntas, container, false);

        TextView enunciado = view.findViewById(R.id.txtEnunciado);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) RadioGroup radioContainer = view.findViewById(R.id.container);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button siguiente = view.findViewById(R.id.btnSiguiente);

        if (getArguments() != null) {
            ArrayList<Pregunta> preguntas = (ArrayList<Pregunta>) getArguments().getSerializable(PARAMETROS);

            Encuesta encuesta = new Encuesta();
            encuesta.setEdad(getArguments().getInt(EDAD));
            encuesta.setGenero(getArguments().getString(GENERO));
            encuesta.setProvincia(getArguments().getString(PROVINCIA));

            obtenerRespuestas(contador, preguntas, enunciado, radioContainer);

            HashMap<String,String> hashMap = new HashMap<>();

            siguiente.setOnClickListener(v -> {
                // mapa para guardar pregunta y respuesta


                // comprobar si la respuesta tiene una o varias respuestas posibles
                if (preguntas.get(contador).getNumeroRespuestas() == 1){ //una respuesta
                    if (respuestaSeleccionada.isEmpty() || respuestaSeleccionada.isBlank()){
                        Toast.makeText(getContext(), "Debe seleccionar una respuesta para continuar", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else { // varias respuestas
                    if (valoresSeleccionados.isEmpty()){
                        Toast.makeText(getContext(), "Debe seleccionar al menos una respuesta para continuar", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        respuestaSeleccionada = valoresSeleccionados.toString();
                    }
                }

                hashMap.put("respuesta"+(contador+1),respuestaSeleccionada);

                contador++;

                if(preguntas.size() == contador){
                    encuesta.setMapa(hashMap);
                    FinalFragment fragment = FinalFragment.newInstance(encuesta);
                    FragmentTransaction iniciar = getActivity().getSupportFragmentManager().beginTransaction();
                    iniciar.replace(R.id.contenedor, fragment);
                    iniciar.commit();
                } else {
                    obtenerRespuestas(contador, preguntas, enunciado, radioContainer);
                }

            });
        }
        return view;
    }

    private void obtenerRespuestas(int contador, ArrayList<Pregunta> preguntas, TextView enunciado, LinearLayout checkboxContainer) {

        enunciado.setText(preguntas.get(contador).getEnunciado());

        checkboxContainer.removeAllViews();
        ArrayList<String> arrayRespuestas = new ArrayList<>();

        for (int i = 0; i < preguntas.get(contador).getRespuestas().size(); i++) {
            arrayRespuestas.add(preguntas.get(contador).getRespuestas().get(i));
        }

        if (preguntas.get(contador).getNumeroRespuestas() == 1) {

            for (String valor : arrayRespuestas) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(valor);
                checkboxContainer.addView(radioButton);

                radioButton.setOnCheckedChangeListener(((buttonView, isChecked) -> {
                    respuestaSeleccionada = radioButton.getText().toString();
                }));
            }
        } else {
            for (String valor : arrayRespuestas) {
                CheckBox checkBox = new CheckBox(getContext());
                checkBox.setText(valor);
                checkboxContainer.addView(checkBox);

                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    String valorActual = checkBox.getText().toString();
                    if (isChecked) {
                        valoresSeleccionados.add(valorActual);
                    } else {
                        valoresSeleccionados.remove(valorActual);
                    }
                });
            }
        }

    }

}