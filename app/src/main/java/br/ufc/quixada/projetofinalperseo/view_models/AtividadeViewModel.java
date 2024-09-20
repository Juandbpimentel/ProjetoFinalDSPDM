package br.ufc.quixada.projetofinalperseo.view_models;

import android.content.Context;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import br.ufc.quixada.projetofinalperseo.models.Atividade;
import br.ufc.quixada.projetofinalperseo.models.Grupo;
import br.ufc.quixada.projetofinalperseo.models.Localizacao;

public class AtividadeViewModel extends BaseObservable {
    private Atividade atividade;

    public AtividadeViewModel(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        final DocumentReference docRef = db.collection("atividades").document(id);
        Task<DocumentSnapshot> task = docRef.get();
        task.addOnSuccessListener((resultado) -> {
            if (!resultado.exists()) {
                Log.d("Projeto Mobile - Carregando Atividade View Model", "Não existe atividade com id: " + id);
                return;
            }
            Atividade atividade = resultado.toObject(Atividade.class);
            if (atividade == null) {
                Log.d("Projeto Mobile - Carregando Atividade View Model", "Atividade nulo");
                return;
            }
            setAtividade(atividade);
            Log.d("Projeto Mobile - Carregando Atividade View Model", "Carregado atividade com id: " + id + " - " + atividade);
            notifyPropertyChanged(BR.nome);
            notifyPropertyChanged(BR.descricao);
            notifyPropertyChanged(BR.data);
        });
        task.addOnFailureListener((e) -> {
            Log.d("Projeto Mobile - Carregando Atividade View Model", "Erro ao carregar atividade com id: " + id + " - " + e.getLocalizedMessage());
        });
    }

    public AtividadeViewModel() { this.atividade = new Atividade(); }

    public AtividadeViewModel(Atividade atividade) { this.atividade = atividade; }

    //getters e setters
    @Bindable
    public String getNome(){
        if (atividade == null) return "Atividade não encontrado";
        return atividade.getNome();
    }
    public void setNome(String nome){
        atividade.setNome(nome);
        notifyPropertyChanged(BR.nome);
    }

    @Bindable
    public String getDescricao(){
        if (atividade == null) return "Atividade não encontrado";
        return atividade.getDescricao();
    }
    public void setDescricao(String descricao){
        atividade.setDescricao(descricao);
        notifyPropertyChanged(BR.email);
    }

    public String getId(){
        return atividade.getId();
    }

    public void setId(String id){
        atividade.setId(id);
    }

    @Bindable
    public String getData(){
        if (atividade == null) return "Atividade não encontrado";
        DateFormat dateFormat = DateFormat.getDateInstance();
        return dateFormat.format(atividade.getDataAgenda().toDate());
    }

    public void setData(Timestamp data){
        atividade.setDataAgenda(data);
        notifyPropertyChanged(BR.data);
    }

    public Grupo getGrupo(){
        DocumentReference grupoRef = atividade.getGrupo();
        AtomicReference<Grupo> grupo = new AtomicReference<>();
        grupoRef.get().addOnSuccessListener(documentSnapshot -> {
            Grupo grupoResult = documentSnapshot.toObject(Grupo.class);
            grupo.set(grupoResult);
            if (grupo == null) return;
        }).addOnFailureListener(e -> {
            Log.d("Projeto Mobile - Grupo View Model", "Erro ao carregar grupo com id: " + grupoRef.getId() + " - " + e.getLocalizedMessage());
            return;
        });

        if (grupo.get() == null) return null;
        return grupo.get();
    }

    public void setGrupo(Grupo grupoNovo){
        DocumentReference grupoRef = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("grupos").document(grupoNovo.getId());
        atividade.setGrupo(grupoRef);
    }

    public Localizacao getLocalizacao(String id){
        return atividade.getLocalizacao().get().getResult().toObject(Localizacao.class);
    }

    public void setLocalizacao(Localizacao localizacao){
        DocumentReference localizacaoRef = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("localizacoes").document(atividade.getLocalizacao().getId());
        localizacaoRef.delete();
        localizacaoRef = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("localizacoes").document(localizacao.getId());
        localizacaoRef.set(localizacao);
        atividade.setLocalizacao(localizacaoRef);
    }

    public Atividade getAtividade(){
        return atividade;
    }

    public void setAtividade(Atividade atividade){
        this.atividade = atividade;
        notifyPropertyChanged(BR.nome);
        notifyPropertyChanged(BR.esporte);
        notifyPropertyChanged(BR.descricao);
        notifyPropertyChanged(BR.administrador);
    }


    //Metodos Firebase

    public void atualizarNome(String nome, String id){
        if (nome == null || nome.equals(atividade.getNome())) return;
        FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("atividades").document(id).update("nome", nome);
        atividade.setNome(nome);
    }

    public void atualizarDescricao(String descricao, String id){
        if (descricao == null || descricao.equals(atividade.getDescricao())) return;
        FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("atividades").document(id).update("descricao", descricao);
        atividade.setDescricao(descricao);
    }

    public void atualizarId(String id, String idAntigo){
        DocumentReference atividadeRef = FirebaseFirestore.getInstance().collection("atividades").document(idAntigo);
        if (idAntigo.equals(id))
            return;
        atividade.setId(id);
        FirebaseFirestore.getInstance().collection("atividades").document(id).set(atividade);
        FirebaseFirestore.getInstance().collection("atividades").document(idAntigo).delete();

        getGrupo().getAtividades().removeIf(atividadeRef::equals);
        getGrupo().getAtividades().add(FirebaseFirestore.getInstance().collection("atividades").document(id));
        FirebaseFirestore.getInstance().collection("grupos").document(getGrupo().getId()).update("atividades", getGrupo().getAtividades());

    }

    public void atualizarGrupo(Grupo grupo, String id){
        if (grupo == null) return;
        if (grupo.getId().equals(atividade.getGrupo().getId())) return;

        if (atividade.getGrupo() == null || atividade.getGrupo().get().getResult() == null) {
            grupo.getAtividades().add(FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("atividades").document(id));
            DocumentReference grupoRef = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("grupos").document(grupo.getId());
            grupoRef.update("atividades", grupo.getAtividades());
            return;
        }

        Grupo grupoAntigo = atividade.getGrupo().get().getResult().toObject(Grupo.class);
        grupoAntigo.getAtividades().removeIf(atividadeRef -> Objects.equals(atividadeRef.getId(), id));

        DocumentReference grupoNovoRef = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("grupos").document(grupo.getId());
        grupo.getAtividades().add(FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("atividades").document(id));
        grupoNovoRef.update("atividades", grupo.getAtividades());

        DocumentReference atividadeRef = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("atividades").document(id);
        atividade.setGrupo(grupoNovoRef);
        atividadeRef.update("grupo", grupoNovoRef);
    }

    public void atualizarLocalizacao(Localizacao localizacao, String id){
        if (localizacao == null) return;
        if (localizacao.getId().equals(atividade.getLocalizacao().getId())) return;

        DocumentReference localizacaoRef = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("localizacoes").document(localizacao.getId());
        atividade.setLocalizacao(localizacaoRef);
        FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("atividades").document(id).update("localizacao", localizacaoRef);
    }

    public void atualizarAtividade(Atividade atividadeAntiga){
        if (atividadeAntiga == null) return;

        atualizarNome(atividade.getNome(), atividadeAntiga.getId());
        atualizarDescricao(atividade.getDescricao(), atividadeAntiga.getId());
        atualizarGrupo(getGrupo(), atividadeAntiga.getId());
        atualizarLocalizacao(getLocalizacao(atividade.getLocalizacao().getId()), atividadeAntiga.getId());
        atualizarId(atividade.getId(), atividadeAntiga.getId());
    }

    public void deletarAtividade(){
        DocumentReference atividadeRef = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("atividades").document(atividade.getId());
        DocumentReference grupoRef = atividade.getGrupo();
        Grupo grupo = grupoRef.get().getResult().toObject(Grupo.class);
        assert grupo != null;
        grupo.getAtividades().removeIf(atividadeRef::equals);
        grupoRef.update("atividades", grupo.getAtividades());
        atividadeRef.delete();
    }

    public void criarAtividade(){
        DocumentReference atividadeRef = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("atividades").document(atividade.getId());
        DocumentReference grupoRef = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("grupos").document(getGrupo().getId());
        Grupo grupo = atividade.getGrupo().get().getResult().toObject(Grupo.class);
        atividadeRef.set(atividade);
        assert grupo != null;
        grupo.getAtividades().add(atividadeRef);
        grupoRef.update("atividades", grupo.getAtividades());
    }
}
