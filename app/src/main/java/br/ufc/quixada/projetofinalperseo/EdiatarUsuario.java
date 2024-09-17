package br.ufc.quixada.projetofinalperseo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EdiatarUsuario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EdiatarUsuario extends Fragment {


    public EdiatarUsuario() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EdiatarUsuario newInstance() {
        EdiatarUsuario fragment = new EdiatarUsuario();
        Bundle args = new Bundle();
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
        return inflater.inflate(R.layout.fragment_ediatar_usuario, container, false);
    }
}