package com.uniquindio.co.modelo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.uniquindio.co.modelo.dominio.Reserva;
import com.uniquindio.co.modelo.producto.ProductoTuristico;
public class Actividad implements ProductoTuristico {

    private String codigo;
    private String nombre;
    private String municipio;
    private int horas;
    private double precio;
    private int cupomaximo;
    private NivelDificultad dificultad;
    private Estado estado;

    private Agencia theAgencia;

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

    public String getCodigo(){
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

    public int getDuracionHoras() {
        return horas;
    }

    public void setDuracionHoras(int horas) {
        this.horas = horas;
    }

    public double getPrecioPorPersona() {
        return precio;
    }

    public void setPrecioPorPersona(double precio) {
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

    public List<Actividad> getActividades(){
        List<Actividad> actividad = new ArrayList<>();
        actividad.add(this);
        return actividad;
    }

    public boolean tieneActividadSuspendida(){
        if(this.estado == Estado.SUSPENDIDA){
            return true;
        }
        return false;
    }

    public boolean contiene(ProductoTuristico otro){
        return false;
    }

    public boolean consultarDisponibilidad(LocalDate fecha, int cantidadViajeros){
        int viajerosreservados = 0;
        for( Reserva reserva : theAgencia.getListaReservas()){
            if(reserva.getProducto().getActividades().contains(this)){
                if((reserva.getFechaViaje().toLocalDate()).isEqual(fecha)){
                    viajerosreservados += reserva.getNumViajeros();
                }
            }
        }
        if(viajerosreservados+cantidadViajeros>this.getCupomaximo()){
            return false;
        }
        return true;
    }

}
