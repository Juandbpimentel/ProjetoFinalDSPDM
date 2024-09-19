package br.ufc.quixada.projetofinalperseo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarUsuario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarUsuario extends Fragment {
    public static final String ARG_ID_USUARIO = "idUsuario";
    public String idUsuario;

    public EditarUsuario() {
        // Required empty public constructor
    }

    public EditarUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    // TODO: Rename and change types and number of parameters
    public static EditarUsuario newInstance(String idUsuario) {
        EditarUsuario fragment = new EditarUsuario();
        if (idUsuario != null) {
            fragment.idUsuario = idUsuario;
        }
        Bundle args = new Bundle();
        args.putString(ARG_ID_USUARIO, idUsuario);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idUsuario = getArguments().getString(ARG_ID_USUARIO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity mainActivity = (MainActivity) requireActivity();
        View view = inflater.inflate(R.layout.fragment_editar_usuario, container, false);
        Button btnSalvar = view.findViewById(R.id.editar_usuario_botao_salvar);

        btnSalvar.setOnClickListener((v) -> {
            Fragment fragment = PerfilUsuario.newInstance(idUsuario);
            mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).commit();
        });

        return view;
    }
}