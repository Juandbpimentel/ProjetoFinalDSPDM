package br.ufc.quixada.projetofinalperseo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import br.ufc.quixada.projetofinalperseo.R;
import br.ufc.quixada.projetofinalperseo.models.Grupo;
import br.ufc.quixada.projetofinalperseo.models.Usuario;

public class CriarGrupo extends Fragment {

    private EditText campoNome;
    private EditText campoDescricao;
    private EditText campoEsporte;
    private Button criarGrupoButton;

    public CriarGrupo() {
        // Required empty public constructor
    }

    public static CriarGrupo newInstance() {
        return new CriarGrupo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_criar_grupo, container, false);

        campoNome = view.findViewById(R.id.criar_grupo_campo_nome);
        campoDescricao = view.findViewById(R.id.criar_grupo_campo_descricao);
        campoEsporte = view.findViewById(R.id.criar_grupo_campo_esporte);
        criarGrupoButton = view.findViewById(R.id.criar_grupo_button);

        criarGrupoButton.setOnClickListener(v -> criarGrupo());

        return view;
    }

    private void criarGrupo() {
        String nome = campoNome.getText().toString();
        String descricao = campoDescricao.getText().toString();
        String esporte = campoEsporte.getText().toString();

        if (nome.isEmpty() || descricao.isEmpty() || esporte.isEmpty()) {
            Toast.makeText(getContext(), "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        Usuario administrador = getAdministradorAtual(); // Obtenha o usuário administrador atual
        Grupo novoGrupo = new Grupo(nome, descricao, administrador, List.of(administrador), List.of(), esporte);

        db.collection("grupos").add(novoGrupo)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getContext(), "Grupo criado com sucesso", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().popBackStack(); // Voltar à tela anterior
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Erro ao criar grupo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private Usuario getAdministradorAtual() {
        // Implementar a lógica para obter o usuário administrador atual
        return new Usuario(); // Substituir por lógica real
    }
}