// VerGrupo.java
package br.ufc.quixada.projetofinalperseo.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
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
    private RecyclerView recyclerViewAtividades;
    private AtividadeAdapter atividadeAdapter;
    private List<Atividade> atividades;

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
        FragmentVerGrupoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ver_grupo, container, false);
        View view = binding.getRoot();

        // Inicialize o RecyclerView
        recyclerViewAtividades = view.findViewById(R.id.recycler_view_atividadese);
        recyclerViewAtividades.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicialize a lista de atividades e o adapter
        atividades = new ArrayList<>();
        atividadeAdapter = new AtividadeAdapter(atividades, idGrupo);
        recyclerViewAtividades.setAdapter(atividadeAdapter);

        // Carregue as atividades do grupo
        carregarAtividades();

        return view;
    }

    private void carregarAtividades() {
        // Lógica para carregar as atividades do grupo e atualizar o adapter
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        db.collection("grupos").document(idGrupo).get().addOnSuccessListener(snapshot -> {
            Grupo grupo = snapshot.toObject(Grupo.class);
            if (grupo != null) {
                grupo.getAtividades().forEach(docRef -> {
                    docRef.get().addOnSuccessListener(atividadeSnapshot -> {
                        Atividade atividade = atividadeSnapshot.toObject(Atividade.class);
                        if (atividade != null) {
                            atividades.add(atividade);
                            atividadeAdapter.notifyDataSetChanged();
                        }
                    });
                });
            }
        });
    }


}