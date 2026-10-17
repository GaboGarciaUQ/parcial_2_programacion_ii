package com.uniquindio.co.modelo;
import java.util.List;
public class Agencia {

    private String nombre;
    private String nit;
    private String rnt;
    private String direccion;
    private String telefono;
    private String correo;
    private String paginaweb;
    private List<Cliente> listaClientes;
    private List<Guia> listaGuias;
    private List<Actividad> listaActividades;
    private List<PaqueteTuristico> listaPaquetesTuristicos;
    private List<Reserva> listaReservas;

    public Agencia(String nombre, String nit, String rnt, String direccion, String telefono, String correo, String paginaweb, List<Cliente> listaClientes, List<Guia> listaGuias, List<Actividad> listaActividades, List<PaqueteTuristico> listaPaquetesTuristicos, List<Reserva> listaReservas) {
        this.nombre = nombre;
        this.nit = nit;
        this.rnt = rnt;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.paginaweb = paginaweb;
        this.listaClientes = listaClientes;
        this.listaGuias = listaGuias;
        this.listaActividades = listaActividades;
        this.listaPaquetesTuristicos = listaPaquetesTuristicos;
        this.listaReservas = listaReservas;
    }

    public void RegistrarCliente(String id, String nombre, String telefono, String correo, String pais, String idioma, List<Reserva> listaReservas){
        Cliente cliente = new Cliente(id, nombre, telefono, correo, pais, idioma,listaReservas);
        listaClientes.add(cliente);
    }

    public void RegistrarGuia(String id, String nombre, List<String> idiomas, String telefono, double tarifadia){
        Guia guia = new Guia(id, nombre, idiomas, telefono, tarifadia);
        listaGuias.add(guia);
    }

    public void RegistrarActividad(String codigo, String nombre, String municipio, int horas, double precio, int cupomaximo, NivelDificultad dificultad, Estado estado){
        Actividad actividad = new Actividad(codigo, nombre, municipio, horas, precio, cupomaximo, dificultad, estado);
        listaActividades.add(actividad);
    }

    public void RegistrarPaqueteTuristico(String codigo, String nombre, double descuento, List<IContenible> listaContenidos){
        PaqueteTuristico paquete = new PaqueteTuristico(codigo, nombre, descuento, listaContenidos)
        listaPaquetesTuristicos.add(paquete);
    }

    public void RegistrarReserva(){
        //Este tambien se hace con el builder que no hemos construido.
    }

    public GenerarReporteIngresos(){
        for(Reserva reserva : listaReservas){
            
        }
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRnt() {
        return rnt;
    }

    public void setRnt(String rnt) {
        this.rnt = rnt;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPaginaweb() {
        return paginaweb;
    }

    public void setPaginaweb(String paginaweb) {
        this.paginaweb = paginaweb;
    }

}
