package br.ufc.quixada.projetofinalperseo.models;

import com.google.firebase.firestore.DocumentId;

import java.util.List;

public class Grupo {
    @DocumentId
    private String id;
    private String nome;
    private String descricao;
    private Usuario criador;
    private Localizacao localizacao;
    private List<Usuario> participantes;
    private List<Atividade> atividades;

    public Grupo(String nome, String descricao, Usuario criador, Localizacao localizacao, List<Usuario> participantes, List<Atividade> atividades) {
        this.nome = nome;
        this.descricao = descricao;
        this.criador = criador;
        this.localizacao = localizacao;
        this.participantes = participantes;
        this.atividades = atividades;
    }

    public Grupo(String id, String nome, String descricao, Usuario criador, Localizacao localizacao, List<Usuario> participantes, List<Atividade> atividades) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.criador = criador;
        this.localizacao = localizacao;
        this.participantes = participantes;
        this.atividades = atividades;
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

    public Usuario getCriador() {
        return criador;
    }

    public void setCriador(Usuario criador) {
        this.criador = criador;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public List<Usuario> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Usuario> participantes) {
        this.participantes = participantes;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }
}
