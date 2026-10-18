package com.uniquindio.co.modelo;
import java.util.ArrayList;
import java.util.List;
import com.uniquindio.co.modelo.producto.ProductoTuristico;
public class PaqueteTuristico implements ProductoTuristico{

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

    public void agregarContenido(ProductoTuristico contenido){
        if(this.getCodigo() != contenido.getCodigo()){
            if(this.getListaContenidos().contains(contenido)!=true){
                this.listaContenidos.add(contenido);
            }
        }
    }

    public double getPrecioPorPersona(){
        double precio = 0;
        for (ProductoTuristico producto : listaContenidos){
                precio += producto.getPrecioPorPersona();
        }
        return precio;  
    }

    public int getDuracionHoras(){
        int horas = 0;
        for(ProductoTuristico producto : listaContenidos){
            horas += producto.getDuracionHoras();
        }
        return horas;
    }

    public List<Actividad> getActividades(){
        List<Actividad> actividades = new ArrayList<>();
        for(ProductoTuristico producto : listaContenidos){
            if(producto instanceof PaqueteTuristico){
                actividades.addAll(producto.getActividades());
            }
            if(actividades.contains(producto)!=true){
                actividades.addAll(producto.getActividades());
            }
        }
        return actividades;
    }

    public boolean tieneActividadSuspendida(){
        boolean suspendida = false;
        for(ProductoTuristico producto : listaContenidos){
            if(producto instanceof PaqueteTuristico){
                suspendida = producto.tieneActividadSuspendida();
            }
            suspendida = producto.tieneActividadSuspendida();
        }
        return suspendida;
    }

    public boolean contiene(ProductoTuristico otro){
        for(ProductoTuristico producto : listaContenidos){
            if(producto == otro){
                return true;
            }
        }
        return false;
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

}
