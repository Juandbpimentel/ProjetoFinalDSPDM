// VerGrupo.java
package br.ufc.quixada.projetofinalperseo.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import br.ufc.quixada.projetofinalperseo.R;
import br.ufc.quixada.projetofinalperseo.databinding.FragmentVerGrupoBinding;
import br.ufc.quixada.projetofinalperseo.models.Grupo;
import br.ufc.quixada.projetofinalperseo.view_models.GrupoViewModel;

public class VerGrupo extends Fragment {
    public static final String ARG_ID_GRUPO = "idGrupo";
    public String idGrupo;
    public static final String ARG_ID_USUARIO = "idUsuario";
    public String idUsuario;
    public GrupoViewModel grupoViewModel;
    private FirebaseFirestore db;

    public VerGrupo() {
    }

    public static VerGrupo newInstance(String idUsuario, String idGrupo) {
        VerGrupo fragment = new VerGrupo();
        Bundle args = new Bundle();
        args.putString(ARG_ID_USUARIO, idUsuario);
        args.putString(ARG_ID_GRUPO, idGrupo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idGrupo = getArguments().getString(ARG_ID_GRUPO);
            idUsuario = getArguments().getString(ARG_ID_USUARIO);
        }
        db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ver_grupo, container, false);
        grupoViewModel  = new GrupoViewModel(idGrupo);
        FragmentVerGrupoBinding binding = FragmentVerGrupoBinding.bind(view);
        binding.setGrupoViewModel(grupoViewModel);
        binding.setLifecycleOwner(this);

        loadGrupoData();

        return view;
    }

    private void loadGrupoData() {
//        DocumentReference grupoRef = db.collection("grupos").document(idGrupo);
//        grupoRef.get().addOnSuccessListener(documentSnapshot -> {
//            if (documentSnapshot.exists()) {
//                Grupo grupo = documentSnapshot.toObject(Grupo.class);
//                grupoViewModel.setGrupo(grupo);
//            }
//        });
    }
}