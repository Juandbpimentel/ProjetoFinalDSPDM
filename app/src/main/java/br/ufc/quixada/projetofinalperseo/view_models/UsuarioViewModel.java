package br.ufc.quixada.projetofinalperseo.view_models;

import static com.google.android.gms.tasks.Tasks.await;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import br.ufc.quixada.projetofinalperseo.models.Grupo;
import br.ufc.quixada.projetofinalperseo.models.Usuario;
import br.ufc.quixada.projetofinalperseo.utilities.AuthService;

public class UsuarioViewModel extends BaseObservable {
    private Usuario usuario;

    public UsuarioViewModel(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        final DocumentReference docRef = db.collection("usuarios").document(id);
        Task<DocumentSnapshot> task = docRef.get();
        task.addOnSuccessListener((resultado) -> {
                if (!resultado.exists()) {
                    Log.d("Projeto Mobile - Carregando View Model", "Não existe usuario com id: " + id);
                    return;
                }
                Usuario usuario = resultado.toObject(Usuario.class);
                if (usuario == null) {
                    Log.d("Projeto Mobile - Carregando View Model", "Usuario nulo");
                    return;
                }
                setUsuario(usuario);
                Log.d("Projeto Mobile - Carregando View Model", "Carregado usuario com id: " + id + " - " + usuario.toString());
                notifyPropertyChanged(BR.nome);
                notifyPropertyChanged(BR.email);

        });
        task.addOnFailureListener((e) -> {
            Log.d("Projeto Mobile - Carregando View Model", "Erro ao carregar usuario com id: " + id + " - " + e.getLocalizedMessage());
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
    public void criarUsuario(Usuario novoUsuario, String senha, Context context){
        try{
            AuthService.criarUsuario(novoUsuario.getEmail(), senha , context);
            FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("usuarios").document(novoUsuario.getId()).set(novoUsuario);
            setUsuario(novoUsuario);
        }catch (Exception e){
            e.printStackTrace();
            setUsuario(new Usuario());
            return;
        }
    }

    public void atualizarUsuario( String idUsuarioAntigo, String senha, Context context){
        if (idUsuarioAntigo == null) return;
        Log.d("Projeto Atualizando Usuario", "Usuario novo: " + usuario.toString());
        if (senha != null)
            Log.d("Projeto Atualizando Usuario", "Senha: " + senha);
        String email = usuario.getEmail();
        String nome = usuario.getNome();
        Log.d("Projeto Atualizando Usuario", "Email: " + email);
        atualizarNome(nome, idUsuarioAntigo);
        atualizarEmail(email, idUsuarioAntigo, context);
        atualizarSenha(senha, context);
        atualizarId(usuario.getId(), idUsuarioAntigo);
    }

    public void atualizarNome(String nome, String id){
        DocumentReference usuarioRef = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("usuarios").document(id);
        AtomicReference<Usuario> usuarioAntigo = new AtomicReference<>();
        usuarioRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                usuarioAntigo.set(task.getResult().toObject(Usuario.class));
            }
        });
        if(usuarioAntigo.get() != null && nome.equals(usuarioAntigo.get().getNome()))
            return;
        usuarioRef.update("nome", nome);
        usuario.setNome(nome);
    }

    public void atualizarEmail(String email, String id, Context context){
        DocumentReference usuarioRef = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("usuarios").document(id);
        Log.d("Projeto Atualizando Email", "Email: " + email + " | Id: " + id);

        usuarioRef.get().addOnSuccessListener(
            documentSnapshot -> {
                Usuario usuarioAntigo = documentSnapshot.toObject(Usuario.class);
                if(usuarioAntigo == null) {
                    Log.d("Atualizando Email", "Usuario antigo nulo");
                    return;
                }
                if (email == null || usuarioAntigo.getEmail() == null) {
                    Log.d("Atualizando Email", email == null ? "Email nulo" : "Email antigo nulo");
                    if (usuarioAntigo.getEmail() != null)
                        setEmail(usuarioAntigo.getEmail());
                    return;
                }
                if (email.equals(usuarioAntigo.getEmail())) {
                    Log.d("Atualizando Email", "Email igual ao antigo: "+ email);
                    return;
                }
                Log.d("Projeto Atualizando Email", "Email antigo: " + usuarioAntigo.getEmail() + " | Email novo: " + email);
                boolean deuCerto = AuthService.alterarEmail(email, context);
                if (deuCerto) {
                    Log.d("Projeto Atualizando Email", "Email alterado com sucesso");
                    usuarioRef.update("email", email);
                    usuario.setEmail(email);
                    return;
                }
                Log.d("Projeto Atualizando Email", "Email não alterado");
                usuario.setEmail(usuarioAntigo.getEmail());
            }
        ).addOnFailureListener(e -> {
            Log.d("Atualizando Email", "Erro ao buscar usuario antigo: " + e.getLocalizedMessage());
        });

    }

    public void atualizarId(String id, String idAntigo){
        DocumentReference usuarioRef = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("usuarios").document(idAntigo);
        if (id.equals(idAntigo))
            return;
        usuario.setId(id);
        FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("usuarios").document(id).set(usuario);
        FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("usuarios").document(idAntigo).delete();
        for (Grupo grupo : getGrupos()) {
            grupo.getParticipantes().removeIf(usuarioRef::equals);
            grupo.getParticipantes().add(FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("usuarios").document(id));
            FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("grupos").document(grupo.getId()).update("usuarios", grupo.getParticipantes());
            if (grupo.getAdministrador().equals(usuarioRef)){
                grupo.setAdministrador(FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("usuarios").document(id));
                FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo").collection("grupos").document(grupo.getId()).update("administrador", grupo.getAdministrador());
            }
        }
        usuario.setId(id);
    }

    public void atualizarSenha(String senha, Context context){
        if (senha == null || senha.isEmpty()) return;
        AuthService.alterarSenha(senha, context);
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
