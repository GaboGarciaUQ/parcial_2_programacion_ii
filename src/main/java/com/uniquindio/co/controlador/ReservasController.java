package com.uniquindio.co.controlador;

import java.time.LocalDate;

import com.uniquindio.co.modelo.entidades.Cliente;
import com.uniquindio.co.modelo.entidades.Guia;
import com.uniquindio.co.modelo.entidades.Reserva;
import com.uniquindio.co.modelo.patrones.creacionales.builder.ReservaBuilder;
import com.uniquindio.co.modelo.patrones.estructurales.composite.ProductoTuristico;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class ReservasController extends BaseController {

    @FXML private ComboBox<Cliente> cbCliente;
    @FXML private ComboBox<ProductoTuristico> cbProducto;
    @FXML private DatePicker dpFecha;
    @FXML private Spinner<Integer> spViajeros;
    @FXML private CheckBox chkGuia, chkTransporte, chkSeguro;
    @FXML private ComboBox<Guia> cbGuia;
    @FXML private TextArea taObservaciones;
    @FXML private TableView<Reserva> tabla;

    @Override
    protected void alIniciar() {
        cbCliente.setItems(FXCollections.observableArrayList(ctx.getAgencia().getListaClientes()));
        mostrarComo(cbCliente, c -> c.getId() + " - " + c.getNombre() + " (" + c.getIdioma() + ")");

        cbProducto.setItems(FXCollections.observableArrayList(ctx.getAgencia().getListaProductos()));
        mostrarComo(cbProducto, p -> p.getNombre() + " - " + dinero(p.getPrecioPorPersona()) + " p/p");

        cbGuia.setItems(FXCollections.observableArrayList(ctx.getAgencia().getListaGuias()));
        cbGuia.disableProperty().bind(chkGuia.selectedProperty().not());

        spViajeros.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15, 1));

        dpFecha.setDayCellFactory(p -> new DateCell() {
            @Override
            public void updateItem(LocalDate d, boolean vacio) {
                super.updateItem(d, vacio);
                setDisable(vacio || d.isBefore(LocalDate.now()));
            }
        });

        ajustar(tabla);
        tabla.getColumns().add(col("N°", r -> String.valueOf(r.getNumero())));
        tabla.getColumns().add(col("Cliente", r -> r.getCliente().getNombre()));
        tabla.getColumns().add(col("Producto", r -> r.getProducto().getNombre()));
        tabla.getColumns().add(col("Fecha", r -> r.getFechaViaje().toString()));
        tabla.getColumns().add(col("Viajeros", r -> String.valueOf(r.getNumViajeros())));
        tabla.getColumns().add(col("Valor", r -> dinero(r.calcularValor())));
        tabla.setItems(FXCollections.observableArrayList(ctx.getAgencia().getListaReservas()));
    }

    @FXML
    private void continuar() {
        try {
            if (chkGuia.isSelected() && cbGuia.getValue() == null) {
                error("Selecciona el guía o desmarca la opción.");
                return;
            }
            // Patrón Builder: obligatorios en el constructor, opcionales encadenados
            ReservaBuilder builder = new ReservaBuilder(cbCliente.getValue(), cbProducto.getValue(),
                    dpFecha.getValue(), spViajeros.getValue());
            if (chkGuia.isSelected()) builder.conGuia(cbGuia.getValue());
            if (chkTransporte.isSelected()) builder.conTransporte();
            if (chkSeguro.isSelected()) builder.conSeguro();
            builder.conObservaciones(taObservaciones.getText());

            // La reserva queda pendiente; se paga y confirma en la siguiente pantalla
            ctx.setPendiente(builder.construir());
            ctx.irA("confirmar-reserva");
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
        }
    }
}