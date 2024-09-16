package br.ufc.quixada.projetofinalperseo.models;

import com.google.firebase.firestore.DocumentId;

import java.util.UUID;

public class Endereco {
    @DocumentId
    private String id;
    private String cidade;
    private String estado;
    private String pais;

    public Endereco(String id, String cidade, String estado, String pais) {
        this.id = id;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }

    public Endereco(String cidade, String estado, String pais) {
        this.id = UUID.randomUUID().toString();
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }

    public Endereco() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
