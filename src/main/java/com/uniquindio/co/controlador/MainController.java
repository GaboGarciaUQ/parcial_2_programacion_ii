package com.uniquindio.co.controlador;

import java.io.IOException;
import java.io.UncheckedIOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML private StackPane contenido;
    @FXML private Label lblAgencia;
    private Contexto ctx;

    public void init(Contexto ctx) {
        this.ctx = ctx;
        ctx.setNavegador(this::cargar);
        lblAgencia.setText(ctx.getAgencia().getNombre() + "  |  NIT " + ctx.getAgencia().getNit()
                + "  |  " + ctx.getAgencia().getRnt());
        mostrarClientes();
    }

    @FXML private void mostrarClientes()    { cargar("clientes"); }
    @FXML private void mostrarGuias()       { cargar("guias"); }
    @FXML private void mostrarActividades() { cargar("actividades"); }
    @FXML private void mostrarPaquetes()    { cargar("paquetes"); }
    @FXML private void mostrarReservas()    { cargar("reservas"); }
    @FXML private void mostrarKiosco()      { cargar("kiosco"); }
    @FXML private void mostrarCorreos() { cargar("correos"); }
    @FXML private void mostrarReportes()    { cargar("reportes"); }

    private void cargar(String vista) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uniquindio/co/vista/" + vista + ".fxml"));
            Parent raiz = loader.load();
            BaseController c = loader.getController();
            c.init(ctx);
            contenido.getChildren().setAll(raiz);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}