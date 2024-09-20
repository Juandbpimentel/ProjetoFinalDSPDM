package br.ufc.quixada.projetofinalperseo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.SupportMapFragment;

import br.ufc.quixada.projetofinalperseo.MainActivity;
import br.ufc.quixada.projetofinalperseo.R;
import br.ufc.quixada.projetofinalperseo.databinding.FragmentEditarUsuarioBinding;
import br.ufc.quixada.projetofinalperseo.databinding.FragmentPerfilUsuarioBinding;
import br.ufc.quixada.projetofinalperseo.models.Usuario;
import br.ufc.quixada.projetofinalperseo.view_models.UsuarioViewModel;

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
//        // Inflate the layout for this fragment
//        MainActivity mainActivity = (MainActivity) requireActivity();
       View view = inflater.inflate(R.layout.fragment_editar_usuario, container, false);
//
//
//        UsuarioViewModel usuarioViewModel = mainActivity.usuarioViewModel;
//        FragmentEditarUsuarioBinding binding = FragmentEditarUsuarioBinding.bind(view);
//        binding.setEditarUsuarioViewModel(usuarioViewModel);
//        Usuario usuario = usuarioViewModel.getUsuario();
//
//        EditText senha = view.findViewById(R.id.editar_usuario_campo_senha);
//        EditText nome = view.findViewById(R.id.editar_usuario_campo_nome);
//
//        Button btnSalvar = view.findViewById(R.id.editar_usuario_botao_salvar);
//        btnSalvar.setOnClickListener((v) -> {
//            Log.d("EditarUsuario", "Email: " + usuarioViewModel.getUsuario().getEmail());
//            String senhaString = senha.getText().toString();
//            String nomeString = nome.getText().toString();
//            usuarioViewModel.setNome(nomeString);
//            usuarioViewModel.atualizarUsuario(idUsuario, senhaString, mainActivity);
//            Fragment fragment = PerfilUsuario.newInstance(idUsuario);
//            mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).commit();
//        });

        return view;
    }
}