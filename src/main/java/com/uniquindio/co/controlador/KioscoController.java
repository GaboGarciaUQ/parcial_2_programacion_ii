package com.uniquindio.co.controlador;

import java.time.LocalDate;

import com.uniquindio.co.modelo.entidades.Cliente;
import com.uniquindio.co.modelo.entidades.Reserva;
import com.uniquindio.co.modelo.entidades.enums.CanalVoucher;
import com.uniquindio.co.modelo.patrones.creacionales.builder.ReservaBuilder;
import com.uniquindio.co.modelo.patrones.estructurales.composite.ProductoTuristico;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class KioscoController extends BaseController {

    @FXML private TextField tfDocumento, tfTarjeta;
    @FXML private ComboBox<ProductoTuristico> cbProducto;
    @FXML private DatePicker dpFecha;
    @FXML private Spinner<Integer> spViajeros;
    @FXML private TextArea taTiquete;

    @Override
    protected void alIniciar() {
        cbProducto.setItems(FXCollections.observableArrayList(ctx.getAgencia().getListaProductos()));
        mostrarComo(cbProducto, p -> p.getNombre() + " - " + dinero(p.getPrecioPorPersona()) + " p/p");
        spViajeros.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15, 1));
        dpFecha.setDayCellFactory(p -> new DateCell() {
            @Override public void updateItem(LocalDate d, boolean vacio) {
                super.updateItem(d, vacio);
                setDisable(vacio || d.isBefore(LocalDate.now()));
            }
        });
    }

    @FXML
    private void reservar() {
        Cliente cliente = ctx.getAgencia().buscarCliente(tfDocumento.getText().trim());
        if (cliente == null) {
            error("No existe un cliente con ese documento. Regístralo primero en la sección Clientes.");
            return;
        }
        try {
            Reserva reserva = new ReservaBuilder(cliente, cbProducto.getValue(),
                    dpFecha.getValue(), spViajeros.getValue()).construir();
            // Mismo Facade que usa el asesor; solo cambia el canal
            taTiquete.setText(ctx.getFacade().confirmar(reserva, tfTarjeta.getText(), CanalVoucher.KIOSCO));
            tfTarjeta.clear();
        } catch (IllegalArgumentException | IllegalStateException e) {
            taTiquete.clear();
            error(e.getMessage());
        }
    }
}