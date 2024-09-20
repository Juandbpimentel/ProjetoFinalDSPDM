package br.ufc.quixada.projetofinalperseo.view_models;

import android.content.Context;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import br.ufc.quixada.projetofinalperseo.models.Atividade;
import br.ufc.quixada.projetofinalperseo.models.Grupo;
import br.ufc.quixada.projetofinalperseo.models.Usuario;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GrupoViewModel extends BaseObservable {
    private Grupo grupo;

    public GrupoViewModel(String id) {
        Log.d("Doideira mano Projeto Mobile - Grupo View Model", "Carregando grupo com id: " + id);
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        DocumentReference docRef = db.collection("grupos").document(id);
        Log.d("Doideira mano Pgggggggrojeto Mobile 2", "Sucesso ao carregar grupo com id: " + id);
        docRef.get().addOnSuccessListener(snapshot -> {
            Log.d("Doideira mano Projeto Mobile 2", "Sucesso ao carregar grupo com id: " + id);
            if (snapshot == null || !snapshot.exists()) {
                Log.d("Projeto Mobile - Carregando Grupo View Model", "Não existe grupo com id: " + id);
                return;
            }
            Grupo grupo = snapshot.toObject(Grupo.class);
            if (grupo == null) {
                Log.d("Projeto Mobile - Carregando Grupo View Model", "Grupo nulo");
                return;
            }
            setGrupo(grupo);
            Log.d("Projeto Mobile - Carregando Grupo View Model", "Carregado grupo com id: " + id + " - " + grupo);
            notifyPropertyChanged(BR.nome);
        }).addOnFailureListener(e -> {
            Log.d("Doideira mano Projeto Mobile 3", "Sucesso ao carregar grupo com id: " + id);
            Log.d("Projeto Mobile - Carregando Grupo View Model", "Erro ao carregar grupo com id: " + id + " - " + e.getLocalizedMessage());
        });
        Log.d("Doideira mano Pgggggggrojeto Mobile 2", "Sucesso ao carregar grupo com id: " + id);
    }

    public GrupoViewModel() { this.grupo = new Grupo(); }

    public GrupoViewModel(Grupo grupo) { this.grupo = grupo; }

    public void getAtividades(FirebaseCallback<List<Atividade>> callback) {
        CollectionReference atividadesRef = FirebaseFirestore.getInstance().collection("atividades");
        List<Atividade> atividades = new ArrayList<>();

        if (grupo.getAtividades() == null) {
            callback.onCallback(List.of());
            return;
        }

        for (DocumentReference docRef : grupo.getAtividades()) {
            atividadesRef.document(docRef.getId()).get().addOnSuccessListener(snapshot -> {
                Atividade atividade = snapshot.toObject(Atividade.class);
                if (atividade != null) {
                    atividades.add(atividade);
                }
                // Se todas as atividades forem carregadas, retorna o callback
                if (atividades.size() == grupo.getAtividades().size()) {
                    callback.onCallback(atividades);
                }
            }).addOnFailureListener(e -> {
                Log.d("GrupoViewModel", "Erro ao carregar atividade: " + e.getMessage());
            });
        }
    }

    public interface FirebaseCallback<T> {
        void onCallback(T result);
    }


    public Atividade getAtividade(String id){
        CollectionReference atividadesRef = FirebaseFirestore.getInstance().collection("atividades");
        AtomicReference<Atividade> atividade = new AtomicReference<>();
        grupo.getParticipantes().forEach(documentReference -> {
            atividadesRef.document(documentReference.getId()).get().addOnSuccessListener(documentSnapshot -> {
                Atividade atividadeTemp = documentSnapshot.toObject(Atividade.class);
                if (atividadeTemp == null) return;
                atividade.set(atividadeTemp);
            }).addOnFailureListener(e -> {
                Log.d("Projeto Mobile - Grupo View Model", "Erro ao carregar participantes do grupo com id: " + grupo.getId() + " - " + e.getLocalizedMessage());
                return;
            });
        });
        return atividade.get();
    }

    public void getParticipantes(FirebaseCallback<List<Usuario>> callback) {
        CollectionReference usuariosRef = FirebaseFirestore.getInstance().collection("usuarios");
        List<Usuario> participantes = new ArrayList<>();

        if (grupo.getParticipantes() == null) {
            callback.onCallback(List.of());
            return;
        }

        for (DocumentReference docRef : grupo.getParticipantes()) {
            usuariosRef.document(docRef.getId()).get().addOnSuccessListener(snapshot -> {
                Usuario usuario = snapshot.toObject(Usuario.class);
                if (usuario != null) {
                    participantes.add(usuario);
                }
                // Se todos os participantes forem carregados, retorna o callback
                if (participantes.size() == grupo.getParticipantes().size()) {
                    callback.onCallback(participantes);
                }
            }).addOnFailureListener(e -> {
                Log.d("GrupoViewModel", "Erro ao carregar participante: " + e.getMessage());
            });
        }
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
    public void setDescricao(String descricao){
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
        AtomicReference<Usuario> administrador = new AtomicReference<>();
        grupo.getAdministrador().get().addOnSuccessListener(snapshot -> {
            if (snapshot == null || !snapshot.exists()) {
                Log.d("Projeto Mobile - Grupo View Model", "Não existe administrador para o grupo com id: " + grupo.getId());
                return;
            }
            Usuario usuario = snapshot.toObject(Usuario.class);
            administrador.set(usuario);
        });
        if (administrador.get() == null) return "Grupo não encontrado";
        return administrador.get().getNome();
    }

    //Metodos Firebase
    public void atualizarNome(String nome){
        if (nome == null || nome.equals(grupo.getNome())) return;
        FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("grupos").document(grupo.getId()).update("nome", nome);
        grupo.setNome(nome);
    }

    public void atualizarEsporte(String esporte){
        if (esporte == null || esporte.equals(grupo.getEsporte())) return;
        FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("grupos").document(grupo.getId()).update("esporte", esporte);
        grupo.setEsporte(esporte);
    }

    public void atualizarDescricao(String descricao){
        if (descricao == null || descricao.equals(grupo.getDescricao())) return;
        FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("grupos").document(grupo.getId()).update("descricao", descricao);
        grupo.setDescricao(descricao);
    }

    public void atualizarGrupo(Grupo grupoAntigo,Context context){
        if (grupoAntigo == null) return;
        if (grupo.getId().equals(grupoAntigo.getId())) return;

        atualizarDescricao(grupo.getDescricao());
        atualizarNome(grupo.getNome());
        atualizarEsporte(grupo.getEsporte());
    }
}
