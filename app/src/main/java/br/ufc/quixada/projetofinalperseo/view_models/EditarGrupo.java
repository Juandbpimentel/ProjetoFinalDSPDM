package br.ufc.quixada.projetofinalperseo.view_models;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufc.quixada.projetofinalperseo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarGrupo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarGrupo extends Fragment {

    public EditarGrupo() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static EditarGrupo newInstance() {
        EditarGrupo fragment = new EditarGrupo();
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
        return inflater.inflate(R.layout.fragment_editar_grupo, container, false);
    }
}