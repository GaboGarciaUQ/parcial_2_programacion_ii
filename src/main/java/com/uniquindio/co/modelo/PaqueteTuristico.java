package com.uniquindio.co.modelo;
import java.util.List;

import com.uniquindio.co.modelo.producto.ProductoTuristico;
public class PaqueteTuristico {

    private String codigo;
    private String nombre;
    private double descuento;
    private List<ProductoTuristico> listaContenidos;

    public PaqueteTuristico(String codigo, String nombre, double descuento, List<ProductoTuristico> listaContenidos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descuento = descuento;
        this.listaContenidos = listaContenidos;
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

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public List<ProductoTuristico> getListaContenidos() {
        return listaContenidos;
    }

    public void setListaContenidos(List<ProductoTuristico> listaContenidos) {
        this.listaContenidos = listaContenidos;
    }

}
