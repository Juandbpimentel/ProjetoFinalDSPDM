package br.ufc.quixada.projetofinalperseo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import br.ufc.quixada.projetofinalperseo.R;
import br.ufc.quixada.projetofinalperseo.models.Atividade;
import br.ufc.quixada.projetofinalperseo.view_models.AtividadeViewModel;
import br.ufc.quixada.projetofinalperseo.databinding.CardAtividadeBinding;

// AtividadeAdapter.java
public class AtividadeAdapter extends RecyclerView.Adapter<AtividadeAdapter.AtividadeViewHolder> {
    private List<Atividade> atividades;
    private String idGrupo;

    public AtividadeAdapter(List<Atividade> atividades, String idGrupo) {
        this.atividades = atividades;
        this.idGrupo = idGrupo;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    @NonNull
    @Override
    public AtividadeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_atividade, parent, false);
        return new AtividadeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AtividadeViewHolder holder, int position) {
        Atividade atividade = atividades.get(position);
        holder.nome.setText(atividade.getNome());
        holder.descricao.setText(atividade.getDescricao());
        holder.dataAgenda.setText(atividade.getDataAgenda().toDate().toString());
        holder.idAtividade = atividade.getId();
        holder.idGrupo = idGrupo;
    }

    @Override
    public int getItemCount() {
        return atividades.size();
    }

    static class AtividadeViewHolder extends RecyclerView.ViewHolder {
        TextView nome, descricao, dataAgenda;
        public String idAtividade;
        public String idGrupo;

        public AtividadeViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.card_atividade_nome);
            descricao = itemView.findViewById(R.id.card_atividade_descricao);
            dataAgenda = itemView.findViewById(R.id.card_atividade_data);
        }
    }
}