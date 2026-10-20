package com.uniquindio.co.modelo.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.uniquindio.co.modelo.entidades.enums.EstadoActividad;
import com.uniquindio.co.modelo.entidades.enums.NivelDificultad;
import com.uniquindio.co.modelo.patrones.estructurales.composite.Actividad;
import com.uniquindio.co.modelo.patrones.estructurales.composite.PaqueteTuristico;
import com.uniquindio.co.modelo.patrones.estructurales.composite.ProductoTuristico;

public class Agencia {

    private final String nombre;
    private final String nit;
    private final String rnt;
    private String direccion;
    private String telefono;
    private String correo;
    private String paginaweb;
    private final List<Cliente> listaClientes;
    private final List<Guia> listaGuias;
    private final List<Actividad> listaActividades;
    private final List<PaqueteTuristico> listaPaquetesTuristicos;
    private final List<Reserva> listaReservas;

    public Agencia(String nombre, String nit, String rnt, String direccion,
                   String telefono, String correo, String paginaweb) {
        this.nombre = nombre;
        this.nit = nit;
        this.rnt = rnt;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.paginaweb = paginaweb;
        this.listaClientes = new ArrayList<>();
        this.listaGuias = new ArrayList<>();
        this.listaActividades = new ArrayList<>();
        this.listaPaquetesTuristicos = new ArrayList<>();
        this.listaReservas = new ArrayList<>();
    }

    public void registrarCliente(String id, String nombre, String telefono, String correo,
                                 String pais, String idioma) {
        if (buscarCliente(id) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con ese documento");
        }
        listaClientes.add(new Cliente(id, nombre, telefono, correo, pais, idioma));
    }

    public Cliente buscarCliente(String id) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getId().equals(id)) {
                return cliente;
            }
        }
        return null;
    }

    
    public void registrarGuia(String id, String nombre, List<String> idiomas,
                              String telefono, double tarifadia) {
        if (buscarGuia(id) != null) {
            throw new IllegalArgumentException("Ya existe un guía con esa identificación");
        }
        listaGuias.add(new Guia(id, nombre, idiomas, telefono, tarifadia));
    }

    public Guia buscarGuia(String id) {
        for (Guia guia : listaGuias) {
            if (guia.getId().equals(id)) {
                return guia;
            }
        }
        return null;
    }

   
    public void registrarActividad(String codigo, String nombre, String municipio, int horas,
                                   double precio, int cupomaximo,
                                   NivelDificultad dificultad, EstadoActividad estado) {
        if (buscarProducto(codigo) != null) {
            throw new IllegalArgumentException("Ya existe un producto con ese código");
        }
        listaActividades.add(new Actividad(codigo, nombre, municipio, horas, precio,
                                           cupomaximo, dificultad, estado));
    }

    public void registrarPaqueteTuristico(String codigo, String nombre, double descuento,
                                          List<ProductoTuristico> listaContenidos) {
        if (buscarProducto(codigo) != null) {
            throw new IllegalArgumentException("Ya existe un producto con ese código");
        }
        PaqueteTuristico paquete = new PaqueteTuristico(codigo, nombre, descuento);
        for (ProductoTuristico contenido : listaContenidos) {
            paquete.agregarContenido(contenido);
        }
        listaPaquetesTuristicos.add(paquete);
    }

    public ProductoTuristico buscarProducto(String codigo) {
        for (ProductoTuristico producto : getListaProductos()) {
            if (producto.getCodigo().equals(codigo)) {
                return producto;
            }
        }
        return null;
    }

    public List<ProductoTuristico> getListaProductos() {
        List<ProductoTuristico> productos = new ArrayList<>();
        productos.addAll(listaActividades);
        productos.addAll(listaPaquetesTuristicos);
        return productos;
    }


    // La reserva se crea con ReservaBuilder y se confirma en ConfirmarReservaFacade.
    public void registrarReserva(Reserva reserva) {
        listaReservas.add(reserva);
    }

    public int siguienteConsecutivo() {
        return listaReservas.size() + 1;
    }


    public double generarReporteIngresos(LocalDate fechaini, LocalDate fechafin) {
        double ingresos = 0;
        for (Reserva reserva : listaReservas) {
            if(reserva.estaConfirmada()){
                if (reserva.viajaEnPeriodo(fechaini, fechafin)) {
                    ingresos += reserva.calcularValor();
                }
            }
        }
        return ingresos;
    }

    public String generarReporteProductoMasVendido() {
        ProductoTuristico productomasvendido = null;
        int viajerosacumulados = 0;

        for (ProductoTuristico producto : getListaProductos()) {
            int viajeros = 0;
            for (Reserva reserva : listaReservas) {
                if(reserva.estaConfirmada()){
                    if (reserva.getProducto() == producto) {
                        viajeros += reserva.getNumViajeros();
                    }
                }
            }

            if (viajeros > viajerosacumulados) {
                productomasvendido = producto;
                viajerosacumulados = viajeros;
            }
        }

        if (productomasvendido == null) {
            return "Aún no hay reservas confirmadas.";
        }

        String tipoproducto;
        if (productomasvendido instanceof Actividad) {
            tipoproducto = "la Actividad: ";
        } else {
            tipoproducto = "el Paquete Turístico: ";
        }

        return "El producto que más viajeros ha movido en la Agencia es " + tipoproducto
                + productomasvendido.getNombre() + " con un total de "
                + viajerosacumulados + " viajeros";
    }

    public String getNombre() { return nombre; }
    public String getNit() { return nit; }
    public String getRnt() { return rnt; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPaginaweb() { return paginaweb; }
    public void setPaginaweb(String paginaweb) { this.paginaweb = paginaweb; }

    public List<Cliente> getListaClientes() { return Collections.unmodifiableList(listaClientes); }
    public List<Guia> getListaGuias() { return Collections.unmodifiableList(listaGuias); }
    public List<Actividad> getListaActividades() { return Collections.unmodifiableList(listaActividades); }
    public List<PaqueteTuristico> getListaPaquetesTuristicos() { return Collections.unmodifiableList(listaPaquetesTuristicos); }
    public List<Reserva> getListaReservas() { return Collections.unmodifiableList(listaReservas); }
}