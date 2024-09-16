package br.ufc.quixada.projetofinalperseo.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;

public class Atividade {
    @DocumentId
    private String id;
    private Timestamp dataAgenda;
    private String descricao;
    private String nome;
    private Localizacao localizacao;
    private Grupo grupo;

    public Atividade() {
    }

    public Atividade(String id, Timestamp dataAgenda, String descricao, String nome, Localizacao localizacao, Grupo grupo) {
        this.id = id;
        this.dataAgenda = dataAgenda;
        this.descricao = descricao;
        this.nome = nome;
        this.localizacao = localizacao;
        this.grupo = grupo;
    }


    public Atividade(Timestamp dataAgenda, String descricao, String nome, Localizacao localizacao, Grupo grupo) {
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

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
}
