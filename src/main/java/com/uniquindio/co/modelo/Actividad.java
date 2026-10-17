package com.uniquindio.co.modelo;
public class Actividad {

    private String codigo;
    private String nombre;
    private String municipio;
    private int horas;
    private double precio;
    private int cupomaximo;
    private NivelDificultad dificultad;
    private Estado estado;

    public Actividad(String codigo, String nombre, String municipio, int horas, double precio, int cupomaximo, NivelDificultad dificultad, Estado estado){
        this.codigo = codigo;
        this.nombre = nombre;
        this.municipio = municipio;
        this.horas = horas;
        this.precio = precio;
        this.cupomaximo = cupomaximo;
        this.dificultad = dificultad;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCupomaximo() {
        return cupomaximo;
    }

    public void setCupomaximo(int cupomaximo) {
        this.cupomaximo = cupomaximo;
    }

    public NivelDificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(NivelDificultad dificultad) {
        this.dificultad = dificultad;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}
