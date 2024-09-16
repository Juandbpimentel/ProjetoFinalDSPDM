package br.ufc.quixada.projetofinalperseo.models;

import java.util.List;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private List<Grupo> grupos;
    private Endereco endereco;


    public Usuario(String id, String nome, String email, String senha, List<Grupo> grupos, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.grupos = grupos;
        this.endereco = endereco;
    }

    public Usuario(String nome, String email, String senha, List<Grupo> grupos, Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.grupos = grupos;
        this.endereco = endereco;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
