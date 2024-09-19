package br.ufc.quixada.projetofinalperseo.models;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.GeoPoint;

public class Localizacao {
    private String id;
    private String cidade;
    private String estado;
    private String pais;
    private GeoPoint geoPoint;

    public Localizacao() {
    }

    public Localizacao(String id, String cidade, String estado, String pais, GeoPoint geoPoint) {
        this.id = id;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.geoPoint = geoPoint;
    }

    public Localizacao(String cidade, String estado, String pais, GeoPoint geoPoint) {
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.geoPoint = geoPoint;
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

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }
}
