package br.ufc.quixada.projetofinalperseo.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class Atividade {
    private String id;
    private String nome;
    private String descricao;
    private Timestamp dataAgenda;
    private DocumentReference grupo;
    private DocumentReference localizacao;

    public Atividade() {
    }

    public Atividade(String nome , String descricao, Timestamp dataAgenda, Grupo grupo, Localizacao localizacao) {
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        this.id = UUID.randomUUID().toString();
        this.dataAgenda = dataAgenda;
        this.descricao = descricao;
        this.nome = nome;
        this.localizacao = db.collection("localizacoes").document(localizacao.getId());
        this.grupo = db.collection("grupos").document(grupo.getId());
    }

    public Atividade(String id, String nome, String descricao, Timestamp dataAgenda, Grupo grupo, Localizacao localizacao) {
        FirebaseFirestore db = FirebaseFirestore.getInstance("db-firestore-projeto-mobile-perseo");
        this.id = id;
        this.dataAgenda = dataAgenda;
        this.descricao = descricao;
        this.nome = nome;
        this.localizacao = db.collection("localizacoes").document(localizacao.getId());
        this.grupo = db.collection("grupos").document(grupo.getId());
    }


    public Atividade(String nome , String descricao, Timestamp dataAgenda, DocumentReference grupo, DocumentReference localizacao) {
        this.dataAgenda = dataAgenda;
        this.descricao = descricao;
        this.nome = nome;
        this.localizacao = localizacao;
        this.grupo = grupo;
    }

    public Atividade(String id, String nome, String descricao, Timestamp dataAgenda, DocumentReference grupo, DocumentReference localizacao) {
        this.id = id;
        this.dataAgenda = dataAgenda;
        this.descricao = descricao;
        this.nome = nome;
        this.localizacao = localizacao;
        this.grupo = grupo;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDataAgenda() {
        return dataAgenda;
    }

    public void setDataAgenda(Timestamp dataAgenda) {
        this.dataAgenda = dataAgenda;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public DocumentReference getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(DocumentReference localizacao) {
        this.localizacao = localizacao;
    }

    public DocumentReference getGrupo() {
        return grupo;
    }

    public void setGrupo(DocumentReference grupo) {
        this.grupo = grupo;
    }
}
