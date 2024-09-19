package br.ufc.quixada.projetofinalperseo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.ufc.quixada.projetofinalperseo.databinding.FragmentPerfilUsuarioBinding;
import br.ufc.quixada.projetofinalperseo.utilities.AuthService;
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
        Button botaoEditarPerfil = view.findViewById(R.id.perfil_usuario_botao_editar_dados);
        Button botaoFazerLogout = view.findViewById(R.id.perfil_usuario_botao_fazer_logout);
        RecyclerView recyclerView = view.findViewById(R.id.perfil_usuario_recycler_view_grupos);
        MainActivity mainActivity = (MainActivity) requireActivity();

        UsuarioViewModel usuarioViewModel = mainActivity.usuarioViewModel;
        FragmentPerfilUsuarioBinding binding = FragmentPerfilUsuarioBinding.bind(view);
        binding.setUsuarioViewModel(usuarioViewModel);
        binding.setLifecycleOwner(this);
        botaoEditarPerfil.setOnClickListener((v) -> {
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
            Fragment fragment = EditarUsuario.newInstance(idUsuario);
            mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).commit();
        });
        botaoFazerLogout.setOnClickListener((v) -> {
            AuthService.fazerLogout();
            BottomNavigationView bottomNavigationView = mainActivity.findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setSelected(false);
            bottomNavigationView.setVisibility(BottomNavigationView.GONE);
            Fragment fragment = TelaLogin.newInstance();
            mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).commit();
        });
        return view;
    }


}