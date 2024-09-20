package br.ufc.quixada.projetofinalperseo.view_models;

import static com.google.android.gms.tasks.Tasks.await;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import br.ufc.quixada.projetofinalperseo.models.Grupo;
import br.ufc.quixada.projetofinalperseo.models.Usuario;
import br.ufc.quixada.projetofinalperseo.utilities.AuthService;

public class UsuarioViewModel extends BaseObservable {
    private Usuario usuario;

    public UsuarioViewModel(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        db.collection("usuarios").document(id).addSnapshotListener((snapshot, e) -> {
                if (e != null){
                    Log.d("Projeto Mobile - Carregando View Model", "Erro ao carregar usuario com id: " + id + " - " + e.getLocalizedMessage());
                }
                if (snapshot == null || !snapshot.exists()) {
                    Log.d("Projeto Mobile - Carregando View Model", "Não existe usuario com id: " + id);
                    return;
                }
                Usuario usuario = snapshot.toObject(Usuario.class);
                if (usuario == null) {
                    Log.d("Projeto Mobile - Carregando View Model", "Usuario nulo");
                    return;
                }
                setUsuario(usuario);
                Log.d("Projeto Mobile - Carregando View Model", "Carregado usuario com id: " + id + " - " + usuario.toString());
                notifyPropertyChanged(BR.nome);
                notifyPropertyChanged(BR.email);

        });
    }

    public UsuarioViewModel() { this.usuario = new Usuario(); }

    public UsuarioViewModel(Usuario usuario) { this.usuario = usuario; }

    public List<Grupo> getGrupos(){
        CollectionReference gruposRef = FirebaseFirestore.getInstance().collection("grupos");
        AtomicReference<List<Grupo>> grupos = new AtomicReference<>();
        usuario.getGrupos().forEach(documentReference -> {
            gruposRef.document(documentReference.getId()).get().addOnSuccessListener(documentSnapshot -> {
                Grupo grupo = documentSnapshot.toObject(Grupo.class);
                if (grupo == null) return;
                grupos.get().add(grupo);
            }).addOnFailureListener(e -> {
                Log.d("Projeto Mobile - Grupo View Model", "Erro ao carregar grupo com id: " + documentReference.getId() + " - " + e.getLocalizedMessage());
                return;
            });
        });
        return grupos.get();
    }

    public Grupo getGrupo(String id){
        CollectionReference gruposRef = FirebaseFirestore.getInstance().collection("grupos");
        AtomicReference<List<Grupo>> grupos = new AtomicReference<>();
        usuario.getGrupos().forEach(documentReference -> {
            if (documentReference.getId().equals(id)) {
                gruposRef.document(documentReference.getId()).get().addOnSuccessListener(documentSnapshot -> {
                    Grupo grupo = documentSnapshot.toObject(Grupo.class);
                    grupos.get().add(grupo);
                    if (grupo == null) return;
                }).addOnFailureListener(e -> {
                    Log.d("Projeto Mobile - Grupo View Model", "Erro ao carregar grupo com id: " + documentReference.getId() + " - " + e.getLocalizedMessage());
                    return;
                });
            }
        });
        if (grupos.get().isEmpty()) return null;
        return grupos.get().get(0);
    }

    //getters e setters
    @Bindable
    public String getNome(){
        if (usuario == null) return "";
        return usuario.getNome();
    }
    public void setNome(String nome){
        usuario.setNome(nome);
        notifyPropertyChanged(BR.nome);
    }

    @Bindable
    public String getEmail(){
        if (usuario == null) return "";
        return usuario.getEmail();
    }
    public void setEmail(String email){
        usuario.setEmail(email);
        Log.d("Projeto Mobile - UsuarioViewModel - Atualizando Email", "Email: " + email);
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

    //Metodos Firebase Firestore e Auth
    public void criarUsuario( String senha, Context context ){
        try{
            AuthService.criarUsuario(usuario.getEmail(), senha , context);
            FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("usuarios").document(usuario.getId()).set(usuario.getId());
            setUsuario(usuario);
        }catch (Exception e){
            e.printStackTrace();
            setUsuario(new Usuario());
            return;
        }
    }

    public void entrarEmGrupo(@NonNull Grupo grupo){
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        DocumentReference grupoRef = db.collection("grupos").document(grupo.getId());
        DocumentReference usuarioRef = db.collection("usuarios").document(usuario.getId());
        usuario.getGrupos().add(grupoRef);
        grupo.getParticipantes().add(usuarioRef);

        db.collection("usuarios").document(usuario.getId()).update("grupos", usuario.getGrupos());
        db.collection("grupos").document(grupo.getId()).update("usuarios", grupo.getParticipantes());
    }

    public void sairDeGrupo(@NonNull Grupo grupo){
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        DocumentReference grupoRef = db.collection("grupos").document(grupo.getId());
        DocumentReference usuarioRef = db.collection("usuarios").document(usuario.getId());
        usuario.getGrupos().removeIf(grupoRef::equals);
        grupo.getParticipantes().removeIf(usuarioRef::equals);

        db.collection("usuarios").document(usuario.getId()).update("grupos", usuario.getGrupos());
        db.collection("grupos").document(grupo.getId()).update("usuarios", grupo.getParticipantes());
    }

    public void setUsuarioPorAuth(){
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        db.collection("usuarios").whereEqualTo("email", AuthService.usuario.getEmail()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                if(!task.getResult().getDocuments().isEmpty()){
                    setUsuario(task.getResult().getDocuments().get(0).toObject(Usuario.class));
                    Log.d("Projeto Mobile - Carregando View Model", "Carregado usuario com id: " + usuario.getId() + " - " + usuario.toString());
                }else{
                    setUsuario(new Usuario());
                }
            }else{
                setUsuario(new Usuario());
            }
        });
    }
}
