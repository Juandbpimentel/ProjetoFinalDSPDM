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

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import br.ufc.quixada.projetofinalperseo.models.Atividade;
import br.ufc.quixada.projetofinalperseo.models.Grupo;
import br.ufc.quixada.projetofinalperseo.models.Usuario;

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

    public List<Atividade> getAtividades(){
        CollectionReference atividadesRef = FirebaseFirestore.getInstance().collection("atividades");
        AtomicReference<List<Atividade>> atividades = new AtomicReference<>();
        Log.d("Projeto Mobile - Grupo View Model", "Carregando atividades do grupo com id: " + grupo.getId());
        if (grupo.getAtividades() == null) return List.of();
        grupo.getAtividades().forEach(documentReference -> {
            atividadesRef.document(documentReference.getId()).get().addOnSuccessListener(documentSnapshot -> {
                Atividade atividade = documentSnapshot.toObject(Atividade.class);
                if (atividade == null) return;
                atividades.get().add(atividade);
            }).addOnFailureListener(e -> {
                Log.d("Projeto Mobile - Grupo View Model", "Erro ao carregar participantes do grupo com id: " + grupo.getId() + " - " + e.getLocalizedMessage());
                return;
            });
        });
        return atividades.get();
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

    public List<Usuario> getParticipantes(){
        CollectionReference usuariosRef = FirebaseFirestore.getInstance().collection("usuarios");
        AtomicReference<List<Usuario>> usuarios = new AtomicReference<>();
        grupo.getParticipantes().forEach(documentReference -> {
            usuariosRef.document(documentReference.getId()).get().addOnSuccessListener(documentSnapshot -> {
                Usuario usuario = documentSnapshot.toObject(Usuario.class);
                if (usuario == null) return;
                usuarios.get().add(usuario);
            }).addOnFailureListener(e -> {
                Log.d("Projeto Mobile - Grupo View Model", "Erro ao carregar participantes do grupo com id: " + grupo.getId() + " - " + e.getLocalizedMessage());
                return;
            });
        });
        return usuarios.get();
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
        if (grupo == null || grupo.getAdministrador() == null) return "Grupo não encontrado";
        AtomicReference<DocumentSnapshot> administrador = new AtomicReference<>();
        grupo.getAdministrador().get().addOnSuccessListener(administrador::set);
        if (administrador.get() == null) return "Grupo não encontrado";
        return Objects.requireNonNull(administrador.get().toObject(Usuario.class)).getNome();
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
