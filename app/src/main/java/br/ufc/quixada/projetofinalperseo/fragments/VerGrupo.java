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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import br.ufc.quixada.projetofinalperseo.R;
import br.ufc.quixada.projetofinalperseo.adapter.AtividadeAdapter;
import br.ufc.quixada.projetofinalperseo.databinding.FragmentVerGrupoBinding;
import br.ufc.quixada.projetofinalperseo.models.Atividade;
import br.ufc.quixada.projetofinalperseo.models.Grupo;
import br.ufc.quixada.projetofinalperseo.view_models.GrupoViewModel;

public class VerGrupo extends Fragment {
    public static final String ARG_ID_GRUPO = "idGrupo";
    public String idGrupo;
    public static final String ARG_ID_USUARIO = "idUsuario";
    public String idUsuario;
    public GrupoViewModel grupoViewModel;
    private FirebaseFirestore db;
    private FragmentVerGrupoBinding binding;

    public VerGrupo(){

    }

    public static VerGrupo newInstance(String idUsuario, String idGrupo) {
        VerGrupo fragment = new VerGrupo();
        Bundle args = new Bundle();
        args.putString(ARG_ID_USUARIO, idUsuario);
        args.putString(ARG_ID_GRUPO, idGrupo);
        fragment.setArguments(args);
        Log.d("Projeto Mobile - Ver Grupo", "Criando fragmento VerGrupo com idUsuario: " + idUsuario + " e idGrupo: " + idGrupo);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idGrupo = getArguments().getString(ARG_ID_GRUPO);
            idUsuario = getArguments().getString(ARG_ID_USUARIO);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        grupoViewModel = new GrupoViewModel();
        DocumentReference docRef = db.collection("grupos").document(idGrupo);
        docRef.get().addOnSuccessListener(snapshot -> {
            if (snapshot == null || !snapshot.exists()) {
                Log.d("Projeto Mobile - Carregando Grupo View Model", "Não existe grupo com id: " + idGrupo);
                return;
            }
            Grupo grupo = snapshot.toObject(Grupo.class);
            if (grupo == null) {
                Log.d("Projeto Mobile - Carregando Grupo View Model", "Grupo nulo");
                return;
            }
            grupoViewModel.setGrupo(grupo);
            Log.d("Projeto Mobile - Carregando Grupo View Model", "Carregado grupo com id: " + idGrupo + " - " + grupo);
        }).addOnFailureListener(e -> {
            Log.d("Projeto Mobile - Carregando Grupo View Model", "Erro ao carregar grupo com id: " + idGrupo + " - " + e.getLocalizedMessage());
        });
        View view = inflater.inflate(R.layout.fragment_ver_grupo, container, false);
        binding = FragmentVerGrupoBinding.bind(view);
        binding.setGrupoViewModel(grupoViewModel);
        binding.setLifecycleOwner(this);

         setupRecyclerView(view, container);

        return view;
    }

    private void setupRecyclerView(View view, ViewGroup container) {
        RecyclerView recyclerView = view.findViewById(R.id.teste_teste);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Atividade> atividades = grupoViewModel.getAtividades(); // Supondo que este método existe
        AtividadeAdapter adapter = new AtividadeAdapter(atividades, idGrupo); // Pass idGrupo here
        recyclerView.setAdapter(adapter);
    }
}