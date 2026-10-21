package com.uniquindio.co.modelo.patrones.estructurales.composite;

import java.util.ArrayList;
import java.util.List;

import com.uniquindio.co.modelo.entidades.enums.EstadoActividad;
import com.uniquindio.co.modelo.entidades.enums.NivelDificultad;

public class Actividad implements ProductoTuristico {

    private final String codigo;
    private String nombre;
    private String municipio;
    private int horas;
    private double precio;
    private int cupomaximo;
    private NivelDificultad dificultad;
    private EstadoActividad estado;

    public Actividad(String codigo, String nombre, String municipio, int horas,
                     double precio, int cupomaximo, NivelDificultad dificultad, EstadoActividad estado) {
        if (horas <= 0 || precio <= 0 || cupomaximo <= 0) {
            throw new IllegalArgumentException("Duración, precio y cupo deben ser mayores que cero");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.municipio = municipio;
        this.horas = horas;
        this.precio = precio;
        this.cupomaximo = cupomaximo;
        this.dificultad = dificultad;
        this.estado = estado;
    }

    //Interfaz ProductoTuristico
    @Override
    public String getCodigo() { return codigo; }

    @Override
    public String getNombre() { return nombre; }

    @Override
    public int getDuracionHoras() { return horas; }

    @Override
    public double getPrecioPorPersona() { return precio; }

    @Override
    public List<Actividad> getActividades() {
        List<Actividad> actividades = new ArrayList<>();
        actividades.add(this);
        return actividades;
    }

    @Override
    public boolean tieneActividadSuspendida() {
        return estado == EstadoActividad.SUSPENDIDA;
    }

    @Override
    public boolean contiene(ProductoTuristico otro) {
        return this == otro;
    }

    //Propios
    public String getMunicipio() { return municipio; }
    public int getCupomaximo() { return cupomaximo; }
    public NivelDificultad getDificultad() { return dificultad; }
    public EstadoActividad getEstado() { return estado; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }
    public void setDuracionHoras(int horas) { this.horas = horas; }
    public void setPrecioPorPersona(double precio) { this.precio = precio; }
    public void setCupomaximo(int cupomaximo) { this.cupomaximo = cupomaximo; }
    public void setDificultad(NivelDificultad dificultad) { this.dificultad = dificultad; }
    public void setEstado(EstadoActividad estado) { this.estado = estado; }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (" + municipio + ")";
    }
}