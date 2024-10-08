package br.ufc.quixada.projetofinalperseo.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import br.ufc.quixada.projetofinalperseo.R;
import br.ufc.quixada.projetofinalperseo.adapter.GrupoAdapter;
import br.ufc.quixada.projetofinalperseo.models.Grupo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerGrupos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerGrupos extends Fragment {
    public static final String ARG_ID_GRUPO = "idGrupo";
    public String idGrupo;
    public static final String ARG_ID_USUARIO = "idUsuario";
    public String idUsuario;
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
        View view = inflater.inflate(R.layout.fragment_ver_grupos, container, false);

        CollectionReference docRef = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("grupos");

        RecyclerView recyclerView = view.findViewById(R.id.ver_grupos_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Grupo> grupos = task.getResult().toObjects(Grupo.class);
                GrupoAdapter adapter = new GrupoAdapter(grupos, null);
                recyclerView.setAdapter(adapter);
            } else {
                Log.e(TAG, "Error getting documents: ", task.getException());
            }
        });

        // Adicionar OnClickListener ao botão
        Button criarGrupoButton = view.findViewById(R.id.ver_grupos_criar_grupo_botao);
        criarGrupoButton.setOnClickListener(v -> {
            Fragment fragment = CriarGrupo.newInstance();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame_layout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }
}
