package br.ufc.quixada.projetofinalperseo.view_models;

import android.content.Context;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import br.ufc.quixada.projetofinalperseo.models.Atividade;
import br.ufc.quixada.projetofinalperseo.models.Grupo;
import br.ufc.quixada.projetofinalperseo.models.Usuario;

public class GrupoViewModel extends BaseObservable {
    private Grupo grupo;

    public GrupoViewModel(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        final DocumentReference docRef = db.collection("grupos").document(id);
        Task<DocumentSnapshot> task = docRef.get();
        task.addOnSuccessListener((resultado) -> {
            if (!resultado.exists()) {
                Log.d("Projeto Mobile - Carregando Grupo View Model", "Não existe grupo com id: " + id);
                return;
            }
            Grupo grupo = resultado.toObject(Grupo.class);
            if (grupo == null) {
                Log.d("Projeto Mobile - Carregando Grupo View Model", "Grupo nulo");
                return;
            }
            setGrupo(grupo);
            Log.d("Projeto Mobile - Carregando Grupo View Model", "Carregado grupo com id: " + id + " - " + grupo);
            notifyPropertyChanged(BR.nome);
            notifyPropertyChanged(BR.email);

        });
        task.addOnFailureListener((e) -> {
            Log.d("Projeto Mobile - Carregando Grupo View Model", "Erro ao carregar grupo com id: " + id + " - " + e.getLocalizedMessage());
        });
    }

    public GrupoViewModel() { this.grupo = new Grupo(); }

    public GrupoViewModel(Grupo grupo) { this.grupo = grupo; }

    public List<Atividade> getAtividades(){
        return grupo.getAtividades().stream().map(DocumentReference::get).collect(Collectors.toList()).stream().map(documentSnapshot -> documentSnapshot.getResult().toObject(Atividade.class)).collect(Collectors.toList());
    }

    public Atividade getAtividade(String id){
        return grupo.getAtividades().stream().map(DocumentReference::get).collect(Collectors.toList())
                .stream().map(documentSnapshot -> documentSnapshot.getResult().toObject(Atividade.class)).collect(Collectors.toList())
                .stream().filter(atividade -> atividade.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Usuario> getParticipantes(){
        return grupo.getParticipantes().stream().map(DocumentReference::get).collect(Collectors.toList()).stream().map(documentSnapshot -> documentSnapshot.getResult().toObject(Usuario.class)).collect(Collectors.toList());
    }

    //getters e setters
    @Bindable
    public String getNome(){
        if (grupo == null) return "Grupo não encontrado";
        return grupo.getNome();
    }

    public void setNome(String nome){
        grupo.setNome(nome);
        notifyPropertyChanged(BR.nome);
    }

    @Bindable
    public String getDescricao(){
        if (grupo == null) return "Grupo não encontrado";
        return grupo.getDescricao();
    }
    public void setDescricao(String descricao, Context context){
        grupo.setDescricao(descricao);
        notifyPropertyChanged(BR.email);
    }

    public String getId(){
        return grupo.getId();
    }

    public void setId(String id){
        grupo.setId(id);
    }

    @Bindable
    public String getEsporte(){
        if (grupo == null) return "Grupo não encontrado";
        return grupo.getEsporte();
    }

    public void setEsporte(String esporte){
        grupo.setEsporte(esporte);
        notifyPropertyChanged(BR.esporte);
    }

    public void setGrupo(Grupo grupo){
        this.grupo = grupo;
        notifyPropertyChanged(BR.nome);
        notifyPropertyChanged(BR.esporte);
        notifyPropertyChanged(BR.descricao);
        notifyPropertyChanged(BR.administrador);
    }

    public Grupo getGrupo(){
        return grupo;
    }

    @Bindable
    public String getAdministrador(){
        if (grupo == null) return "Grupo não encontrado";
        return Objects.requireNonNull(grupo.getAdministrador().get().getResult().toObject(Usuario.class)).getNome();
    }

    //Metodos Firebase
    public void atualizarNome(String nome, String id){
        if (nome == null || nome.equals(grupo.getNome())) return;
        FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("grupos").document(id).update("nome", nome);
        grupo.setNome(nome);
    }

    public void atualizarEsporte(String esporte, String id){
        if (esporte == null || esporte.equals(grupo.getEsporte())) return;
        FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("grupos").document(id).update("esporte", esporte);
        grupo.setEsporte(esporte);
    }

    public void atualizarDescricao(String descricao, String id){
        if (descricao == null || descricao.equals(grupo.getDescricao())) return;
        FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("grupos").document(id).update("descricao", descricao);
        grupo.setDescricao(descricao);
    }

    public void atualizarId(String id, String idAntigo){
        DocumentReference grupoRef = FirebaseFirestore.getInstance().collection("grupos").document(idAntigo);
        if (idAntigo.equals(id))
            return;
        grupo.setId(id);
        FirebaseFirestore.getInstance().collection("grupos").document(id).set(grupo);
        FirebaseFirestore.getInstance().collection("grupos").document(idAntigo).delete();

        for (Usuario usuario : getParticipantes()) {
            usuario.getGrupos().removeIf(grupoRef::equals);
            usuario.getGrupos().add(FirebaseFirestore.getInstance().collection("grupos").document(id));
            FirebaseFirestore.getInstance().collection("usuarios").document(usuario.getId()).update("grupos", usuario.getGrupos());
        }
        for (Atividade atividade : getAtividades()) {
            atividade.setGrupo(FirebaseFirestore.getInstance().collection("grupos").document(id));
            FirebaseFirestore.getInstance().collection("atividades").document(atividade.getId()).update("grupo", atividade.getGrupo());
        }
    }

    public void atualizarGrupo(Grupo grupoAntigo,Context context){
        if (grupoAntigo == null) return;
        if (grupo.getId().equals(grupoAntigo.getId())) return;

        atualizarDescricao(grupo.getDescricao(), grupoAntigo.getId());
        atualizarNome(grupo.getNome(), grupoAntigo.getId());
        atualizarEsporte(grupo.getEsporte(), grupoAntigo.getId());
        atualizarId(grupo.getId(), grupoAntigo.getId());
    }
}
