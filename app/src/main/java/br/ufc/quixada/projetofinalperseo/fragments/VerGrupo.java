package br.ufc.quixada.projetofinalperseo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufc.quixada.projetofinalperseo.R;
import br.ufc.quixada.projetofinalperseo.databinding.FragmentVerGrupoBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerGrupo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerGrupo extends Fragment {

    public VerGrupo() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static VerGrupo newInstance() {
        VerGrupo fragment = new VerGrupo();
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
        FragmentVerGrupoBinding binding = FragmentVerGrupoBinding.inflate(inflater, container, false);
        return inflater.inflate(R.layout.fragment_ver_grupo, container, false);
    }
}