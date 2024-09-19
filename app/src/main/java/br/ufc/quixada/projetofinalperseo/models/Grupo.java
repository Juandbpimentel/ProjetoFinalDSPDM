package br.ufc.quixada.projetofinalperseo.models;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.stream.Collectors;

public class Grupo {
    @DocumentId
    private String id;

    @Override
    public String toString() {
        return "Grupo{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", administrador=" + administrador +
                ", localizacao=" + localizacao +
                ", participantes=[" + participantes.stream().map(DocumentReference::getId).collect(Collectors.joining(", ")) +
                "], atividades=" + atividades.stream().map(DocumentReference::getId).collect(Collectors.joining(", ")) +
                ", esporte='" + esporte + '\'' +
                '}';
    }

    private String nome;
    private String descricao;
    private DocumentReference administrador;
    private DocumentReference localizacao;
    private List<DocumentReference> participantes;
    private List<DocumentReference> atividades;
    private String esporte;

    public Grupo(String nome, String descricao, Usuario administrador, Localizacao localizacao, List<Usuario> participantes, List<Atividade> atividades, String esporte) {
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        this.nome = nome;
        this.descricao = descricao;
        this.administrador = db.collection("usuarios").document(administrador.getId());
        this.localizacao = db.collection("localizacoes").document(localizacao.getId());
        this.participantes = participantes.stream().map(usuario -> db.collection("usuarios").document(usuario.getId())).collect(Collectors.toList());
        this.atividades = atividades.stream().map(atividade -> db.collection("atividades").document(atividade.getId())).collect(Collectors.toList());
        this.esporte = esporte;
    }

    public Grupo(String id, String nome, String descricao, Usuario administrador, Localizacao localizacao, List<Usuario> participantes, List<Atividade> atividades, String esporte) {
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.administrador = db.collection("usuarios").document(administrador.getId());
        this.localizacao = db.collection("localizacoes").document(localizacao.getId());
        this.participantes = participantes.stream().map(usuario -> db.collection("usuarios").document(usuario.getId())).collect(Collectors.toList());
        this.atividades = atividades.stream().map(atividade -> db.collection("atividades").document(atividade.getId())).collect(Collectors.toList());
        this.esporte = esporte;
    }

    public Grupo(String nome, String descricao, DocumentReference administrador, DocumentReference localizacao, List<DocumentReference> participantes, List<DocumentReference> atividades, String esporte) {
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        this.nome = nome;
        this.descricao = descricao;
        this.administrador = administrador;
        this.localizacao = localizacao;
        this.participantes = participantes;
        this.atividades = atividades;
        this.esporte = esporte;
    }

    public Grupo(String id, String nome, String descricao, DocumentReference administrador, DocumentReference localizacao, List<DocumentReference> participantes, List<DocumentReference> atividades, String esporte) {
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.administrador = administrador;
        this.localizacao = localizacao;
        this.participantes = participantes;
        this.atividades = atividades;
        this.esporte = esporte;
    }

    public Grupo() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public DocumentReference getAdministrador() {
        return administrador;
    }

    public void setAdministrador(DocumentReference administrador) {
        this.administrador = administrador;
    }

    public DocumentReference getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(DocumentReference localizacao) {
        this.localizacao = localizacao;
    }

    public List<DocumentReference> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<DocumentReference> participantes) {
        this.participantes = participantes;
    }

    public List<DocumentReference> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<DocumentReference> atividades) {
        this.atividades = atividades;
    }

    public String getEsporte() {
        return esporte;
    }

    public void setEsporte(String esporte) {
        this.esporte = esporte;
    }
}
