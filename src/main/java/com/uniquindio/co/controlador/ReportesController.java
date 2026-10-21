package com.uniquindio.co.controlador;

import java.time.LocalDate;

import com.uniquindio.co.modelo.patrones.estructurales.adapter.PagoAndinoAdapter.RegistroCobro;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class ReportesController extends BaseController {

    @FXML private DatePicker dpInicio, dpFin;
    @FXML private Label lblIngresos, lblCentavos, lblProducto;
    @FXML private TableView<RegistroCobro> tablaCobros;

    @Override
    protected void alIniciar() {
        ajustar(tablaCobros);
        tablaCobros.setPlaceholder(new Label("Aún no se ha enviado ningún cobro"));
        tablaCobros.getColumns().add(col("Referencia", RegistroCobro::getReferencia));
        tablaCobros.getColumns().add(col("Valor enviado (centavos)", c -> String.format("%,d", c.getValorCentavos())));
        tablaCobros.getColumns().add(col("Resultado", RegistroCobro::getDetalle));
        dpInicio.setValue(LocalDate.now());
        dpFin.setValue(LocalDate.now().plusMonths(1));
        generar();
    }

    @FXML
    private void generar() {
        LocalDate ini = dpInicio.getValue();
        LocalDate fin = dpFin.getValue();
        if (ini == null || fin == null || fin.isBefore(ini)) {
            error("Elige un periodo válido: la fecha final no puede ser anterior a la inicial.");
            return;
        }
        double ingresos = ctx.getAgencia().generarReporteIngresos(ini, fin);
        lblIngresos.setText("Ingresos del periodo: " + dinero(ingresos));
        lblCentavos.setText("Equivalente en centavos: " + String.format("%,d", Math.round(ingresos * 100)));
        lblProducto.setText(ctx.getAgencia().generarReporteProductoMasVendido());
        tablaCobros.setItems(FXCollections.observableArrayList(ctx.getPasarela().getCobros()));
    }
}