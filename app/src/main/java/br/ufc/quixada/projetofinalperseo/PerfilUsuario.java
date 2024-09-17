package br.ufc.quixada.projetofinalperseo;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.ufc.quixada.projetofinalperseo.databinding.FragmentPerfilUsuarioBinding;
import br.ufc.quixada.projetofinalperseo.view_models.UsuarioViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilUsuario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilUsuario extends Fragment {
    public static final String ARG_ID_USUARIO = "idUsuario";
    public String idUsuario;

    public PerfilUsuario() {
        // Required empty public constructor
    }

    public PerfilUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public static PerfilUsuario newInstance(String idUsuario) {
        PerfilUsuario fragment = new PerfilUsuario();
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
        View view = inflater.inflate(R.layout.fragment_perfil_usuario, container, false);
        MainActivity mainActivity = (MainActivity) requireActivity();

        UsuarioViewModel usuarioViewModel = mainActivity.usuarioViewModel;
        FragmentPerfilUsuarioBinding binding = FragmentPerfilUsuarioBinding.bind(view);
        binding.setUsuarioViewModel(usuarioViewModel);
        return view;
    }


}