package com.uniquindio.co.controlador;

import com.uniquindio.co.modelo.entidades.Reserva;
import com.uniquindio.co.modelo.entidades.enums.CanalVoucher;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ConfirmarReservaController extends BaseController {

    @FXML private Label lblResumen;
    @FXML private TextField tfTarjeta;
    @FXML private ComboBox<CanalVoucher> cbCanal;
    @FXML private TextArea taVoucher;
    @FXML private Button btnConfirmar;

    @Override
    protected void alIniciar() {
        cbCanal.setItems(FXCollections.observableArrayList(CanalVoucher.values()));
        cbCanal.setValue(CanalVoucher.ASESOR);
        Reserva r = ctx.getPendiente();
        if (r == null) {
            lblResumen.setText("No hay una reserva pendiente. Crea una en la sección Reservas.");
            btnConfirmar.setDisable(true);
            return;
        }
        lblResumen.setText(
                "Cliente: " + r.getCliente().getNombre()
                + "\nProducto: " + r.getProducto().getNombre()
                + "\nFecha del viaje: " + r.getFechaViaje()
                + "\nViajeros: " + r.getNumViajeros()
                + "\n\nProducto x viajeros: " + dinero(r.getProducto().getPrecioPorPersona() * r.getNumViajeros())
                + "\nGuía: " + dinero(r.calcularCostoGuia())
                + "\nTransporte: " + dinero(r.calcularCostoTransporte())
                + "\nSeguro: " + dinero(r.calcularCostoSeguro())
                + "\n\nTotal a pagar: " + dinero(r.calcularValor()));
    }

    @FXML
    private void confirmar() {
        Reserva r = ctx.getPendiente();
        try {
            // Facade: cupo, tarjeta, cobro, cupos, registro, voucher y correo
            taVoucher.setText(ctx.getFacade().confirmar(r, tfTarjeta.getText(), cbCanal.getValue()));
            ctx.setPendiente(null);
            btnConfirmar.setDisable(true);
            tfTarjeta.clear();
        } catch (IllegalArgumentException | IllegalStateException e) {
            taVoucher.clear();
            error(e.getMessage());   // la reserva sigue pendiente: se puede reintentar con otra tarjeta
        }
    }

    @FXML
    private void cancelar() {
        ctx.setPendiente(null);
        ctx.irA("reservas");
    }

    @FXML
    private void volver() {
        ctx.irA("reservas");
    }
}