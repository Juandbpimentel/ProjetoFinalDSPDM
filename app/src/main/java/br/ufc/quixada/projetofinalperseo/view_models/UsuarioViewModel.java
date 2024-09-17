package br.ufc.quixada.projetofinalperseo.view_models;

import android.content.Context;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.stream.Collectors;

import br.ufc.quixada.projetofinalperseo.models.Grupo;
import br.ufc.quixada.projetofinalperseo.models.Usuario;
import br.ufc.quixada.projetofinalperseo.utilities.AuthService;

public class UsuarioViewModel extends BaseObservable {
    private Usuario usuario;

    public UsuarioViewModel(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("usuarios").document(id);
        docRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Projeto Mobile - Carregando View Model", "Listen failed.", error);
                return;
            }
            String source = value != null && value.getMetadata().hasPendingWrites()
                    ? "Local" : "Server";
            if ( value == null || !value.exists()) {
                Log.d("Projeto Mobile - Carregando View Model", source + " data: null");
                return;
            }
            Usuario usuarioDTO = value.toObject(Usuario.class);
            if (usuarioDTO == null) {
                Log.d("Projeto Mobile - Carregando View Model", source + " data: null");
                return;
            }
            setUsuario(usuario);
            notifyPropertyChanged(BR.nome);
            notifyPropertyChanged(BR.email);
            Log.d("Projeto Mobile - Carregando View Model", source + " data: " + value.getData());
        });
    }

    public UsuarioViewModel() { this.usuario = new Usuario(); }

    public UsuarioViewModel(Usuario usuario) { this.usuario = usuario; }

    public List<Grupo> getGrupos(){
        return usuario.getGrupos().stream().map(DocumentReference::get).collect(Collectors.toList()).stream().map(documentSnapshot -> documentSnapshot.getResult().toObject(Grupo.class)).collect(Collectors.toList());
    }

    public Grupo getGrupo(String id){
        return usuario.getGrupos().stream().map(DocumentReference::get).collect(Collectors.toList())
                                        .stream().map(documentSnapshot -> documentSnapshot.getResult().toObject(Grupo.class)).collect(Collectors.toList())
                                                .stream().filter(grupo -> grupo.getId().equals(id)).findFirst().orElse(null);
    }

    //getters e setters
    @Bindable
    public String getNome(){
        return usuario.getNome();
    }
    public void setNome(String nome){
        usuario.setNome(nome);
        notifyPropertyChanged(BR.nome);
    }

    public void setSenha(String senha, Context context){
        atualizarSenha(senha,context);
    }

    @Bindable
    public String getEmail(){
        return usuario.getEmail();
    }
    public void setEmail(String email, Context context){
        usuario.setEmail(email);
        atualizarEmail(email, context);
        notifyPropertyChanged(BR.email);
    }

    public String getId(){
        return usuario.getId();
    }
    public void setId(String id){
        usuario.setId(id);
    }

    public Usuario getUsuario(){
        return usuario;
    }
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
        notifyPropertyChanged(BR.nome);
        notifyPropertyChanged(BR.email);
    }

    //Metodos Firebase
    public void criarUsuario(String nome , String email, String senha){
        try{
            Usuario novoUsuario = new Usuario(nome, email, senha, List.of());
            FirebaseFirestore.getInstance().collection("usuarios").document(novoUsuario.getId()).set(novoUsuario);
            setUsuario(novoUsuario);
        }catch (Exception e){
            e.printStackTrace();
            setUsuario(new Usuario());
            return;
        }
    }

    public void atualizarNome(String nome){
        FirebaseFirestore.getInstance().collection("usuarios").document(usuario.getId()).update("nome", nome);
        usuario.setNome(nome);
    }

    public void atualizarEmail(String email, Context context){
        AuthService.alterarEmail(email, context);
        FirebaseFirestore.getInstance().collection("usuarios").document(usuario.getId()).update("email", email);
        usuario.setEmail(email);
    }

    public void atualizarId(String id){
        FirebaseFirestore.getInstance().collection("usuarios").document(usuario.getId()).update("id", id);
        usuario.setId(id);
    }

    public void atualizarSenha(String senha, Context context){
        AuthService.alterarSenha(senha, context);
    }

    public void entrarEmGrupo(Grupo grupo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference grupoRef = db.collection("grupos").document(grupo.getId());
        DocumentReference usuarioRef = db.collection("usuarios").document(usuario.getId());
        usuario.getGrupos().add(grupoRef);
        grupo.getParticipantes().add(usuarioRef);

        db.collection("usuarios").document(usuario.getId()).update("grupos", usuario.getGrupos());
        db.collection("grupos").document(grupo.getId()).update("usuarios", grupo.getParticipantes());
    }

    public void sairDeGrupo(Grupo grupo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference grupoRef = db.collection("grupos").document(grupo.getId());
        DocumentReference usuarioRef = db.collection("usuarios").document(usuario.getId());
        usuario.getGrupos().removeIf(grupoRef::equals);
        grupo.getParticipantes().removeIf(usuarioRef::equals);

        db.collection("usuarios").document(usuario.getId()).update("grupos", usuario.getGrupos());
        db.collection("grupos").document(grupo.getId()).update("usuarios", grupo.getParticipantes());
    }

    public void setUsuarioPorAuth(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios").whereEqualTo("email", AuthService.usuario.getEmail()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                if(!task.getResult().getDocuments().isEmpty()){
                    setUsuario(task.getResult().getDocuments().get(0).toObject(Usuario.class));
                }else{
                    setUsuario(new Usuario());
                }
            }else{
                setUsuario(new Usuario());
            }
        });
    }
}
