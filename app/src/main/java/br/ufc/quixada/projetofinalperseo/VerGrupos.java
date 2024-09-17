package br.ufc.quixada.projetofinalperseo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerGrupos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerGrupos extends Fragment {

    public VerGrupos() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static VerGrupos newInstance() {
        VerGrupos fragment = new VerGrupos();
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
        return inflater.inflate(R.layout.fragment_ver_grupos, container, false);
    }
}