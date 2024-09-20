package br.ufc.quixada.projetofinalperseo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import br.ufc.quixada.projetofinalperseo.R;
import br.ufc.quixada.projetofinalperseo.databinding.FragmentVerGrupoBinding;

public class VerGrupo extends Fragment {
    public static final String ARG_ID_GRUPO = "idGrupo";
    public String idGrupo;
    public static final String ARG_ID_USUARIO = "idUsuario";
    public String idUsuario;

    private Button verGrupoButton;
    private FirebaseFirestore db;

    public VerGrupo() {
        // Required empty public constructor
    }

    public static VerGrupo newInstance() {
        VerGrupo fragment = new VerGrupo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static VerGrupo newInstance(String idUsuario, String idGrupo) {
        VerGrupo fragment = new VerGrupo();
        if (idUsuario != null) {
            fragment.idUsuario = idUsuario;
        }
        if (idGrupo != null) {
            fragment.idGrupo = idGrupo;
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentVerGrupoBinding binding = FragmentVerGrupoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Button botaoGrupo = view.findViewById(R.id.card_grupo_botao_ver_grupo);

//        botaoGrupo.setOnClickListener(v -> {
//            DocumentReference grupoRef = db.collection("grupos").document(idGrupo);
//            grupoRef.get().addOnSuccessListener(documentSnapshot -> {
//                if (documentSnapshot.exists()) {
//                    String grupoId = documentSnapshot.getId();
//                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, VerGrupo.newInstance(idUsuario, grupoId)).commit();
//                }
//            });
//        });

        return view;
    }
}