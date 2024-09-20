package br.ufc.quixada.projetofinalperseo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.ufc.quixada.projetofinalperseo.R;
import br.ufc.quixada.projetofinalperseo.fragments.VerGrupo;
import br.ufc.quixada.projetofinalperseo.models.Grupo;

public class GrupoAdapter extends RecyclerView.Adapter<GrupoAdapter.GrupoViewHolder> {
    private List<Grupo> grupos;
    private String idUsuario;

    public GrupoAdapter(List<Grupo> grupos, String idUsuario) {
        this.grupos = grupos;
        this.idUsuario = idUsuario;
    }

    @NonNull
    @Override
    public GrupoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_grupo, parent, false);
        return new GrupoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GrupoViewHolder holder, int position) {
        Grupo grupo = grupos.get(position);
        holder.nome.setText(grupo.getNome());
        holder.descricao.setText(grupo.getDescricao());
        holder.esporte.setText(grupo.getEsporte());
        holder.idGrupo = grupo.getId();
        holder.idUsuario = idUsuario;
    }

    @Override
    public int getItemCount() {
        return grupos.size();
    }

    public static class GrupoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nome, descricao, esporte;
        public String idGrupo;
        Button botaoVerGrupo;
        public String idUsuario;

        public GrupoViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.card_grupo_nome);
            descricao = itemView.findViewById(R.id.card_grupo_descricao);
            esporte = itemView.findViewById(R.id.card_grupo_esporte);
            botaoVerGrupo = itemView.findViewById(R.id.card_grupo_botao_ver_grupo);
            botaoVerGrupo.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == botaoVerGrupo.getId()) {
                Fragment fragment = VerGrupo.newInstance(idUsuario, idGrupo);
                FragmentTransaction transaction = ((FragmentActivity) itemView.getContext()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame_layout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }
    }
}