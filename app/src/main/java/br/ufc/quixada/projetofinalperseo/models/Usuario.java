package br.ufc.quixada.projetofinalperseo.models;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Usuario {
    private String id;

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", grupos= [" + grupos.stream().map(DocumentReference::getId).collect(Collectors.joining(", "))
                +
                "]}";
    }

    private String nome;
    private String email;
    private List<DocumentReference> grupos;


    public Usuario(String nome, String email, ArrayList<Grupo> grupos) {
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.email = email;
        this.grupos = grupos.stream().map(grupo -> db.collection("grupos").document(grupo.getId())).collect(Collectors.toList());
    }

    public Usuario(String id, String nome, String email, ArrayList<Grupo> grupos) {
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.grupos = grupos.stream().map(grupo -> db.collection("grupos").document(grupo.getId())).collect(Collectors.toList());
    }

    public Usuario(String nome, String email, List<DocumentReference> atividades) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.email = email;
        this.grupos = atividades;
    }

    public Usuario(String id, String nome, String email, List<DocumentReference> atividades) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.grupos = atividades;
    }

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<DocumentReference> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<DocumentReference> grupos) {
        this.grupos = grupos;
    }
}
