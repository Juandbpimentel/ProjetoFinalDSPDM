package br.ufc.quixada.projetofinalperseo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufc.quixada.projetofinalperseo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TelaInicial#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TelaInicial extends Fragment {



    public TelaInicial() {
        // Required empty public constructor
    }

    public static TelaInicial newInstance() {
        TelaInicial fragment = new TelaInicial();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tela_inicial, container, false);
        return inflater.inflate(R.layout.fragment_tela_inicial, container, false);
    }
}