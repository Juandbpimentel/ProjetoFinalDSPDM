package br.ufc.quixada.projetofinalperseo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.ufc.quixada.projetofinalperseo.R;
import br.ufc.quixada.projetofinalperseo.models.Grupo;

public class GrupoAdapter extends RecyclerView.Adapter<GrupoAdapter.GrupoViewHolder> {
    private List<Grupo> grupos;

    public GrupoAdapter(List<Grupo> grupos) {
        this.grupos = grupos;
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
    }

    @Override
    public int getItemCount() {
        return grupos.size();
    }

    public static class GrupoViewHolder extends RecyclerView.ViewHolder {
        TextView nome, descricao, esporte;

        public GrupoViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.card_grupo_nome);
            descricao = itemView.findViewById(R.id.card_grupo_descricao);
            esporte = itemView.findViewById(R.id.card_grupo_esporte);
        }
    }
}