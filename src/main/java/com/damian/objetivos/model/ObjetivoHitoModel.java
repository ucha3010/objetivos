package com.damian.objetivos.model;

public class ObjetivoHitoModel {

    private int idSubcategoria;
    private String titulo;
    private String objetivo;
    private int hito;

    public int getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(int idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public ObjetivoHitoModel() {

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public int getHito() {
        return hito;
    }

    public void setHito(int hito) {
        this.hito = hito;
    }

    @Override
    public String toString() {
        return "ObjetivoHitoModel{" +
                "idSubcategoria=" + idSubcategoria +
                ", titulo='" + titulo + '\'' +
                ", objetivo='" + objetivo + '\'' +
                ", hito=" + hito +
                '}';
    }
}
